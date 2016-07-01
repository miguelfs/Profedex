package com.partiufast.profedex.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by lgos on 30/06/16.
 */
public class ProfessorResponse {
    @SerializedName("professors")
    private List<Professor> professors;

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
}
