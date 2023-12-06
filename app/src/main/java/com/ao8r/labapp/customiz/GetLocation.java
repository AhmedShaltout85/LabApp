package com.ao8r.labapp.customiz;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GetLocation {
    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private OnLocationChangeListener mListener;

    public interface OnLocationChangeListener {
        void onLocationChange(Location location);
    }

    public GetLocation(Context context) {
        this.context = context;
    }

    public void setLocationChangeListener(OnLocationChangeListener listener) {
        this.mListener = listener;
    }

    public void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(5000); // Update interval in milliseconds

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null) {
                        Location lastLocation = locationResult.getLastLocation();
                        if (mListener != null) {
                            mListener.onLocationChange(lastLocation);
                        }
                    }
                }
            };

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            Log.e("GetLocation", "Location permission not granted");
        }
    }

    public void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}

//    To use this class in your Android project, follow these steps:
//
//        1. Create an instance of the `GetLocation` class in your activity or fragment.
//
//
//        GetLocation getLocation = new GetLocation(this);
//
//        2. Implement the `OnLocationChangeListener` interface to receive location updates.
//
//        getLocation.setLocationChangeListener(new GetLocation.OnLocationChangeListener() {
//@Override
//public void onLocationChange(Location location) {
//        // Handle the updated location
//        // You can access the latitude and longitude using location.getLatitude() and location.getLongitude()
//        }
//        });
//
//        3. Start location updates using the `startLocationUpdates()` method.
//
//
//        getLocation.startLocationUpdates();
//
//        4. Stop location updates when you no longer need them, typically in the `onPause()` or `onDestroy()` method.
//
//
//@Override
//protected void onPause() {
//        super.onPause();
//        getLocation.stopLocationUpdates();
//        }
//
//        Please note that you need to include the necessary permissions in your manifest file and handle the permission request callbacks properly. The example assumes that you have already granted the required location permission.


// CHATGPT CODE

//
//import android.Manifest;
//        import android.content.Context;
//        import android.content.pm.PackageManager;
//        import android.location.Location;
//        import android.location.LocationListener;
//        import android.location.LocationManager;
//        import android.os.Bundle;
//        import android.support.v4.app.ActivityCompat;
//
//public class GetLocation {
//    private Context context;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
//
//    public GetLocation(Context context) {
//        this.context = context;
//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//    }
//
//    public void getLocation() {
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                // Handle location updates here
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                // Do something with the latitude and longitude
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            // Request location updates
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//        }
//    }
//
//    public void stopGettingLocationUpdates() {
//        if (locationManager != null && locationListener != null) {
//            locationManager.removeUpdates(locationListener);
//        }
//    }
//}
