package com.partiufast.profedex;

import java.util.List;

public class Teacher {
    private String mName;
    private String mDescription;
    private String mProfessorRoom; //sala do professor
    private List<String> mClassesList; //aulas que o professor dá
    private int mDidacticLevel;
    private int mDifficultyLevel;

    public Teacher(String name, String description, String professorRoom, List<String> classesList, int didacticLevel, int difficultyLevel) {
        mName = name;
        mDescription = description;
        mProfessorRoom = professorRoom;
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
}
