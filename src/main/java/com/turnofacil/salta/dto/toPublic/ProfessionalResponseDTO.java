package com.turnofacil.salta.dto.toPublic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalResponseDTO {
    private Long professionalId;
    private String fullName;
    private String licence;

    private Long specialityDetailId;
}
