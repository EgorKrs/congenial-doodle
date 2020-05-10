package com.loneliness.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.loneliness.util.json_parser.RoleDeserializer;
import com.loneliness.util.json_parser.RoleSerializer;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSerialize(using = RoleSerializer.class)
@JsonDeserialize(using = RoleDeserializer.class)
public enum Role implements GrantedAuthority {

    USER, ADMIN, UNKNOWN;

     @Override
    public String getAuthority() {
        return name();
    }

    public Role getRole(String roleName){
        for (final Role role : Role.values())
        {
            if (role.getAuthority().equals(roleName))
            {
                return role;
            }
        }
        return null;
    }

}
