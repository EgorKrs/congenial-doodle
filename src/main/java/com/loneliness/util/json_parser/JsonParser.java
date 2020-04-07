package com.loneliness.util.json_parser;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class JsonParser {
    private static final ObjectMapper objectMapper =new ObjectMapper();
    public static String mapToJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
    public static <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return objectMapper.readValue(json, clazz);
    }
}
