package com.yardspoon.sendaway;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static MockLocationProvider mockLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mockLocationProvider = new MockLocationProvider(getApplicationContext());

        new AsyncTask<Void, Void, Void>() {
            @Override protected Void doInBackground(Void... voids) {
                Log.i("SendAway", "Starting mock location provider");
                mockLocationProvider.startup();
                Log.i("SendAway", "Started mock location provider");
                return null;
            }
        }.execute();

        findViewById(R.id.go_to_bismark).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                changeLocation(46.819931, -100.7985263);
            }
        });


        findViewById(R.id.go_to_sandiego).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                changeLocation(32.841244, -117.281568);
            }
        });


        findViewById(R.id.go_to_dayton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                changeLocation(39.740528, -84.1818745);
            }
        });
    }

    @Override protected void onDestroy() {
        Log.i("SendAway", "Shutting Down mock location provider");
        mockLocationProvider.shutdown();
        Log.i("SendAway", "Shut Down mock location provider");
        super.onDestroy();
    }

    private void changeLocation(double aLat, double aLong) {
        Intent intent = new Intent("com.yardspoon.sendaway.changelocation");
        intent.putExtra("lat", aLat);
        intent.putExtra("long", aLong);
        sendBroadcast(intent);
    }
}
