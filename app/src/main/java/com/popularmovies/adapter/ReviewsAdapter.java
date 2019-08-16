package com.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.R;
import com.popularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private List<Review> reviews;

    public ReviewsAdapter() {
        this.reviews = new ArrayList<>();
    }

    public void setData(List<Review> reviews) {
        this.reviews = reviews;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reviews_list, viewGroup, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        Review review = reviews.get(holder.getAdapterPosition());
        holder.author.setText(review.getAuthor());
        holder.content.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView author;
        AppCompatTextView content;

        ReviewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.item_review_author);
            content = itemView.findViewById(R.id.item_review_content);
        }
    }

}
