package com.loneliness.dto;

import com.loneliness.entity.domain.Domain;

public interface DTO  <R extends Domain>{
    R fromDTO();
}
