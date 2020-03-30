package com.loneliness.util.search;

import com.loneliness.util.search.SearchCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Searcher {
     <T>List<T> search(final List<SearchCriteria> params,final Class<T> tClass);

}
