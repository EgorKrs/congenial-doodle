package com.loneliness.dto;

import com.loneliness.util.search.SearchCriteria;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class SearchCriteriaDTO {
    @NotBlank
    private String key;
    @NotBlank
    private String operation;
    @NotNull
    private Object value;
    public SearchCriteria fromDTO(){
        return SearchCriteria.builder().value(value).operation(operation).key(key).build();
    }
}
