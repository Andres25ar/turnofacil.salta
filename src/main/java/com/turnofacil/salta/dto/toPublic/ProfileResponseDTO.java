package com.turnofacil.salta.dto.toPublic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cuil;
    private Long dni;
    private String userPhone;
    private String userAddress;
    private LocalDate dateOfBirth;
}
