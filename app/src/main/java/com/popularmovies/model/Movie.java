package com.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abarajithan
 */
@Entity(tableName = "movies")
public class Movie implements Parcelable {

    @Expose
    @PrimaryKey
    private long id;
    @Expose
    private String title;
    @Expose
    @SerializedName("backdrop_path")
    private String headerUrl;
    @Expose
    @SerializedName("poster_path")
    private String imageUrl;
    @Expose
    @SerializedName("overview")
    private String synopsis;
    @Expose
    @SerializedName("vote_average")
    private double rating;
    @Expose
    @SerializedName("release_date")
    private String releaseDate;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] posterImage;

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
        int bytes = in.readInt();
        if (bytes != 0) {
            byte posterImage[] = new byte[bytes];
            in.readByteArray(posterImage);
        }
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

    public void setId(long id) {
        this.id = id;
    }

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
        return headerUrl;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setPosterImage(byte[] posterImage) {
        this.posterImage = posterImage;
    }

    public byte[] getPosterImage() {
        return posterImage;
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
        dest.writeInt(posterImage != null ? posterImage.length : 0);
        dest.writeByteArray(posterImage);
    }
}
