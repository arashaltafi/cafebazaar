package com.arash.altafi.caffebazar.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferences_Download {

    private SharedPreferences preferences;

    public SharedPreferences_Download(Context context) {
            preferences = context.getSharedPreferences("download",Context.MODE_PRIVATE);
    }

    public void saveDownloadInfo (boolean first)
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
