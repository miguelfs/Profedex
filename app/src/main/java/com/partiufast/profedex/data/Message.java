package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lgos on 06/07/16.
 */
public class Message {
    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String mMessage;

    public boolean isError() { return error;}

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public Message(boolean error, String message) {
        this.error = error;
        this.mMessage = message;
    }
}
