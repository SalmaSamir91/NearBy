package com.example.cognitev.nearby;


import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Salma on 9/25/2017.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(NearbyApplication app);

    SharedPreferences getSharedPreferences();

}
