package com.popularmovies.adapter;

import android.view.View;

/**
 * Created by Abarajithan
 */
public interface OnClickListener<T> {
    public void onClick(View root, T data);
}
