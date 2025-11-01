package com.turnofacil.salta.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_app")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Long userId;

    @Column(name = "dni", nullable = false, length = 8)
    private Long dni;

    @Column(name = "cuil", nullable = false, unique = true, length = 11)
    private String cuil;

    @Column(name = "first_name", nullable = false, length = 120)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 120)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //recordar mejorar antes de pasar a produccion. ENCRIPTAR
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY) //muchos a muchos. Un usuario podria tener varios roles
    @JoinTable(
            name = "user_role",   //nombre de la tabla intermedia que rompe la relacion muchos a muchos
            joinColumns = @JoinColumn(name = "user_id"),         //clave foranea local
            inverseJoinColumns = @JoinColumn(name = "role_id")  //clave foranea de la otra entidad
    )
    private Set<Role> roles = new HashSet<>();

    //relacion de uno a muchos con appointment (turnos)
    // mapeado a travez del atributo de la clase appointment llamado user
    // en cascada (si un usuario se elimina se elimina su turno)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = true)
    private Professional professional;

    /*
    *auditorias
    */

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;
}





























