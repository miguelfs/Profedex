package com.partiufast.profedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 16/04/2016.
 */
public class TeacherAdapater extends RecyclerView.Adapter<TeacherAdapater.TeacherViewHolder> {
    ArrayList<String> mTeacherList;

    public TeacherAdapater( ArrayList<String> teacherList) {
        mTeacherList = teacherList;
    }

    @Override
    public TeacherAdapater.TeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_layout, parent, false);
        TeacherViewHolder viewHolder = new TeacherViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeacherViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{

        public TeacherViewHolder(View itemView) {
            super(itemView);
        }
    }

}
