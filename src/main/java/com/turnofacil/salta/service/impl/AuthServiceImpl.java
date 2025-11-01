package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.auth.AuthResponseDTO;
import com.turnofacil.salta.dto.auth.LoginRequestDTO;
import com.turnofacil.salta.dto.auth.RegisterRequestDTO;
import com.turnofacil.salta.entity.Professional;
import com.turnofacil.salta.entity.Role;
import com.turnofacil.salta.entity.User;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.repository.ProfessionalRepository;
import com.turnofacil.salta.repository.RoleRepository;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.security.JwtUtils;
import com.turnofacil.salta.security.UserDetailsImpl;
import com.turnofacil.salta.service.IAuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private final ProfessionalRepository professionalRepository;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils, ProfessionalRepository professionalRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.professionalRepository = professionalRepository;
    }

    /**public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils){
        this(userRepository, roleRepository, passwordEncoder, authenticationManager, jwtUtils, null);
    }*/

    @Override
    @Transactional
    public MessageResponse register(RegisterRequestDTO requestDTO) {
        if(userRepository.existsByEmail(requestDTO.getEmail())){
            throw new IllegalArgumentException("ERROR: Este email está en uso... Intente con un email no registrado");
        }
        if(userRepository.existsByDni(requestDTO.getDni())){
            throw new IllegalArgumentException("ERROR: Este dni está registrado");
        }
        if(userRepository.existsByCuil(requestDTO.getCuil())){
            throw new IllegalArgumentException("ERROR: Este cuil está registrado");
        }

        //creamos una instancia de la clase entidad usuario y guardamos los campos de la request en ella
        User user = new User();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setDni(requestDTO.getDni());
        user.setCuil(requestDTO.getCuil());
        user.setEmail(requestDTO.getEmail());
        user.setDateOfBirth(requestDTO.getDateOfBirth());
        user.setUserAddress(requestDTO.getUserAddress());
        user.setUserPhone(requestDTO.getUserPhone());

        //encriptamos la contraseña del usuario con el metodo encode de passwordEncoder
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        //
        Set<String> strRoles = requestDTO.getRoles();
        Set<Role> roles = new HashSet<>();
        boolean isProfessional = false;

        if(strRoles == null || strRoles.isEmpty()){
            //por defecto paciente
            Role userRole = roleRepository.findByRoleName("ROLE_PACIENTE")
                    .orElseThrow(() -> new ResourceNotFoundException("No se encontró el role: ROLE_PACIENTE"));
            roles.add(userRole);
        }else {
            /*strRoles.forEach(roleName -> {
                Role role = roleRepository.findByRoleName(roleName.toUpperCase())
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró el role: " + roleName));
                roles.add(role);
            });*/
            for(String roleName : strRoles){
                //determinar si es un profesional
                if(roleName.toUpperCase().equals("ROLE_PROFESIONAL")){
                    isProfessional = true;
                }
                Role role = roleRepository.findByRoleName(roleName.toUpperCase())
                        .orElseThrow(() -> new ResourceNotFoundException("No se encontró el role: " + roleName));
                roles.add(role);
            }
        }
        user.setRoles(roles);

        if(isProfessional){
            String licence = requestDTO.getLicence();
            if (licence == null || licence.isBlank()){
                throw new IllegalArgumentException("Para registrar un profesional debe ingresar una matricula valida");
            }
            if(professionalRepository.findByLicence(licence).isPresent()){
                throw new IllegalArgumentException("Para registrar un profesional debe ingresar una matricula valida");
            }
            //si no hay problemas con la matricula
            Professional professional = new Professional();
            professional.setLicence(licence);

            professional.setUser(user);
            user.setProfessional(professional);
        }

        userRepository.save(user);
        return new MessageResponse("USUARIO REGISTRADO EXITOSAMENTE");
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        // 1. El AuthenticationManager usa UserDetailsServiceImpl y PasswordEncoder
        // para verificar que el email y la contraseña son correctos.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDTO.getEmailOrCuil(), requestDTO.getPassword()));

        // 2. Si tiene éxito, guardamos la autenticación en el contexto
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Generamos el token JWT
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 4. Obtenemos los detalles del usuario (que ya cargó Spring Security)
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // 5. Obtenemos los roles como una lista de Strings
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // 6. Devolvemos el DTO de respuesta con el token y los datos
        return new AuthResponseDTO(
                jwt,
                userDetails.getUsername(), // Este es el email
                userDetails.getFirstName(),
                null, // No tenemos lastName en UserDetailsImpl, podríamos añadirlo
                roles
        );
    }
}










