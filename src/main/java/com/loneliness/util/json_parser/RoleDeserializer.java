package com.loneliness.util.json_parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.loneliness.entity.Role;

import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final String jsonValue = parser.getText();
        for (final Role role : Role.values())
        {
            if (role.getAuthority().equals(jsonValue))
            {
                return role;
            }
        }
        return Role.UNKNOWN;
    }
}
