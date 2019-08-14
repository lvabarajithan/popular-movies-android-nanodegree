package com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.popularmovies.R;
import com.popularmovies.model.Trailer;
import com.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {

    private List<Trailer> trailers;
    private Context context;

    private OnClickListener<Trailer> listener;

    public TrailersAdapter(Context context, OnClickListener<Trailer> listener) {
        this.context = context;
        this.trailers = new ArrayList<>();
        this.listener = listener;
    }

    public void setData(List<Trailer> trailers) {
        this.trailers = trailers;
        this.notifyDataSetChanged();
    }

    public List<Trailer> getData() {
        return this.trailers;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_details_movie_trailer, viewGroup, false);
        return new TrailerHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int i) {
        Trailer trailer = trailers.get(holder.getAdapterPosition());
        holder.trailerName.setText(trailer.getName());
        Glide.with(context)
                .load(Constants.YOUTUBE_IMG_PREFIX + trailer.getVideoId() + Constants.YOUTUBE_IMG_SUFFIX)
                .error(R.drawable.ic_cloud_off)
                .into(holder.trailerImg);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailerHolder extends RecyclerView.ViewHolder {
        AppCompatImageView trailerImg;
        AppCompatTextView trailerName;

        TrailerHolder(@NonNull View itemView, final OnClickListener<Trailer> listener) {
            super(itemView);
            trailerImg = itemView.findViewById(R.id.item_details_movie_trailer_img);
            trailerName = itemView.findViewById(R.id.item_details_movie_trailer_name);
            itemView.findViewById(R.id.item_details_movie_trailer_root)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listener.onClick(trailers.get(getAdapterPosition()));
                        }
                    });
        }
    }

}
