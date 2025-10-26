package com.turnofacil.salta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// Un DTO genérico para enviar mensajes simples de vuelta al cliente.
// Ej: {"message": "¡Operación exitosa!"}
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
