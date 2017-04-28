package com.project.rishabhsingh.dWarden;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Rishabh Singh on 22-03-2017.
 */

public class AppDataPreferences {

    private static final String INTRO_NEEDED = "introNeeded";
    private static final String PREF_TOKEN_KEY= "token";
    private static final String PREF_EMAIL_KEY= "email";
    public static final String URL = "http://hmsonline.herokuapp.com/api/";
    public static final String studentRollNo = "1404310064";

    public static boolean isIntroNeeded(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(INTRO_NEEDED,true);
    }

    public static void setIntroNeed(Context context, boolean isIntroNeeded) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(INTRO_NEEDED,isIntroNeeded)
                .apply();
    }

    public static String getToken(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_TOKEN_KEY, null);
    }

    public static void setToken(Context context, String token){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_TOKEN_KEY,token)
                .apply();
    }

    public static String getEmail(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_EMAIL_KEY, null);
    }

    public static void setEmail(Context context, String email){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_EMAIL_KEY,email)
                .apply();
    }

    public static boolean isTokenSet(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_TOKEN_KEY,null)!=null;
    }
}
