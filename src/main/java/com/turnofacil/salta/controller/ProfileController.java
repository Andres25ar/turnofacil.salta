package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.toPublic.ProfileResponseDTO;
import com.turnofacil.salta.dto.toPublic.ProfileUpdateRequestDTO;
import com.turnofacil.salta.service.IProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Obtiene el perfil del usuario actualmente logueado (desde el token).
     */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDTO> getMyProfile(Authentication authentication) {
        String userEmail = authentication.getName();

        ProfileResponseDTO profile = profileService.getMyProfile(userEmail);
        return ResponseEntity.ok(profile);
    }

    /**
     * Actualiza el perfil del usuario actualmente logueado (desde el token).
     */
    @PutMapping("/me")
    public ResponseEntity<MessageResponse> updateMyProfile(
            @Valid @RequestBody ProfileUpdateRequestDTO requestDTO,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        MessageResponse response = profileService.updateMyProfile(userEmail, requestDTO);
        return ResponseEntity.ok(response);
    }
}
