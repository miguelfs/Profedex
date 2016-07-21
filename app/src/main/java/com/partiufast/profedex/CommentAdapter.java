package com.partiufast.profedex;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.partiufast.profedex.data.Comment;

import java.util.List;

/**
 * Created by lgos on 20/07/16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView userName;
        TextView commentText;
        ImageView profilePicture;

        CommentViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            userName = (TextView)itemView.findViewById(R.id.user_name);
            commentText = (TextView)itemView.findViewById(R.id.comment_text);
            profilePicture = (ImageView)itemView.findViewById(R.id.profile_picture);
        }
    }

    List<Comment> comments;

    public CommentAdapter(List<Comment> comments){
        this.comments = comments;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment, viewGroup, false);
        CommentViewHolder pvh = new CommentViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder commentViewHolder, int i) {
        commentViewHolder.userName.setText(comments.get(i).getUserName());
        commentViewHolder.commentText.setText(comments.get(i).getText());
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
