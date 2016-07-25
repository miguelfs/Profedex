package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgos on 25/07/16.
 */
public class PicturePath {

    @SerializedName("picture_path")
    public String picturePath;

    public String getPicturePath() {
        return picturePath;
    }
}
