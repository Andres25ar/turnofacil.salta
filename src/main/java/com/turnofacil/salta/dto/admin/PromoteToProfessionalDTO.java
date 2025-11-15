package com.turnofacil.salta.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PromoteToProfessionalDTO {
    @NotBlank(message = "La matricual (licence) es obligatoria")
    private String licence;
}
