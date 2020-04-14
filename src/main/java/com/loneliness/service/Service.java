package com.loneliness.service;

import com.loneliness.util.search.SearchCriteria;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface Service <T>{

    Integer saveAndReturnId(T note);

    public T save(T note);

    public void delete(T note);

    public void delete(int id);

    public Optional<T> findById(Integer id);

    public List<T> findAll(String property);

    public List<T> findAll();

    public List<T> search(List<SearchCriteria> params, Class<T> tClass);

    public List<T> search(SearchCriteria[] params,Class<T> tClass);
}
