package com.loneliness.util.json_parser;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.codehaus.jackson.JsonNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CustomAuthorityDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        //ObjectMapper mapper = (ObjectMapper) jp.getCodec();

        JsonNode jsonNode = jp.getCodec().readTree(jp);
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();

        Iterator<JsonNode> elements = jsonNode.getElements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
        }
        return grantedAuthorities;
    }
}