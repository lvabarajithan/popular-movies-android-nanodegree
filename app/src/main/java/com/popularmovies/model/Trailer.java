package com.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abarajithan
 */
public class Trailer {

    private String name;
    @SerializedName("key")
    private String videoId;

    public Trailer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
