package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.PromoteToProfessionalDTO;
import com.turnofacil.salta.dto.admin.UserResponseDTO;
import com.turnofacil.salta.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/promote-professional")
    public ResponseEntity<MessageResponse> promoteUser(
            @PathVariable Long id,
            @Valid @RequestBody PromoteToProfessionalDTO requestDTO
    ) {
        MessageResponse response = userService.promoteToProfessional(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // Aquí puedes añadir los endpoints para suspender, etc.
    // Ej. @PutMapping("/professional/{id}/status")
    // public ResponseEntity<MessageResponse> updateProfessionalStatus(...)

}
