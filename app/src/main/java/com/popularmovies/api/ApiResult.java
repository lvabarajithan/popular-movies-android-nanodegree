package com.popularmovies.api;

import java.util.List;

/**
 * Created by Abarajithan
 */
public class ApiResult<T> {

    private List<T> results;

    public ApiResult() {
    }

    public List<T> getResults() {
        return results;
    }
}
