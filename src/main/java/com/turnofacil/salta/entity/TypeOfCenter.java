package com.turnofacil.salta.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_center")
public class TypeOfCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(min = 5, max = 40)
    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "typeOfCenter")
    private List<HealthCenter> healthCenters;
}
