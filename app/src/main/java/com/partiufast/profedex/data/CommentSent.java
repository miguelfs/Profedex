package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgos on 27/07/16.
 */
public class CommentSent extends Message{
    @SerializedName("id")
    int mID;

    public CommentSent(boolean error, String message, int mID) {
        super(error, message);
        this.mID = mID;
    }

    public int getID() {
        return mID;
    }
}
