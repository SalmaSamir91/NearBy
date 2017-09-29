package com.example.cognitev.nearby;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;


/**
 * Created by Salma on 9/25/2017.
 */
@Module
public class ApplicationModule {
    private Application app;
    private String PREF_NAME = "prefs";

    ApplicationModule(Application app) {
        this.app = app;    }


    @Provides
    SharedPreferences provideSharedPreference() {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
