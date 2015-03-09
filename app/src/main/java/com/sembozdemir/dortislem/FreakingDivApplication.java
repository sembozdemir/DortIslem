package com.sembozdemir.dortislem;

import android.app.Application;

import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Semih Bozdemir on 9.3.2015.
 */
public class FreakingDivApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Prefs class
        Prefs.initPrefs(getApplicationContext());
    }
}
