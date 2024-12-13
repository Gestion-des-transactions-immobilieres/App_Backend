package com.immobiler.ProjectGeoInfo.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // Store the enum as a String in the database
    @Column(nullable = false, unique = true)
    private OperationType operationType;

    // No longer need a relation with "Annonce"
}
