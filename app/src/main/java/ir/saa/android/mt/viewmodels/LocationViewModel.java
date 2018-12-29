package ir.saa.android.mt.viewmodels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import android.content.IntentSender;
import android.location.Location;


import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;


import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.services.GPSTracker;
import ir.saa.android.mt.services.ILocationCallBack;

public class LocationViewModel extends AndroidViewModel  {

    public MutableLiveData<Location> locationMutableLiveData;
    MutableLiveData<Location> locationMutableLiveDataForRemark;
    GPSTracker gpsTracker;
    Context context;
    LocationManager manager = null;

    protected static final String TAG = "LocationOnOff";

    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;




    public LocationViewModel(@NonNull Application application) {
        super(application);

        if (locationMutableLiveData == null)
            locationMutableLiveData = new MutableLiveData<>();
        if (gpsTracker == null) {
            gpsTracker = GPSTracker.getInstance(application);
        }
        if(locationMutableLiveDataForRemark==null){
            locationMutableLiveDataForRemark=new MutableLiveData<>();
        }
        this.context = application;
        if(manager==null)
            manager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsTracker.setiLocationCallBack(new ILocationCallBack() {
            @Override
            public void HasLocation(Location location) {
                locationMutableLiveData.setValue(location);
            }

        });
    }


    private FusedLocationProviderClient client;

    public Location getLocation(Context contextF) {
        Location location = null;
        if (G.checkPermissions()) {
            location = gpsTracker.getLocation();
            locationMutableLiveData.postValue(location);
        }

        return location;

    }


    public boolean isGpsEnable() {

            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public boolean isNetworkEnable() {
        gpsTracker.getLocation();
        return gpsTracker.isNetworkEnabled;
    }


    public void trunOnGps(Context contextFragment) {


        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //enableLoc(contextFragment);
            gpsTracker.showSettingsAlert(contextFragment);

        }


    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist*1000);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



    private void buildAlertMessageNoGps(Context contextFragment) {
        LayoutInflater factory = LayoutInflater.from(contextFragment);
        final View deleteDialogView = factory.inflate(R.layout.custom_dialog_mt, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(contextFragment);
        builder.setCancelable(false);


        final AlertDialog alert = builder.create();
        alert.setView(deleteDialogView);
        //builder.setMessage("لطفا موقعیت مکانی را روشن کنید.")
        deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enableLoc(contextFragment);
                alert.dismiss();
            }
        });
        deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });


        alert.show();
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc(Context context) {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {


                        }
                    }).build();

            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult((Activity) context, REQUEST_LOCATION);

                                //((Activity) context).finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }



}
