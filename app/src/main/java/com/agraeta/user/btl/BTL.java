package com.agraeta.user.btl;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.agraeta.user.btl.model.RegisteredUserTourResponse;
import com.agraeta.user.btl.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 27-Jun-17.
 */

public class BTL extends Application {
    public static String salesPersonTourID = "";
    public static String salesPersonStateID = "";
    public static User user = new User();
    public static RegisteredUserTourResponse.Distributor distributor = new RegisteredUserTourResponse.Distributor();
    public static List<RegisteredUserTourResponse.Address> addressList = new ArrayList<>();

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
