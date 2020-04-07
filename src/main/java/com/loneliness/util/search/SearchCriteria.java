package com.loneliness.util.search;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
public class SearchCriteria {
    @NotBlank
    private String key;
    @NotBlank
    private String operation;
    @NotNull
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
