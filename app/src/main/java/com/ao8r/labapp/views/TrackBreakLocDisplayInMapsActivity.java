package com.ao8r.labapp.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;


import androidx.fragment.app.FragmentActivity;

import com.ao8r.labapp.R;

import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.databinding.ActivityTrackBreakLocDisplayInMapsBinding;
import com.ao8r.labapp.models.BreakPointsModel;


import com.ao8r.labapp.models.ReferenceData;

import com.ao8r.labapp.repository.GetAllBreakPointTrackListToDisplay;
import com.ao8r.labapp.repository.GetAllLabCodeListForBreakPointsDropdown;
import com.ao8r.labapp.repository.InsertLocationsToTrackBreakTB;
import com.ao8r.labapp.services.InternetConnection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class TrackBreakLocDisplayInMapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;
    private ActivityTrackBreakLocDisplayInMapsBinding binding;
    private ArrayList<BreakPointsModel> breakPointsModelArrayList;
    private ArrayList<LatLng> polylinePoints = new ArrayList<>();
    Button refreshTrackBtn, breakDateBtn;
    ArrayList<BreakPointsModel> breakPointsModelLabCodeArrayList;
    EditText enterBreakId;
    //    ArrayList<GetBreakIdTrackLocationsModel> getBreakIdTrackLocationsModelArrayList;
    Spinner mapBreakPointLabCodeSpinner, mapBreakPointBreakIdSpinner;
    //
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable updatedTrackList;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTrackBreakLocDisplayInMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.displayMap);
        mapFragment.getMapAsync(this);

        //declare vars
//        navToBrokenScreenBtn =findViewById(R.id.navToBrokenScreen);
        enterBreakId = findViewById(R.id.addBreakId);
//        mapBreakPointBreakIdSpinner = findViewById(R.id.addBreakId); // using reusable spinner
        refreshTrackBtn = findViewById(R.id.refreshTrackBtn);
        breakDateBtn = findViewById(R.id.selectBreakDate);
        mapBreakPointLabCodeSpinner = findViewById(R.id.addLabCode);

        if (InternetConnection.checkConnection(getApplicationContext())) {
            // Its Available...
            CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
        } else {
            // Not Available...
            CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

        }


        // get mapBreakPointLABCODES
        try {
            breakPointsModelLabCodeArrayList =
                    GetAllLabCodeListForBreakPointsDropdown.getAllLabCodeListForBreakPointsDropdown(getApplicationContext());
        } catch (Exception e) {
            e.getStackTrace();
            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
        }
        //labCode spinner
//        CustomSpinner.setupSpinner(this, mapBreakPointLabCodeSpinner, breakPointsModelLabCodeArrayList);
//        ReferenceData.TRACK_LAB_CODE = ReferenceData.SELECTED_ITEM_SPINNER;

        ArrayAdapter<BreakPointsModel> spinnerBreakLabCodeType = new
                ArrayAdapter<BreakPointsModel>(TrackBreakLocDisplayInMapsActivity.this,
                android.R.layout.simple_spinner_item,
                breakPointsModelLabCodeArrayList
        );

        spinnerBreakLabCodeType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapBreakPointLabCodeSpinner.setAdapter(spinnerBreakLabCodeType);
        mapBreakPointLabCodeSpinner.setOnItemSelectedListener(this);
        // get mapBreakPointBreakID
//        try {
//
//            getBreakIdTrackLocationsModelArrayList =
//                    GetAllBreakIdListByLabCode.getAllBreakIdListByLabCodeBreakPointsDropdown(getApplicationContext());
//        } catch (Exception e) {
//            e.getStackTrace();
//            CustomToast.customToast(getApplicationContext(), "الانترنت غير مستقر, حاول مره أخرى");
//        }
//
//
//        //Break Id spinner
//        CustomSpinner.setupSpinner(this, mapBreakPointBreakIdSpinner, getBreakIdTrackLocationsModelArrayList);
//        ReferenceData.TRACK_BREAK_ID = ReferenceData.SELECTED_ITEM_SPINNER;


        // active action in button
        refreshTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    if (InternetConnection.checkConnection(getApplicationContext())) {
                        // Its Available...
                        CustomToast.customToast(getApplicationContext(), "متصل بالانترنت");
                    } else {
                        // Not Available...
                        CustomToast.customToast(getApplicationContext(), "فضلا تحقق من الاتصال بالانترنت");

                    }
//                                        breakPointsModelArrayList.clear();

                    //// hide keyboard after type
//                    InputMethodManager imm = null;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    }
//                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

//                    ReferenceData.TRACK_LAB_CODE = ReferenceData.SELECTED_ITEM_SPINNER;
                    ReferenceData.TRACK_BREAK_ID = enterBreakId.getText().toString();

                    if (ReferenceData.TRACK_LAB_CODE.isEmpty()
                            || ReferenceData.TRACK_BREAK_ID.isEmpty()
                            || ReferenceData.TRACK_BREAK_DATE.isEmpty()
                    ) {
                        CustomToast.customToast(getApplicationContext(), "فضلا أختار بيانات الحقول");
                    }
                    //initiate arraylists
                    breakPointsModelArrayList = new ArrayList<>();


                    breakPointsModelArrayList =
                            GetAllBreakPointTrackListToDisplay.getAllBreakPointTrackListToDisplay(getApplicationContext());


                    if (breakPointsModelArrayList.isEmpty()) {
                        CustomToast.customToast(getApplicationContext(), "عفو ليس هناك مسار لهذه البيانات");
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        breakPointsModelArrayList
                                .forEach(System.out::println);
                    }
                    //first step of debug in breakPoint screen
                    System.out.println("debug in breakPoint screen -- onCreate on Display Track breakPoint");
                    onMapReady(mMap);

//                    Handler handler = new Handler();
//                    handler = new Handler();
                    // Define the code block to be executed

//                    updatedTrackList = new Runnable() {
//                        @Override
//                        public void run() {
//                            // Do something here on the main thread
//                            Log.d("Handlers", "Called on main thread");
                            // Repeat this the same runnable code block again another 2 seconds
                            // 'this' is referencing the Runnable object
                            //initiate arraylists
//
//                            breakPointsModelArrayList = new ArrayList<>();
//                            breakPointsModelArrayList =
//                                    GetAllBreakPointTrackListToDisplay.getAllBreakPointTrackListToDisplay(getApplicationContext());
//
//
////                            if (breakPointsModelArrayList.isEmpty()) {
////                                CustomToast.customToast(getApplicationContext(), "عفو ليس هناك مسار لهذه البيانات");
////                            }
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                breakPointsModelArrayList
//                                        .forEach(System.out::println);
//                            }
//                            //first step of debug in breakPoint screen
//                            System.out.println("debug in breakPoint screen -- onCreate on Display Track breakPoint");
//
//                            onMapReady(mMap);
////
//
//                            handler.postDelayed(updatedTrackList, 60000);
//
//                            //
//                        }
//                    };
//
//
////                 update track
//
//                    // Start the initial runnable task by posting through the handler
//                    handler.post(updatedTrackList);


                } catch (Exception e) {
                    e.getStackTrace();
                    CustomToast.customToast(getApplicationContext(), "عفو هذا المسار غير موجود");
                }
            }
        });

        breakDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacksAndMessages(null);
                CustomToast.customToast(getApplicationContext(), "الضغط المطول لاختيار التاريخ");
                ReferenceData.TRACK_BREAK_DATE = String.valueOf(CustomTimeAndDate.getCurrentDate());
                System.out.println(String.valueOf(CustomTimeAndDate.getCurrentDate()));
                CustomToast.customToast(getApplicationContext(), String.valueOf(CustomTimeAndDate.getCurrentDate()));
            }
        });

        // to choose certain date for break
        breakDateBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handler.removeCallbacksAndMessages(null);


                //                 TODO Auto-generated method stub
                MaterialDatePicker materialDatePickerTo = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("تاريخ الكسر")
                        .build();
                materialDatePickerTo.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                        calendar.setTimeInMillis((Long) selection);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        ReferenceData.TRACK_BREAK_DATE = format.format(calendar.getTime());
//                       toDate.setText(StoresConstants.END_DATE);
                        CustomToast.customToast(getApplicationContext(), ReferenceData.TRACK_BREAK_DATE);
                        System.out.println(ReferenceData.TRACK_BREAK_DATE);

                    }
                });
                materialDatePickerTo.show(getSupportFragmentManager(), "ShowDATETO");
                return true;
            }
        });
        //         in below line we are initializing our array list.
//        breakPointsModelArrayList = new ArrayList<>();
//        try {
//            breakPointsModelArrayList =
//                    GetAllBreakPointTrackListToDisplay.getAllBreakPointTrackListToDisplay(getApplicationContext());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                breakPointsModelArrayList
//                        .forEach(System.out::println);
//            }
//            //first step of debug in breakPoint screen
//
//            System.out.println("first step of debug in breakPoint screen -- onCreate on breakPoint");
//        } catch (Exception e) {
//            e.getStackTrace();
//            CustomToast.customToast(getApplicationContext(), "عفو هذا المسار لم يتم ادراجه");
//        }


    }

    //    Go Back to MenuScreen
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(TrackBreakLocDisplayInMapsActivity.this, MenuScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mMap = googleMap;


            for (BreakPointsModel breakPointsModel : breakPointsModelArrayList) {

                LatLng trackStartPoint = new LatLng(breakPointsModelArrayList.get(0).getMapBreakLocLat(),
                        breakPointsModelArrayList.get(0).getMapBreakLocLong());

                mMap.addMarker(
                        new MarkerOptions()
                                .position(trackStartPoint)
                                .title(breakPointsModelArrayList.get(0).getMapBreakLocLat() + "," + breakPointsModelArrayList.get(0).getMapBreakLocLong())
                                .snippet("نقطة بداية ال Track")
//                                .icon(BitmapDescriptorFactory.defaultMarker(135f))
                );
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trackStartPoint, 12.0f));
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
//
                LatLng markers = new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong());
                mMap.addMarker(new MarkerOptions()
                        .position(markers)
                        .title(breakPointsModel.getMapBreakLocLat() + "," + breakPointsModel.getMapBreakLocLong())
                        .snippet(breakPointsModel.getMapBreakTime())
                        .icon(BitmapDescriptorFactory.defaultMarker(35f)));


                BreakPointsModel lastRecord = breakPointsModelArrayList.get(breakPointsModelArrayList.size() - 1);
                System.out.println(lastRecord);
                System.out.println("lastRecord ---" + lastRecord.getMapBreakLocLat() + "--" + lastRecord.getMapBreakLocLong());

                LatLng lastMarker = new LatLng(lastRecord.getMapBreakLocLat(), lastRecord.getMapBreakLocLong());

                mMap.addMarker(
                        new MarkerOptions()
                                .position(lastMarker)
                                .title(lastRecord.getMapBreakLocLat() + "," + lastRecord.getMapBreakLocLong())
                                .snippet("النقطة الحاليه على ال Track")
                                .icon(BitmapDescriptorFactory.defaultMarker(135f))
                );


                // below line is use to move our camera to the specific location.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastMarker, 12.0f));
                // below line is use to zoom our camera on map.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));


                polylinePoints.add(new LatLng(breakPointsModel.getMapBreakLocLat(), breakPointsModel.getMapBreakLocLong()));
                drawPolyline(polylinePoints);

            }
        } catch (Exception exception) {
            CustomToast.customToast(getApplicationContext(), "فضلا أكمل بيانات الحقول لتحميل مسار الكسر");
        }
    }

    private void drawPolyline(ArrayList<LatLng> points) {
        if (mMap != null && points.size() > 1) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(points)
                    .color(getResources().getColor(R.color.colorPrimary)) // Change the color as needed
                    .width(13); // Change the width as needed

            Polyline polyline = mMap.addPolyline(polylineOptions);

            // Optionally, zoom the camera to fit the polyline
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ReferenceData.TRACK_LAB_CODE = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}