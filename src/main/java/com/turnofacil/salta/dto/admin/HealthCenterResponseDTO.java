package com.turnofacil.salta.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCenterResponseDTO {
    private Long centerId;
    private String centerName;
    private String centerAddress;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String postalCode;
    private String phone;
    private String typeOfCenterName;
}
