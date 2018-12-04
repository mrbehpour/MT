package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import android.location.Location;
import android.support.annotation.NonNull;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.services.GPSTracker;

public class LocationViewModel extends AndroidViewModel {

    public GPSTracker gpsTracker;

    public LocationViewModel(@NonNull Application application) {
        super(application);

        if(gpsTracker!=null){
            gpsTracker=new GPSTracker(application);
        }
    }

    public Location getLocation(){
        if(G.checkPermissions()) {
            return gpsTracker.getLocation();
        }
        return null;
    }


}
