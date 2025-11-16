package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.toPublic.ProfileResponseDTO;
import com.turnofacil.salta.dto.toPublic.ProfileUpdateRequestDTO;

public interface IProfileService {
    ProfileResponseDTO getMyProfile(String userEmail);

    MessageResponse updateMyProfile(String userEmail, ProfileUpdateRequestDTO requestDTO);
}
