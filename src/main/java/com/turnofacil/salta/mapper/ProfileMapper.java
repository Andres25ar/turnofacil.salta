package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.toPublic.ProfileResponseDTO;
import com.turnofacil.salta.entity.User;

public class ProfileMapper {
    public static ProfileResponseDTO toProfileResponse(User user) {
        if (user == null) {
            return null;
        }

        return new ProfileResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCuil(),
                user.getDni(),
                user.getUserPhone(),
                user.getUserAddress(),
                user.getDateOfBirth()
        );
    }
}
