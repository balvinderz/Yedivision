package com.stuffbox.yedivision;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jasbe on 26-03-2018.
 */

public class MyFirebaseApp extends  android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
