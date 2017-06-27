package com.agraeta.user.btl;

import android.app.Application;
import android.support.multidex.MultiDex;

/**
 * Created by Nivida new on 27-Jun-17.
 */

public class BTL extends Application {
    public static String salesPersonTourID = "";
    public static String salesPersonStateID = "";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
