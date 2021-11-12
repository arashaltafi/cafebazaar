package com.arash.altafi.caffebazar.utility;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class UserInfoManager {

    SharedPreferences preferences;

    public UserInfoManager(Context context)
    {
        preferences =  context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }

    public void saveInfo(String token)
    {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.apply();
    }

    @Nullable
    public String getToken()
    {
        return preferences.getString("token",null);
    }

}
