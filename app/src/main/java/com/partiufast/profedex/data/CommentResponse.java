package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgos on 20/07/16.
 * This class is only used to de-serialize JSON
 */
public class CommentResponse {
    @SerializedName("comments")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }
}
