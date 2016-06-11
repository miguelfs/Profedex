package com.partiufast.profedex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miguel on 16/04/2016.
 */
public class TeacherAdapater extends RecyclerView.Adapter<TeacherAdapater.teacherItemViewHolder> {
    ArrayList<Teacher> mTeacherList;

    public class teacherItemViewHolder extends RecyclerView.ViewHolder {
        public TextView mTeacherNameTextView;

        public teacherItemViewHolder(View itemView) {
            super(itemView);
            mTeacherNameTextView = (TextView) itemView.findViewById(R.id.teacherNameTextView);
        }
    }

    public TeacherAdapater( ArrayList<Teacher> teacherList) {
        mTeacherList = teacherList;
    }

        @Override
        public teacherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_item, parent, false);
            return new teacherItemViewHolder(itemView);
        }


        @Override
    public void onBindViewHolder( teacherItemViewHolder holder, int position) {
            String string = (position+1) + ": " + mTeacherList.get(position).getName();
            holder.mTeacherNameTextView.setText(string);
    }

    @Override
    public int getItemCount() {
        return mTeacherList.size();
    }


}
