package com.example.android.movies;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by ROHAN on 12-08-2017.
 */

public class SampleApplication extends Application{
    public void onCreate() {
        super.onCreate();
        final Context context=this;
        Stetho.initialize(
        Stetho.newInitializerBuilder(context)
        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                .build());

    }
}
