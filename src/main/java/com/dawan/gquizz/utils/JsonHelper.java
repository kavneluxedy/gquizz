package com.dawan.gquizz.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String serialize(String string) throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Sérialiser la chaîne en JSON
        String jsonString = objectMapper.writeValueAsString(string);

        // Afficher la chaîne JSON
        System.out.println(jsonString);

        return jsonString;
    }

    public static <TDestination> TDestination deserialize(String string, Class<TDestination> destinationClass) throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Désérialiser le JSON en object JAVA
        return objectMapper.readValue(string, destinationClass);
    }
}