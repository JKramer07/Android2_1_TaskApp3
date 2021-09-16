package com.geek.android2_1_taskapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    private SharedPreferences prefs;

    public Prefs(Context context) {
        prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    //onBoarding
    public void saveBoardState(){
        prefs.edit().putBoolean("boardIsShown", true).apply();
    }

    public boolean isBoardShown() {
        return prefs.getBoolean("boardIsShown", false);
    }

    //username
    public void saveName(String saveText){
        prefs.edit().putString("text", saveText).apply();
    }

    public String getUsername(){
        return prefs.getString("text", null);
    }

    //Avatar
    public void setProfileImage(Uri uri){
        prefs.edit().putString("avatar", uri.toString()).apply();
    }
    public Uri getProfileImage(){
        return Uri.parse(prefs.getString("avatar",""));
    }

    //clearHistory
    public void putClear(){
        prefs.edit().clear().apply();
    }
    public void putClearText(){
        prefs.edit().remove("saveText");
    }
}
