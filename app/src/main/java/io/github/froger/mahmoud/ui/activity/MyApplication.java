package io.github.froger.mahmoud.ui.activity;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Moustafa on 12/14/2017.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
