package com.atlas.library.bookmanagement.configuration.query;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.atlas.library.bookmanagement.configuration.query.QueryCriteria.QueryOperation.EQUALS;
import static com.atlas.library.bookmanagement.configuration.query.QueryCriteria.QueryOperation.IN;

public class QuerySpecificationsBuilder<T> {

    private final List<QueryCriteria> queryCriteriaList = new ArrayList<>();

    public final QuerySpecificationsBuilder<T> with(final String key, final List<String> arguments) {
        if (!isEmpty(arguments)) {
            queryCriteriaList.add(arguments.size() > 1 ? new QueryCriteria(key, IN, arguments) : new QueryCriteria(key, EQUALS, arguments));
        }
        return this;
    }

    public Specification<T> build() {
        Specification<T> specification = null;

        if (!queryCriteriaList.isEmpty()) {
            specification = new QuerySpecification<>(queryCriteriaList.get(0));

            for (int index = 1; index < queryCriteriaList.size(); ++index) {
                QueryCriteria queryCriteria = this.queryCriteriaList.get(index);
                specification = Specification.where(specification).and(new QuerySpecification<>(queryCriteria));
            }
        }
        return specification;
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
