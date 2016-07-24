package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgos on 23/07/16.
 */
public class RatingResponse {
    @SerializedName("rating")
    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }
}
