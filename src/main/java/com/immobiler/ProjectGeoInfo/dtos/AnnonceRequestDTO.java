package com.immobiler.ProjectGeoInfo.dtos;

import lombok.Data;

@Data
public class AnnonceRequestDTO {
    private double surface;
    private double prix;
    private String description;

    // WKT string for location instead of Point
    private String location;

    // IDs or names for related entities
    private Long citoyenId;        // ID of the citizen who created the announcement
    private Long communeId;        // ID of the commune
    private String typeBienName;   // Name of the type of property
    private String typeOperationName; // Name of the type of operation
    private String statutName;     // Name of the status (e.g., Disponible, Réservé)
}
