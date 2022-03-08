package com.example.instagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PROFILE_PIC = "profilePicture";
    public static final String KEY_PROFILE_DESCRIPTION = "profileDescription";

    public String getUsername() {
        return getString(KEY_USERNAME);
    }

    public void setUsername(String username) {
        put(KEY_USERNAME, username);
    }

    public String getPassword() {
        return getString(KEY_PASSWORD);
    }

    public void setPassword(String password) {
        put(KEY_PASSWORD, password);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_PROFILE_PIC);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_PROFILE_PIC, parseFile);
    }

    public String getProfileDescription() {
        return getString(KEY_PROFILE_DESCRIPTION);
    }

    public void setProfileDescription(String password) {
        put(KEY_PROFILE_DESCRIPTION, password);
    }


}
