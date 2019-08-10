package com.popularmovies;

import java.util.List;

/**
 * Created by Abarajithan
 */
public interface OnLoadListener<T> {
    public void onLoad(List<T> list);
}
