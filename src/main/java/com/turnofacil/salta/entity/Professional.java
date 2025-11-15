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
@Table(name = "professional")
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professional_id")
    private Long profesionalId;

    @Column(name = "licence", unique = true, nullable = false)
    private String licence;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfessionalStatus status;

    //relacio uno a uno con usuario que puede no existir, ya que no todo usuario es un profesional
    @OneToOne(mappedBy = "professional", optional = true, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "professional")
    private List<SpecialityDetail> specialityDetails;

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

    //otros metodos
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ProfessionalStatus.ACTIVO;
        }
    }
}
