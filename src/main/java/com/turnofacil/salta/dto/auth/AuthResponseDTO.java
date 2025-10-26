package com.turnofacil.salta.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    /*respuesta para el cliente luego del login o registro*/
    private String token;   //jason web token para autenticacion
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
