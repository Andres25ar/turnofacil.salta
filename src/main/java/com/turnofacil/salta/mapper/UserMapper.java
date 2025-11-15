package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.UserResponseDTO;
import com.turnofacil.salta.entity.Role;
import com.turnofacil.salta.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO toUserResponse(User user) {
        if (user == null){
            return null;
        }

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());

        String licence = null;
        String status = null;
        Long professionalId = null;
        if (user.getProfessional() != null) {
            licence = user.getProfessional().getLicence();
            professionalId = user.getProfessional().getProfesionalId();

            if (user.getProfessional().getStatus() != null) {
                status = user.getProfessional().getStatus().name();
            }
        }

        return new UserResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCuil(),
                user.getDni(),
                roleNames,
                licence,
                professionalId,
                status
        );
    }
}
