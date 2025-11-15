package com.turnofacil.salta.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cuil;
    private Long dni;
    private List<String> roles;
    private String licence;     //es null si no es un profesional
    private Long professionalId;
    private String status;
}
