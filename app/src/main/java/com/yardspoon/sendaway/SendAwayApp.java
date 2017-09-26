package com.yardspoon.sendaway;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

import hu.supercluster.paperwork.Paperwork;
import io.fabric.sdk.android.Fabric;


public class SendAwayApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Paperwork paperwork = new Paperwork(this);

        Fabric.with(this, new Crashlytics());
        Crashlytics.setString("gitSHA", paperwork.get("gitSHA"));
        Crashlytics.setString("buildTime", paperwork.get("buildTime"));

        Answers.getInstance().logCustom(new CustomEvent("Application Create").putCustomAttribute("gitSHA", paperwork.get("gitSHA")));
    }
}
