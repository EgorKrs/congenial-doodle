package com.loneliness.util.search;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Repository
public class GenericSearcher implements Searcher {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> List<T> search(final List<SearchCriteria> params,Class<T> tClass) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> query = builder.createQuery(tClass);

        Predicate predicate = builder.conjunction();
        SearchQueryCriteriaConsumer searchConsumer = new SearchQueryCriteriaConsumer(predicate, builder, query.from(tClass));
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }
}
