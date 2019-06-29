package com.artexceptionals.youreuro;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {
    private static Context mContext = null;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor = null;
    public static final String Default = "N/A";
    /*
     * This private constructor is to prevent anyone else from instantiating
     * */
    private CustomSharedPreferences() {}
    private static CustomSharedPreferences ourInstance = null;

    public static CustomSharedPreferences getInstance(Context context) {
        mContext = context;
        if (ourInstance == null) {
            ourInstance = new CustomSharedPreferences();
        }
        sharedPreferences = context.getSharedPreferences("StaticData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return ourInstance;
    }

    public void genericSetString(String Key, String Value){
        editor.putString(Key,Value);
        editor.apply();
    }

    public void setBoolean(String Key, boolean Value){
        editor.putBoolean(Key,Value);
        editor.apply();
    }

    public boolean getBoolean(String key){
       return sharedPreferences.getBoolean(key, false);
    }

    public String genericGetString(String Key){
        return sharedPreferences.getString(Key,Default);
    }

    public String genericGetString(String key, String defaultString){
        return sharedPreferences.getString(key, defaultString);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void setInt(String Key, int Value){
        editor.putInt(Key,Value);
        editor.apply();
    }
}
