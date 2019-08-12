package com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.popularmovies.util.Constants;

/**
 * Created by Abarajithan
 */
public class Movie implements Parcelable {

    private long id;
    private String title;
    @SerializedName("backdrop_path")
    private String headerUrl;
    @SerializedName("poster_path")
    private String imageUrl;
    @SerializedName("overview")
    private String synopsis;
    @SerializedName("vote_average")
    private double rating;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie() {
    }

    protected Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        headerUrl = in.readString();
        imageUrl = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getHeaderUrl() {
        return Constants.IMAGE_URL_PREFIX + headerUrl;
    }

    public String getImageUrl() {
        return Constants.IMAGE_URL_PREFIX + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(headerUrl);
        dest.writeString(imageUrl);
        dest.writeString(synopsis);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
    }
}
