package com.example.instagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder>{

    Context context;
    List<Post> posts;

    public TimelineAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        try {
            holder.bind(post);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> postList) {
        posts.addAll(postList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUsername;
        ImageView ivPost;
        EditText etCaption;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPost = itemView.findViewById(R.id.ivPost);
            etCaption = itemView.findViewById(R.id.etDescription);
        }

        public void bind(Post post) throws ParseException {
            tvUsername.setText(post.getUser().getUsername());
            etCaption.setText(post.getDescription());
            Glide.with(context).load(post.getImage().getFile()).into(ivPost);
        }

    }
}
