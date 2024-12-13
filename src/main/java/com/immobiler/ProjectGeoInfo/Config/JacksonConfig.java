package com.immobiler.ProjectGeoInfo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.immobiler.ProjectGeoInfo.Deserialization.PointDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.locationtech.jts.geom.Point;


@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

        // Enregistrer le deserializer personnalisé pour le type Point
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Point.class, new PointDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
}
