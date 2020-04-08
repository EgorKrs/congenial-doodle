package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Domain;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class CRUDService <T extends Domain> implements Service<T>{

    protected JpaRepository<T,Integer> repository;

    protected  SearchService searchService;

    public Integer saveId(T note){
        return repository.save(note).getId();
    }

    public T save(T note){
        return repository.save(note);
    }

    public void delete(T note){
        repository.delete(note);
    }

    public void delete(int id){
        repository.deleteById(id);
    }

    public Optional<T> findById(Integer id){
        return repository.findById(id);
    }

    public List<T> findAll(String property){
        return  repository.findAll(Sort.by(property));
    }

    public List<T> findAll(){
        return  repository.findAll();
    }

    public List<T> search(List<SearchCriteria> params,Class<T> tClass){
      return searchService.search(params, tClass );
    }

    public List<T> search(SearchCriteria[] params,Class<T> tClass){
        return searchService.search(params, tClass );
    }

}
