package com.yardspoon.sendaway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ChangeLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        double aLat = intent.getDoubleExtra("lat", 38.0);
        double aLong = intent.getDoubleExtra("long", -90);

        Log.i("SendAway", "Changing location to " + aLat + ", " + aLong);
        MainActivity.mockLocationProvider.pushLocation(aLat, aLong);
        Log.i("SendAway", "Changed location");
    }
}