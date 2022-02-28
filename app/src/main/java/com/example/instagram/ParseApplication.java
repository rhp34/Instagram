package com.example.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("V81V2cVS6iagjjNbu2RyuXdIatGREo4YBZxGCxKH")
                .clientKey("VZntlB2237FqGFRAqYjEP2fnNRS9b49tXZgPu1Hs")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
