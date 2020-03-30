package com.loneliness.util.search;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;


    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }
}
