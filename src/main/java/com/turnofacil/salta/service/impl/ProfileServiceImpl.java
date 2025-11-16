package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.toPublic.ProfileResponseDTO;
import com.turnofacil.salta.dto.toPublic.ProfileUpdateRequestDTO;
import com.turnofacil.salta.entity.User;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.ProfileMapper;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.service.IProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProfileServiceImpl implements IProfileService {
    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ProfileResponseDTO getMyProfile(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + userEmail));

        return ProfileMapper.toProfileResponse(user);
    }

    @Override
    @Transactional
    public MessageResponse updateMyProfile(String userEmail, ProfileUpdateRequestDTO requestDTO) {
        // 1. Encontrar al usuario
        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + userEmail));

        // 2. Validar si el email está siendo cambiado
        if (!currentUser.getEmail().equals(requestDTO.getEmail())) {
            Optional<User> userWithNewEmail = userRepository.findByEmail(requestDTO.getEmail());
            if (userWithNewEmail.isPresent()) {
                throw new IllegalArgumentException("El email " + requestDTO.getEmail() + " ya está en uso por otro usuario.");
            }
            currentUser.setEmail(requestDTO.getEmail());
        }

        // 3. Actualizar el resto de los campos
        currentUser.setFirstName(requestDTO.getFirstName());
        currentUser.setLastName(requestDTO.getLastName());
        currentUser.setUserPhone(requestDTO.getUserPhone());
        currentUser.setUserAddress(requestDTO.getUserAddress());
        currentUser.setDateOfBirth(requestDTO.getDateOfBirth());

        // 4. Guardar
        userRepository.save(currentUser);

        return new MessageResponse("Perfil actualizado correctamente.");
    }
}
