package com.turnofacil.salta.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCenterRequestDTO {

    // DTO para crear o actualizar un centro de salud

    @NotBlank(message = "El nombre del centro no puede estar vacío")
    private String centerName;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String centerAddress;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @NotBlank(message = "El código postal no puede estar vacío")
    private String cp;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String phone;

    @NotNull(message = "El ID del tipo de centro es obligatorio")
    private Integer typeOfCenterId; // Solo pedimos el ID, no el objeto entero
}
