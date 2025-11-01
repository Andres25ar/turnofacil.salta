package com.turnofacil.salta.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "speciality_detail")
public class SpecialityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speciality_detail_id")
    private Long specialityDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private HealthCenter healthCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "specialityDetail")
    private List<Appointment> appointments;

    /*
     * auditorias
     */

    @CreatedDate
    private LocalDateTime createdDate;

    @CreatedBy
    @ManyToOne (fetch = FetchType.LAZY)
    private User createdBy;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @LastModifiedBy
    @ManyToOne (fetch = FetchType.LAZY)
    private User updatedBy;
}
