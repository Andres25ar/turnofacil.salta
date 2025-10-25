package com.turnofacil.salta.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Long userId;

    @NotNull(message = "El dni es obligatorio")
    @Column(name = "dni", nullable = false, length = 8)
    private Long dni;

    @NotBlank(message = "Su cuil es obligatorio")
    @Column(name = "cuil", nullable = false, unique = true, length = 11)
    private String cuil;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 4)
    @Column(name = "first_name", nullable = false, length = 120)
    private String firstName;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 4)
    @Column(name = "last_name", nullable = false, length = 120)
    private String lastName;

    @Email(message = "Debe ingresar un email valido")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    //recordar mejorar antes de pasar a produccion. ENCRIPTAR
    @Size(min = 8, max = 16, message = "Ingrese una contraseña valida")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_address")
    private String userAddress;

    @Past
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany //muchos a muchos. Un usuario podria tener varios roles
    @JoinTable(
            name = "user_role",   //nombre de la tabla intermedia que rompe la relacion muchos a muchos
            joinColumns = @JoinColumn(name = "id"),             //clave foranea local
            inverseJoinColumns = @JoinColumn(name = "role_id")  //clave foranea de la otra entidad
    )
    private Set<Role> roles = new HashSet<>();

    //relacion de uno a muchos con appointment (turnos)
    // mapeado a travez del atributo de la clase appointment llamado user
    // en cascada (si un usuario se elimina se elimina su turno)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToOne(cascade = CascadeType.ALL)
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





























