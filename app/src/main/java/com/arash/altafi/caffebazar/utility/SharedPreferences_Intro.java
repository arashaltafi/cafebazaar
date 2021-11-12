package com.arash.altafi.caffebazar.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferences_Intro {

    private SharedPreferences preferences;

    public SharedPreferences_Intro(Context context) {
            preferences = context.getSharedPreferences("intro",Context.MODE_PRIVATE);
    }

    public void saveIntroInfo (boolean first)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("first",first);
        editor.apply();
    }

    public boolean isFirst()
    {
        return preferences.getBoolean("first",true);
    }

}
