package com.dawan.gquizz.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
	public static ObjectMapper objectMapper = new ObjectMapper(); // Création d'une instance statique d'ObjectMapper

	public static String serialize(String string) throws Exception {
	    // Configure ObjectMapper pour ne pas échouer sur des propriétés inconnues lors de la désérialisation
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    // Sérialiser la chaîne en JSON
	    String jsonString = objectMapper.writeValueAsString(string);

	    // Afficher la chaîne JSON dans la console
	    System.out.println(jsonString);

	    return jsonString; // Retourner la chaîne JSON
	}

	public static <TDestination> TDestination deserialize(String string, Class<TDestination> destinationClass) throws Exception {
	    // Configure ObjectMapper pour ne pas échouer sur des propriétés inconnues lors de la désérialisation
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	    // Désérialiser la chaîne JSON en un objet Java du type spécifié
	    return objectMapper.readValue(string, destinationClass);
	}

}