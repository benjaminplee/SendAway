package com.yardspoon.sendaway;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MockLocationProvider {
    private final Context context;
    private GoogleApiClient apiClient;
    private boolean started = false;

    public MockLocationProvider(Context context) {
        this.context = context;
    }

    public boolean startup() {
        if (!started) {
            apiClient = buildAPIClientAndConnectToGoogleLocationServices(context);
            LocationServices.FusedLocationApi.setMockMode(apiClient, true);
            started = true;
        }
        return true;
    }

    public boolean pushLocation(double lat, double lon) {
        Location mockLocation = new Location("Mock Location Provider For Tests");
        mockLocation.setLatitude(lat);
        mockLocation.setLongitude(lon);
        mockLocation.setAltitude(0);
        mockLocation.setAccuracy(5);
        mockLocation.setTime(System.currentTimeMillis());
        mockLocation.setElapsedRealtimeNanos(42);

        LocationServices.FusedLocationApi.setMockLocation(apiClient, mockLocation);

        return true;
    }

    public boolean shutdown() {
        if (started) {
            LocationServices.FusedLocationApi.setMockMode(apiClient, false);
            apiClient.disconnect();
        }
        return true;
    }

    private GoogleApiClient buildAPIClientAndConnectToGoogleLocationServices(Context ctx) {
        GoogleApiClient apiClient = new GoogleApiClient.Builder(ctx).addApi(LocationServices.API).build();
        ConnectionResult connectionResult = apiClient.blockingConnect();
        if (!connectionResult.isSuccess()) {
            throw new RuntimeException("Unable to connect to google api");
        }
        return apiClient;
    }
}