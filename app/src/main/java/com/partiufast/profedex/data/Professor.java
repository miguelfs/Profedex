package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

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
    private List<String> mClassesList =  new ArrayList<String>();
    @SerializedName("didactic_level")
    private int mDidacticLevel;
    @SerializedName("difficulty_level")
    private int mDifficultyLevel;


    public Professor(int id, String name, String description, String professorRoom, String professorEmail, List<String> classesList, int didacticLevel, int difficultyLevel) {
        mID = id;
        mName = name;
        mDescription = description;
        mProfessorRoom = professorRoom;
        mProfessorEmail = professorEmail;
        mClassesList = classesList;
        mDidacticLevel = didacticLevel;
        mDifficultyLevel = difficultyLevel;
    }

    public int getDifficultyLevel() {
        return mDifficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        mDifficultyLevel = difficultyLevel;
    }

    public String getName() {
        return mName;
    }
///
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

    public List<String> getClassesList() {
        return mClassesList;
    }

    public void setClassesList(List<String> classesList) {
        mClassesList = classesList;
    }

    public int getDidacticLevel() {
        return mDidacticLevel;
    }

    public void setDidacticLevel(int didacticLevel) {
        mDidacticLevel = didacticLevel;
    }

    public String getProfessorEmail() {
        return mProfessorEmail;
    }

    public void setProfessorEmail(String mProfessorEmail) {
        this.mProfessorEmail = mProfessorEmail;
    }
}
