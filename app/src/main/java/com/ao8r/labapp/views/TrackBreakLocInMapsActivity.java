package com.ao8r.labapp.views;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.GetLocation;
import com.ao8r.labapp.customiz.ScheduleRepeatTaskTimer;
import com.ao8r.labapp.databinding.ActivityTrackBreakLocInMapsBinding;
import com.ao8r.labapp.models.BreakPointsModel;
import com.ao8r.labapp.models.MapParamsModel;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetAllBreakPointTrackList;
import com.ao8r.labapp.repository.GetAllPathLocationsPoints;
import com.ao8r.labapp.repository.InsertLocationsToTrackBreakTB;
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
    Button navToBrokenScreenBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackBreakLocInMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        navToBrokenScreenBtn =findViewById(R.id.navToBrokenScreen);

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

            System.out.println("first step of debug in breakPoint screen -- onCreate on breakPoint");
        } catch (Exception e) {
            e.getStackTrace();
            CustomToast.customToast(getApplicationContext(), "عفو هذا المسار لم يتم ادراجه");
        }

        navToBrokenScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ReferenceData.sampleBrokenX.isEmpty() && !ReferenceData.sampleBrokenY.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(), AddBrokenPointScreen.class);
                    startActivity(intent);
                }else{
                    CustomToast.customToast(getApplicationContext(),"تحقق من الانترنت");
                }
            }
        });

    }

    //    Go Back to MenuScreen
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(TrackBreakLocInMapsActivity.this, MenuScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

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

            for (BreakPointsModel breakPointsModel : breakPointsModelArrayList) {
                if (breakPointsModel.getMapBreakLocLat() == Double.parseDouble(ReferenceData.sampleBrokenX)
                        && breakPointsModel.getMapBreakLocLong() == Double.parseDouble(ReferenceData.sampleBrokenY)) {
                    continue;
                }

                LatLng markers = new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong());
                mMap.addMarker(new MarkerOptions()
                        .position(markers)
                        .title(breakPointsModel.getMapBreakLocLat() + "," + breakPointsModel.getMapBreakLocLong())
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