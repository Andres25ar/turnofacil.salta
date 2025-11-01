package com.turnofacil.salta.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityResponseDTO {
    private Integer specialityId;
    private String name;
    private String description;
}
