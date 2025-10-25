package com.turnofacil.salta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "health_center")
public class HealthCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Long centerId;

    @Size(min = 6, max = 100)
    @Column(name = "center_name", nullable = false, unique = true)
    private String centerName;

    @Size(min = 6, max = 100)
    @Column(name = "center_address", nullable = false, unique = true)
    private String centerAddress;

    @Column(name = "latitude",precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude",precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "postal_code", nullable = false)
    private String cp;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TypeOfCenter typeOfCenter;

    @OneToMany(mappedBy = "healthCenter")
    private List<SpecialityDetail> specialityDetails;

    /*
    * auditorias
    */

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @LastModifiedBy
    @ManyToOne
    private User updatedBy;

    //otros metodos

}
























