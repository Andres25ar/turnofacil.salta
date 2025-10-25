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

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ElementCollection
    @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Column(name = "days")
    private List<String> days;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "finish_time")
    private LocalTime finishTime;

    @Column(name = "number_of_appointment")
    private Integer numberOfAppointment;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "schedule")
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
