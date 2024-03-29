package com.ao8r.labapp.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.ao8r.labapp.R;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.databinding.ActivityTrackBreakLocInMapsBinding;
import com.ao8r.labapp.models.BreakPointsModel;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.GetAllBreakPointTrackList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TrackBreakLocInMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityTrackBreakLocInMapsBinding binding;
    private ArrayList<BreakPointsModel> breakPointsModelArrayList;
    Button navToBrokenScreenBtn;
    private ArrayList<LatLng> polylinePoints = new ArrayList<>();

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
                //stop timer
                MenuScreen menuScreen = new MenuScreen();
                menuScreen.handler.removeCallbacks(menuScreen.runnableCode);
                menuScreen.handler.removeCallbacksAndMessages(null);
                menuScreen.isActive = false;
                menuScreen.timer.cancel();
                menuScreen.timer.purge();
                CustomToast.customToast(v.getContext(), "تم إيقاف تتبع الكسر");

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
//            LatLng trackStartPoint = new LatLng(Double.parseDouble(ReferenceData.sampleBrokenX),
//                    Double.parseDouble(ReferenceData.sampleBrokenY));
//            mMap.addMarker(
//                    new MarkerOptions()
//                            .position(trackStartPoint)
//                            .title(ReferenceData.sampleBrokenX + "," + ReferenceData.sampleBrokenY)
//                            .snippet("نقطة بداية ال Track")
//                            .icon(BitmapDescriptorFactory.defaultMarker(35f)));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackStartPoint, 27.0f));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(27.0f));

            for (BreakPointsModel breakPointsModel : breakPointsModelArrayList) {
//                if (breakPointsModel.getMapBreakLocLat() == Double.parseDouble(ReferenceData.sampleBrokenX)
//                        && breakPointsModel.getMapBreakLocLong() == Double.parseDouble(ReferenceData.sampleBrokenY)) {
//                    continue;
//                }
//                LatLng trackStartPoint = new LatLng(Double.parseDouble(ReferenceData.sampleBrokenX),
//                        Double.parseDouble(ReferenceData.sampleBrokenY));
                LatLng trackStartPoint = new LatLng(breakPointsModelArrayList.get(0).getMapBreakLocLat(),
                        breakPointsModelArrayList.get(0).getMapBreakLocLong());

                mMap.addMarker(
                        new MarkerOptions()
                                .position(trackStartPoint)
                                .title(breakPointsModelArrayList.get(0).getMapBreakLocLat() + "," + breakPointsModelArrayList.get(0).getMapBreakLocLong())
                                .snippet("نقطة بداية ال Track")
//                                .icon(BitmapDescriptorFactory.defaultMarker(35f))
                );
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackStartPoint, 27.0f));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(27.0f));

                LatLng markers = new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong());
                mMap.addMarker(new MarkerOptions()
                        .position(markers)
                        .title(breakPointsModel.getMapBreakLocLat() + "," + breakPointsModel.getMapBreakLocLong())
                        .snippet(breakPointsModel.getMapBreakTime())
                        .icon(BitmapDescriptorFactory.defaultMarker(35f)));


                 BreakPointsModel lastRecord = breakPointsModelArrayList.get(breakPointsModelArrayList.size() - 1);
                System.out.println(lastRecord);
                System.out.println("lastRecord ---"+lastRecord.getMapBreakLocLat() + "--" + lastRecord.getMapBreakLocLong());

                LatLng lastMarker = new LatLng(lastRecord.getMapBreakLocLat(), lastRecord.getMapBreakLocLong());

                mMap.addMarker(
                        new MarkerOptions()
                                .position(lastMarker)
                                .title(lastRecord.getMapBreakLocLat() + "," + lastRecord.getMapBreakLocLong())
                                .snippet("النقطة الحاليه على ال Track")
                                .icon(BitmapDescriptorFactory.defaultMarker(135f))
                );


                // below line is use to move our camera to the specific location.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markers, 27.0f));
                // below line is use to zoom our camera on map.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(27.0f));

//                mMap.addPolyline((new PolylineOptions()).add(trackStartPoint, lastMarker).
//                        // below line is use to specify the width of poly line.
//                                width(7)
//                        // below line is use to add color to our poly line.
//                        .color(Color.RED)
//                        // below line is to make our poly line geodesic.
//                        );


//                polyline with every track point


//                for(int i=0;i<breakPointsModelArrayList.size();i++){
////                    new PolylineOptions().add(new LatLng(breakPointsModelArrayList.get(i).getMapBreakLocLat(), breakPointsModelArrayList.get(i).getMapBreakLocLong()));
//                    mMap.addPolyline((new PolylineOptions()
//                                    .add(new LatLng(breakPointsModelArrayList.get(i).getMapBreakLocLat(),
//                                            breakPointsModelArrayList.get(i).getMapBreakLocLong()))).
//                                    // below line is use to specify the width of poly line.
//                                            width(5)
//                                    // below line is use to add color to our poly line.
//                                    .color(Color.BLUE)
//                            // below line is to make our poly line geodesic.
//                    );
//                }
                polylinePoints.add(new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong()));
                drawPolyline(polylinePoints);

            }
        } catch (Exception exception) {
            CustomToast.customToast(getApplicationContext(), "حدث خطا أثناء تجميل المسار");
        }
    }
    private void drawPolyline(ArrayList<LatLng> points) {
        if (mMap != null && points.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(points)
                    .color(getResources().getColor(R.color.colorPrimary)) // Change the color as needed
                    .width(9); // Change the width as needed

            Polyline polyline = mMap.addPolyline(polylineOptions);

            // Optionally, zoom the camera to fit the polyline
        }
        }

}