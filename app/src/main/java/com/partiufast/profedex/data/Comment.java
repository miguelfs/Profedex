package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lgos on 20/07/16.
 */
public class Comment implements Serializable {

    @SerializedName("comment_id")
    private int mID;

    @SerializedName("comment_text")
    private String mText;

    @SerializedName("comment_date")
    private String mDate;

    @SerializedName("professor_id")
    private int mProfessorID;

    @SerializedName("user_id")
    private int mUserID;

    @SerializedName("user_name")
    private String mUserName;

    @SerializedName("vote")
    private int mVote;

    public Comment(int mID, String mText, String mDate, int mProfessorID, int mUserID,
                   String mUserName, int mVote) {
        this.mID = mID;
        this.mText = mText;
        this.mDate = mDate;
        this.mProfessorID = mProfessorID;
        this.mUserID = mUserID;
        this.mUserName = mUserName;
        this.mVote = mVote;
    }

    public int getID() {
        return mID;
    }
    public String getText() {
        return mText;
    }
    public String getUserName() {
        return mUserName;
    }
}
