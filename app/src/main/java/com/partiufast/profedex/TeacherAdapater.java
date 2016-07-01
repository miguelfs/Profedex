package com.partiufast.profedex;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.fragments.TeacherInfoFragment;

import java.util.ArrayList;

public class TeacherAdapater extends RecyclerView.Adapter<TeacherAdapater.teacherItemViewHolder> {
    ArrayList<Professor> mProfessorList;
    Context mContext;

    public class teacherItemViewHolder extends RecyclerView.ViewHolder {
        public Button mTeacherNameButton;

        public teacherItemViewHolder(View itemView) {
            super(itemView);
            mTeacherNameButton = (Button) itemView.findViewById(R.id.teacherNameButton);
            mContext = itemView.getContext();
        }
    }

    public TeacherAdapater( ArrayList<Professor> professorList) {
        mProfessorList = professorList;
    }

        @Override
        public teacherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_item, parent, false);
            return new teacherItemViewHolder(itemView);
        }


        @Override
    public void onBindViewHolder( teacherItemViewHolder holder, int position) {
            String string = (position+1) + ": " + mProfessorList.get(position).getName();
            holder.mTeacherNameButton.setText(string);
            holder.mTeacherNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeacherInfoFragment mTeacherInfoFragment = new TeacherInfoFragment();
                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, 0, R.anim.slide_in_right, 0 )
                            .replace(R.id.flContent, mTeacherInfoFragment )
                            .addToBackStack("fragment")
                            .commit();
                }
            });
    }

    @Override
    public int getItemCount() {
        return mProfessorList.size();
    }


}
