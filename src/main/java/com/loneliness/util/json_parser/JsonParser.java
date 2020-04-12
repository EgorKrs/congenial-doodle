package com.loneliness.util.json_parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;


import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper objectMapper =new ObjectMapper();
    {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    public static String mapToJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }
}
