package com.loneliness.service;

import com.loneliness.util.search.SearchCriteria;
import com.loneliness.util.search.Searcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SearchService {

    private final Searcher searcher;


    public SearchService(@Qualifier("genericSearcher") Searcher search){
        this.searcher=search;
    }

    public <T>List<T> search(List<SearchCriteria> params, Class<T> tClass){
        return searcher.search(params, tClass );
    }
    public <T>List<T> search(SearchCriteria[] params, Class<T> tClass){
        return searcher.search(params, tClass );
    }

}
