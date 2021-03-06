package com.yardspoon.sendaway;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_GRAB = 1234;
    public static MockLocationProvider mockLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mockLocationProvider = new MockLocationProvider(getApplicationContext());

        new AsyncTask<Void, Void, Boolean>() {
            @Override protected Boolean doInBackground(Void... voids) {
                Log.i("SendAway", "Starting mock location provider");
                return mockLocationProvider.startup();
            }

            @Override protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if(Boolean.FALSE.equals(result)) {
                    Toast.makeText(MainActivity.this, "Unable to start mock location service. Check permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();

        setupClickListener(R.id.go_to_louisville, 38.199135, -85.5736522);
        setupClickListener(R.id.go_to_stl, 38.633387, -90.2470689);
        setupClickListener(R.id.go_to_peterborough, 44.3151891, -78.360679);
        setupClickListener(R.id.go_to_salluit, 62.2047572, -75.6512441);
        setupClickListener(R.id.go_to_honolulu, 21.3543173, -157.9438843);
        setupClickListener(R.id.go_to_bismark, 46.819931, -100.7985263);
        setupClickListener(R.id.go_to_sandiego, 32.841244, -117.281568);
        setupClickListener(R.id.go_to_dayton, 39.740528, -84.1818745);
    }

    private void setupClickListener(int id, final double aLat, final double aLong) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                changeLocation(aLat,  aLong); }
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
