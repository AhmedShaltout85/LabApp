package com.ao8r.labapp.views;

import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.databinding.ActivityMaps2Binding;
import com.ao8r.labapp.models.MapParamsModel;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetAllPathLocationsPoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.logging.Logger;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    private ActivityMaps2Binding binding;
    double dailyXLocation, dailyYLocation;
    double refXLocation, refYLocation;
    String tempSampleName, tempBatchName;
    private ArrayList<MapParamsModel> locationsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tempSampleName = ReferenceData.originSampleName;
        tempBatchName = ReferenceData.originBatchName;
        dailyXLocation = Double.parseDouble(ReferenceData.xLatitude);
        dailyYLocation = Double.parseDouble(ReferenceData.yLongitude);
        refXLocation = Double.parseDouble(ReferenceData.originX);
        refYLocation = Double.parseDouble(ReferenceData.originY);
        System.out.println("mapActivity-onCreate --" + tempSampleName);
        System.out.println("mapActivity-onCreate --" + tempBatchName);
        System.out.println("current -x - mapsActivity " + dailyXLocation);
        System.out.println("current -y - mapsActivity " + dailyYLocation);
        System.out.println(" ref x - mapsActivity " + refXLocation);
        System.out.println(" ref y - mapsActivity " + refYLocation);

        //         in below line we are initializing our array list.
        locationsArrayList = new ArrayList<>();
        try {
            locationsArrayList =
                    GetAllPathLocationsPoints.
                            getAllPathLocationsPoints(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locationsArrayList
                        .forEach(System.out::println);
            }
            //first step of debug in mapMark screen

            System.out.println("1-first step of debug in mapMark screen -- onCreate on mapMarks");

        } catch (Exception e) {
            e.getStackTrace();
            CustomToast.customToast(getApplicationContext(), "عفو هذا المسار لم يتم ادراجه");
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {

            mMap = googleMap;

//         Add a marker in Sydney and move the camera
//        LatLng dailyLocPoint = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(dailyLocPoint).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(dailyLocPoint));

            LatLng dailyLocPoint = new LatLng(dailyXLocation, dailyYLocation);
            mMap.addMarker(new MarkerOptions()
                    .position(dailyLocPoint)
                    .title(tempSampleName)
                    .snippet(tempBatchName));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(dailyLocPoint));




            for (MapParamsModel mapParamsModel : locationsArrayList) {

                //check if a point not have a location axis
                //TODO: // check if ONE OR MORE point have NO ref. location values TO BY PASS IT//
                if (mapParamsModel.getMapLocationLat() == 1.1111111 ||
                        mapParamsModel.getMapLocationLong() == 1.1111111 ||
                        mapParamsModel.getMapLocationLat() == 1.111111044883728 ||
                        mapParamsModel.getMapLocationLong() == 1.111111044883728) {
                    continue;
                }

                LatLng markers =
                        new LatLng(mapParamsModel.getMapLocationLat(),
                                mapParamsModel.getMapLocationLong());

                mMap.addMarker(new MarkerOptions()
                        .position(markers)
                        .title(mapParamsModel.getMapSampleName())
                        .snippet(ReferenceData.originBatchName)
                        .icon(BitmapDescriptorFactory.defaultMarker(135f)));


                // below line is use to move our camera to the specific location.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 11.0f));
                // below line is use to zoom our camera on map.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));

            }
        }catch (Exception exception){
            CustomToast.customToast(getApplicationContext(),"حدث خطا أثناء تجميل المسار");
        }
    }
}