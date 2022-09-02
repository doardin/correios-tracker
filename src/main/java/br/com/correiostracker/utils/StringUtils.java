package br.com.correiostracker.utils;

public class StringUtils {
    
    public static String capitalizeFirstLetter(String value){
        value = value.toLowerCase();
        value = value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
        return value;
    }

}
