package com.partiufast.profedex;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.partiufast.profedex.data.Comment;
import com.partiufast.profedex.data.Professor;
import com.partiufast.profedex.data.Rating;
import com.partiufast.profedex.fragments.TeacherInfoFragment;
import com.partiufast.profedex.fragments.TeacherListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgos on 20/07/16.
 */
public class InfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int COMMENT = 0, HEADER = 1;
    private TeacherInfoFragment mfragment;

    public class ProfessorViewHolder extends RecyclerView.ViewHolder {

        private TextView profName;
        private TextView profDescription;
        private ImageView profImg;

        public ProfessorViewHolder(View v) {
            super(v);
            profName = (TextView) itemView.findViewById(R.id.teacher_name_text_view);
            profDescription = (TextView) itemView.findViewById(R.id.description_teacher_text_view);
            profImg = (ImageView) itemView.findViewById(R.id.professor_picture);
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView userName;
        TextView commentText;
        ImageView profilePicture;
        ImageButton thumbsUpButton;
        ImageButton thumbsDownButton;
        TextView commentVote;

        CommentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            userName = (TextView)itemView.findViewById(R.id.user_name);
            commentText = (TextView)itemView.findViewById(R.id.comment_text);
            commentVote = (TextView)itemView.findViewById(R.id.votes);
            profilePicture = (ImageView)itemView.findViewById(R.id.profile_picture);
            thumbsDownButton = (ImageButton)itemView.findViewById(R.id.button_down);
            thumbsUpButton= (ImageButton)itemView.findViewById(R.id.button_up);

        }
    }


    List<Object> items;

    public InfoAdapter(List<Object> comments, TeacherInfoFragment fragment){
        this.items = comments;
        this.mfragment = fragment;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Professor) {
            return HEADER;
        } else if (items.get(position) instanceof Comment) {
            return COMMENT;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case HEADER:
                View v1 = inflater.inflate(R.layout.professor_header, viewGroup, false);
                viewHolder = new ProfessorViewHolder(v1);
                break;
            case COMMENT:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment, viewGroup, false);
                viewHolder = new CommentViewHolder(v);
                break;
            default:
                View sv = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment, viewGroup, false);
                viewHolder = new CommentViewHolder(sv);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)  {
        switch (viewHolder.getItemViewType()) {
            case HEADER:
                Professor p = (Professor)items.get(position);
                ProfessorViewHolder vh = (ProfessorViewHolder)viewHolder;
                vh.profName.setText(p.getName());
                vh.profDescription.setText(p.getDescription());
                break;
            case COMMENT:
                final Comment c = (Comment)items.get(position);
                final CommentViewHolder commentViewHolder = (CommentViewHolder) viewHolder;
                commentViewHolder.userName.setText(c.getUserName());
                commentViewHolder.commentText.setText(c.getText());
                commentViewHolder.commentVote.setText(""+c.getVote());

                commentViewHolder.thumbsUpButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mfragment.sendCommentVote(c,1);
                        commentViewHolder.commentVote.setText(String.valueOf(c.getVote()+1));
                    }
                });
                commentViewHolder.thumbsDownButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mfragment.sendCommentVote(c,-1);
                        commentViewHolder.commentVote.setText(String.valueOf(c.getVote()-1));
                    }
                });
                break;
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
