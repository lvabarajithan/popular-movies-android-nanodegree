package com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Abarajithan
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {


    public TrailersAdapter(Context context) {

    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder trailerHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class TrailerHolder extends RecyclerView.ViewHolder {

        public TrailerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
