package edu.sabanciuniv.aleynakutukhomework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentsViewHolder>{

    List<CommentItem> commentItems;
    Context context;

    public CommentAdapter(List<CommentItem> commentItems, Context context) {
        this.commentItems = commentItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.comments_layout,parent,false);
        return new CommentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        holder.txtCommenterName.setText(commentItems.get(position).getName());
        holder.txtCommenterMsg.setText(commentItems.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return commentItems.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView txtCommenterName;
        TextView txtCommenterMsg;
        ConstraintLayout root;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCommenterMsg = itemView.findViewById(R.id.txtcommentermsg);
            txtCommenterName = itemView.findViewById(R.id.txtcommentername);
            root = itemView.findViewById(R.id.containerComment);
        }
    }
}
