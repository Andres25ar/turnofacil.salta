package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.PromoteToProfessionalDTO;
import com.turnofacil.salta.dto.admin.UserResponseDTO;

import java.util.List;

public interface IUserService {
    List<UserResponseDTO> getAllUsers();

    MessageResponse promoteToProfessional (Long userId, PromoteToProfessionalDTO request);
}
