package com.cellpointmobile.repoviewer;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MvpApplication extends Application {

    private static MvpApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("repos.realm").build();
        Realm.setDefaultConfiguration(config);
    }

    public static Context getAppContext() {
        return application;
    }

}
