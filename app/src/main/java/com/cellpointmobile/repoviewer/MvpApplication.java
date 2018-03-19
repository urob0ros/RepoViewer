package com.cellpointmobile.repoviewer;

import android.app.Application;
import android.content.Context;

public class MvpApplication extends Application {

    private static MvpApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getAppContext() {
        return application;
    }

}
