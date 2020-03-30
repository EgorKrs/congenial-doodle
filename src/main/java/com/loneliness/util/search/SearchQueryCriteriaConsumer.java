package com.loneliness.util.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;

public class SearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root r;

    public SearchQueryCriteriaConsumer(Predicate predicate, CriteriaBuilder builder, Root r) {
        super();
        this.predicate = predicate;
        this.builder = builder;
        this.r= r;
    }

    @Override
    public void accept(SearchCriteria param) {

        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder.greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("=")) {
            predicate = builder.and(predicate, builder.equal(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
                predicate = builder.and(predicate, builder.like(r.get(param.getKey()), "%" + param.getValue() + "%"));
        }
    }

    public Predicate getPredicate() {
        return predicate;
    }
}

