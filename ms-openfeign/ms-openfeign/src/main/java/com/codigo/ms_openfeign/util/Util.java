package com.codigo.ms_openfeign.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String convertirAString(T objeto){
        try{
            return OBJECT_MAPPER.writeValueAsString(objeto);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir la CLase de Cadena(String) : "+e);
        }
    }

    public static <T> T convertirDesdeString(String json, Class<T> tipoclase){
        try {
            return OBJECT_MAPPER.readValue(json,tipoclase);
        }catch (JsonProcessingException e){
            throw new RuntimeException("Error al convertir desde String a la clase: "+e);
        }
    }

}
