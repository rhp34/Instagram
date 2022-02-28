package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView rvPosts;
    List<Post> posts;
    TimelineAdapter adapter;

    public static final String TAG = "TimelineActivity";
    private final int REQUEST_CODE = 905;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        rvPosts = findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        adapter = new TimelineAdapter(this, posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(adapter);

        populateTimeline();

    }

    private void populateTimeline() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "issue getting posts", e);
                    return;
                }
                for(Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                    adapter.clear();
                    adapter.addAll(posts);
                }
            }
        });
    }
}