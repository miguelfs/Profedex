package com.partiufast.profedex;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.fragments.TeacherInfoFragment;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.teacherItemViewHolder> {
    ArrayList<Professor> mProfessorList;
    Context mContext;

    public class teacherItemViewHolder extends RecyclerView.ViewHolder {
        public Button mTeacherNameButton;
        public ImageView mPictureTeacherImageView;

        public teacherItemViewHolder(View itemView) {
            super(itemView);
            mTeacherNameButton = (Button) itemView.findViewById(R.id.teacherNameButton);
            mPictureTeacherImageView = (ImageView) itemView.findViewById(R.id.pictureImageView);
            mContext = itemView.getContext();
        }
    }

    public TeacherAdapter(ArrayList<Professor> professorList) {
        mProfessorList = professorList;
    }

        @Override
        public teacherItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.professor_item, parent, false);
            return new teacherItemViewHolder(itemView);
        }


        @Override
    public void onBindViewHolder(teacherItemViewHolder holder, final int position) {
            String string =  mProfessorList.get(position).getID() + ": " + mProfessorList.get(position).getName();
            Bitmap bitmap = mProfessorList.get(position).getBitmapProfessorPicture();
            final Professor p = mProfessorList.get(position);
            holder.mTeacherNameButton.setText(string);
            if (bitmap != null) {
                holder.mPictureTeacherImageView.setImageBitmap(bitmap);
            } else {
                holder.mPictureTeacherImageView.setImageResource(R.drawable.walter_1);
            }
            holder.mTeacherNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TeacherInfoFragment mTeacherInfoFragment = TeacherInfoFragment.newInstance(p);
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