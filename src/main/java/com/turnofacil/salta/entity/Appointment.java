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
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_detail_id")
    private SpecialityDetail specialityDetail;

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

    //otros metodo

}
