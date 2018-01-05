package suryakancana.nongkinongki.views.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import pub.devrel.easypermissions.EasyPermissions;
import suryakancana.nongkinongki.NongkinongkiApp;

/**
 * Created by LIMS on 05/01/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    NongkinongkiApp app = NongkinongkiApp.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (app.mGoogleApiClient != null) {
            app.mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnected (@Nullable Bundle bundle) {
        //check for location permission
        if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            app.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(app.mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended (int i) {
        // TODO: add warning on connection suspended
    }

    @Override
    public void onConnectionFailed (@NonNull ConnectionResult result) {
        // TODO: add error message on connection failed
    }

    public void initLocationDetection () {
        if (app.mGoogleApiClient == null) {
            //check for location permission
            if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                app.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
        } else {
            app.mGoogleApiClient.connect();
        }
    }
}
