package com.loneliness.service;

import com.loneliness.entity.domain.Book;
import com.loneliness.entity.domain.Domain;
import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class CRUDService <T extends Domain>{

    protected JpaRepository<T,Integer> repository;

    protected  Searcher searcher;

    public int save(T note){
        return repository.save(note).getId();
    }
    public void delete(T note){
        repository.delete(note);
    }
    public void delete(int id){
        repository.deleteById(id);
    }
    public Optional<T> find(int id){
        return repository.findById(id);
    }
    public List<T> findAll(String property){
        return  repository.findAll(Sort.by(property));
    }
    public List<T> findAll(){
        return  repository.findAll();
    }

    public List<T> search(List<SearchCriteria> params,Class<T> tClass){
      return searcher.search(params, tClass );
    }

}
