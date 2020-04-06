package com.loneliness.entity;

public enum  Role {
    USER, ADMIN,UNKNOWN;
    public String getRole(){
        return name();
    }
}
