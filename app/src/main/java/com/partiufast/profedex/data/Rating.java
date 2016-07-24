package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgos on 23/07/16.
 */
public class Rating {
    @SerializedName("rating_id")
    private int mID;

    @SerializedName("rating_type_name")
    private String mName;

    @SerializedName("rating_value")
    private float mValue;

    @SerializedName("rating_type_id")
    private int mRatingTypeID;

    private float mUserRating;

    public Rating(int mID, String mName, float mValue, int mRatingTypeID) {
        this.mID = mID;
        this.mName = mName;
        this.mValue = mValue;
        this.mRatingTypeID = mRatingTypeID;
    }

    public float getValue() {
        return mValue;
    }

    public String getName() {
        return mName;
    }

    public int getRatingTypeID() {
        return mRatingTypeID;
    }

    public float getUserRating() {
        return mUserRating;
    }

    public void setUserRating(float userRating) {
        this.mUserRating = userRating;
    }

}
