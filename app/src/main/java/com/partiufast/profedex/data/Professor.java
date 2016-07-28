package com.partiufast.profedex.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.ScriptGroup;

import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Professor implements Serializable {

    @SerializedName("professor_id")
    private int mID;
    @SerializedName("professor_name")
    private String mName;
    @SerializedName("professor_description")
    private String mDescription;
    @SerializedName("professor_room")
    private String mProfessorRoom;
    @SerializedName("professor_email")
    private String mProfessorEmail;
    @SerializedName("class_list")
    private List<String> mClassesList =  new ArrayList<>();
    @SerializedName("professor_picture")
    private String professorPicture;
    @SerializedName("professor_picture_inputstream")
    private InputStream mProfessorPictureInputStream;
    private List<String> mPictureList =  new ArrayList<>();
    public Professor(int id, String name, String description, String professorRoom, String professorEmail, List<String> classesList, InputStream pictureStream) {
        mID = id;
        mName = name;
        mDescription = description;
        mProfessorRoom = professorRoom;
        mProfessorEmail = professorEmail;
        mClassesList = classesList;
        mProfessorPictureInputStream = pictureStream;
    }

    public  int getID() { return mID; };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getProfessorRoom() {
        return mProfessorRoom;
    }

    public void setProfessorRoom(String professorRoom) {
        mProfessorRoom = professorRoom;
    }

    public String getProfessorPicture() {
        return professorPicture;
    }

    public List<String> getClassesList() {
        return mClassesList;
    }

    public void setClassesList(List<String> classesList) {
        mClassesList = classesList;
    }

    public String getProfessorEmail() {
        return mProfessorEmail;
    }

    public void setProfessorEmail(String mProfessorEmail) {
        this.mProfessorEmail = mProfessorEmail;
    }

    public InputStream getProfessorPictureInputStream() {
        return mProfessorPictureInputStream;
    }

    public void setProfessorPictureInputStream(InputStream professorPictureInputStream) {
        mProfessorPictureInputStream = professorPictureInputStream;
    }

    public Bitmap getBitmapProfessorPicture(){
        return BitmapFactory.decodeStream(mProfessorPictureInputStream);
    }
}
