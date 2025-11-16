package com.turnofacil.salta.dto.toPublic;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProfileUpdateRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 120)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 3, max = 120)
    private String lastName;

    @Email(message = "Debe ingresar un email valido")
    @NotBlank(message = "Email obligatorio")
    private String email;

    private String userPhone;

    private String userAddress;

    @Past(message = "No puede ingresar una fecha futura")
    private LocalDate dateOfBirth;
}
