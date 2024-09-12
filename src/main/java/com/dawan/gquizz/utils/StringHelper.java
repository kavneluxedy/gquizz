package com.dawan.gquizz.utils;

import java.util.Arrays;
import java.util.List;

public class StringHelper {
    // Méthode pour convertir une liste de chaînes en une chaîne avec séparateur
    public static String convert(List<String> list, String separator) {
        return String.join(separator, list);
    }

    // Méthode pour convertir une chaîne avec séparateur en une liste de chaînes
    public static List<String> convert(String string, String separator) {
        return Arrays.asList(string.split(separator));
    }
}
