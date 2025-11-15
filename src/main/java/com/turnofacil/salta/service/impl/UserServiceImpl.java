package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.PromoteToProfessionalDTO;
import com.turnofacil.salta.dto.admin.UserResponseDTO;
import com.turnofacil.salta.entity.Professional;
import com.turnofacil.salta.entity.Role;
import com.turnofacil.salta.entity.User;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.UserMapper;
import com.turnofacil.salta.repository.ProfessionalRepository;
import com.turnofacil.salta.repository.RoleRepository;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProfessionalRepository professionalRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            ProfessionalRepository professionalRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.professionalRepository = professionalRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public MessageResponse promoteToProfessional(Long userId, PromoteToProfessionalDTO requestDTO) {
        if (professionalRepository.findByLicence(requestDTO.getLicence()).isPresent()) {
            throw new IllegalArgumentException("La matrícula (licence) ya está en uso por otro profesional.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (user.getProfessional() != null) {
            throw new IllegalArgumentException("Este usuario ya es un profesional.");
        }

        Role professionalRole = roleRepository.findByRoleName("ROLE_PROFESIONAL")
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el role: ROLE_PROFESIONAL"));


        Professional professional = new Professional();
        professional.setLicence(requestDTO.getLicence());
        professional.setUser(user); // Asignar el usuario al profesional


        user.setProfessional(professional); // Asignar el profesional al usuario
        user.getRoles().add(professionalRole); // Añadir el nuevo rol

        userRepository.save(user);

        return new MessageResponse("El usuario " + user.getFirstName() + " ha sido ascendido a Profesional.");
    }
}
