package com.turnofacil.salta.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityRequestDTO {
    @NotBlank(message = "El nombre de la especialidad no puede estar vacio")
    @Size(min = 5, max = 100, message = "Asegurate de tener entre 5 un 100 caracteres")
    private String name;

    //la descripcion no suele ser obligatoria
    private String description;
}
