package com.immobiler.ProjectGeoInfo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceResponseDTO {
    private Long id;
    private double surface;
    private double prix;
    private String description;
    private String location; // WKT format
    private String communeName;
    private String typeBienName;
    private String typeOperationName;
    private String statutName;
    private LocalDate dateOffre;
}
