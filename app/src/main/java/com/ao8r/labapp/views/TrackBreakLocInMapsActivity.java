package com.ao8r.labapp.views;

import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.databinding.ActivityTrackBreakLocInMapsBinding;
import com.ao8r.labapp.models.BreakPointsModel;
import com.ao8r.labapp.models.MapParamsModel;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetAllBreakPointTrackList;
import com.ao8r.labapp.repository.GetAllPathLocationsPoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TrackBreakLocInMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackBreakLocInMapsBinding binding;
    private ArrayList<BreakPointsModel> breakPointsModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackBreakLocInMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //         in below line we are initializing our array list.
        breakPointsModelArrayList = new ArrayList<>();
        try {
            breakPointsModelArrayList =
                    GetAllBreakPointTrackList.getAllBreakPointTrackList(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                breakPointsModelArrayList
                        .forEach(System.out::println);
            }
            //first step of debug in breakPoint screen

            System.out.println("1-first step of debug in breakPoint screen -- onCreate on breakPoint");
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
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // Add a marker in Sydney and move the camera
            LatLng trackStartPoint = new LatLng(Double.parseDouble(ReferenceData.sampleBrokenX),
                    Double.parseDouble(ReferenceData.sampleBrokenY));
            mMap.addMarker(
                    new MarkerOptions()
                            .position(trackStartPoint)
                            .title(ReferenceData.sampleBrokenX + "," + ReferenceData.sampleBrokenY)
                            .snippet("نقطة بداية ال Track")
                            .icon(BitmapDescriptorFactory.defaultMarker(35f)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackStartPoint, 15.0f));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));

            for(BreakPointsModel breakPointsModel: breakPointsModelArrayList){
                if(breakPointsModel.getMapBreakLocLat() == Double.parseDouble(ReferenceData.sampleBrokenX)
                        && breakPointsModel.getMapBreakLocLong() == Double.parseDouble(ReferenceData.sampleBrokenY)){
                    continue;
                }

                LatLng markers = new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong());
                mMap.addMarker(new MarkerOptions()
                        .position(markers)
                        .title(breakPointsModel.getMapBreakLocLat()+","+breakPointsModel.getMapBreakLocLong())
                        .snippet(breakPointsModel.getMapBreakTime())
                        .icon(BitmapDescriptorFactory.defaultMarker(135f)));


                // below line is use to move our camera to the specific location.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 15.0f));
                // below line is use to zoom our camera on map.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0f));
            }
        } catch (Exception exception) {
            CustomToast.customToast(getApplicationContext(), "حدث خطا أثناء تجميل المسار");
        }
    }


}