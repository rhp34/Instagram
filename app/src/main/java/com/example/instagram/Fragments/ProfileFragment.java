package com.example.instagram.Fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagram.EditProfileActivity;
import com.example.instagram.LoginActivity;
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

public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileActivity";
    private final int REQUEST_CODE = 45;
    RecyclerView rvGridView;
    ImageView ivProfilePic;
    TextView tvPostsCount;
    TextView tvFollowerCount;
    TextView tvFollowingCount;
    TextView tvPosts;
    TextView tvFollowers;
    TextView tvFollowing;
    TextView tvProfileDescription;
    Button btnEditProfile;
    Button btnSignout1;
    List<Post> posts;
    TimelineAdapter adapter;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivProfilePic = view.findViewById(R.id.ivProfilePic);
        tvPostsCount = view.findViewById(R.id.tvPostsCount);
        tvFollowerCount = view.findViewById(R.id.tvFollowerCount);
        tvFollowingCount = view.findViewById(R.id.tvFollowingCount);
        tvPosts = view.findViewById(R.id.tvPosts);
        tvFollowers = view.findViewById(R.id.tvFollowers);
        tvFollowing = view.findViewById(R.id.tvFollowing);
        tvProfileDescription = view.findViewById(R.id.tvProfileDescription);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnSignout1 = view.findViewById(R.id.btnSignout1);
        rvGridView = view.findViewById(R.id.rvGridView);
        posts = new ArrayList<>();
        adapter = new TimelineAdapter(getContext(), posts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), GridLayoutManager.VERTICAL);
        rvGridView.setLayoutManager(gridLayoutManager);
        rvGridView.setAdapter(adapter);

        populateTimeline();

        btnSignout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Signout clicked");
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                goLoginActivity();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditProfileActivity();
            }
        });

    }

    private void goEditProfileActivity() {
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        startActivity(i);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
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