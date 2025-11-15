package com.turnofacil.salta.dto.admin;

import com.turnofacil.salta.entity.ProfessionalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProfessionalStatusDTO {

    @NotNull(message = "El estado no puede ser nulo")
    private ProfessionalStatus status;
}