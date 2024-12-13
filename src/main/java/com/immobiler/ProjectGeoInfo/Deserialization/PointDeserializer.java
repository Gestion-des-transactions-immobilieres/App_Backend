package com.immobiler.ProjectGeoInfo.Deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;  // Importer JsonNode
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;

import java.io.IOException;

public class PointDeserializer extends JsonDeserializer<Point> {

    @Override
    public Point deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Lire le JSON et obtenir les valeurs des coordonnées x et y
        JsonNode node = p.getCodec().readTree(p);  // Utiliser JsonNode pour lire le JSON
        double x = 0;
        double y = 0;

        // Accéder aux valeurs "x" et "y" dans le JSON
        if (node.has("x")) {
            x = node.get("x").asDouble();
        }
        if (node.has("y")) {
            y = node.get("y").asDouble();
        }

        // Créer un Point avec les coordonnées x et y
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coord = new Coordinate(x, y);
        return geometryFactory.createPoint(coord);
    }
}
