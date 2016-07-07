package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lgos on 30/06/16.
 */
public class User extends Message implements Serializable {
    @SerializedName("user_id")
    int mId;

    @SerializedName("user_name")
    String mName;

    @SerializedName("user_email")
    String mEmail;

    @SerializedName("api_key")
    String mApiKey;

    @SerializedName("created_at")
    String mCreatedAt;

    public User(boolean error, String message, int id, String name, String email, String apiKey, String createdAt)
    {
        super(error, message);
        this.mId = id;
        this.mName = name;
        this.mEmail = email;
        this.mApiKey = apiKey;
        this.mCreatedAt = createdAt;
    }

    public User(User user) {
        super(user.isError(), user.getMessage());
        this.error = user.isError();
        this.mId = user.getId() ;
        this.mName = user.getName();
        this.mEmail = user.getEmail();
        this.mApiKey = getApiKey();
        this.mCreatedAt = getCreatedAt();
    }

    public User() {
        super(true, "");
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String mApiKey) {
        this.mApiKey = mApiKey;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }
}
