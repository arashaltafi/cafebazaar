package com.arash.altafi.caffebazar.utility;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TokenContainer.updateToken(new UserInfoManager(this).getToken());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
