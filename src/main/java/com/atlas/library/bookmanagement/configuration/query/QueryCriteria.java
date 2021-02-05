package com.atlas.library.bookmanagement.configuration.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryCriteria {
    private String key;
    private QueryOperation searchOperation;
    private List<String> arguments;

    public enum QueryOperation {
        EQUALS, IN
    }
}
