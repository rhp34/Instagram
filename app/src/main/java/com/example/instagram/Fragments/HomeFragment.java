package com.example.instagram.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagram.Post;
import com.example.instagram.R;
import com.example.instagram.TimelineAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView rvPosts;
    List<Post> posts;
    TimelineAdapter adapter;
    ActionBar actionBar;

    public static final String TAG = "TimelineActivity";
    private final int REQUEST_CODE = 905;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        posts = new ArrayList<>();
        adapter = new TimelineAdapter(getContext(), posts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(layoutManager);
        rvPosts.setAdapter(adapter);

        populateTimeline();
    }

    private void populateTimeline() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.whereContainedIn(Post.KEY_USER, Collections.singletonList(ParseUser.getCurrentUser()));
        query.include(Post.KEY_USER);
        query.addDescendingOrder(Post.KEY_CREATEDAT);
        query.setLimit(20);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "issue getting posts", e);
                    return;
                }
                for(Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername() + ", date created: " + post.getCreatedAt());
                };
                adapter.clear();
                adapter.addAll(posts);
            }
        });
    }
}