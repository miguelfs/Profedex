package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgos on 30/06/16.
 */
public class User {
    @SerializedName("user_id")
    int mId;

    @SerializedName("user_name")
    String mName;

    public User(int id, String name ) {
        this.mId = id;
        this.mName = name;
    }
}
