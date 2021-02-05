package com.atlas.library.bookmanagement.configuration.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Slf4j
public class QuerySpecification<T> implements Specification<T> {

    private final QueryCriteria queryCriteria;

    public QuerySpecification(final QueryCriteria queryCriteria) {
        super();
        this.queryCriteria = queryCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<String> arguments = queryCriteria.getArguments();
        Object arg = arguments.get(0);

        switch (queryCriteria.getSearchOperation()) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(queryCriteria.getKey()), arg);
            case IN:
                return root.get(queryCriteria.getKey()).in(arguments);
            default:
                return null;
        }
    }
}
