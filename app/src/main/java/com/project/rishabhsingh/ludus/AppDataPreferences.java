package com.project.rishabhsingh.ludus;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Rishabh Singh on 22-03-2017.
 */

public class AppDataPreferences {

    private static final String INTRO_NEEDED = "introNeeded";

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
}
