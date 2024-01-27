package com.ao8r.labapp.customiz;



import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.repository.InsertLocationsToTrackBreakTB;
import com.ao8r.labapp.views.MenuScreen;

public class ScheduleRepeatTaskTimer {
    public static void repeatTask(String locX, String locY, Context tContext){
        MenuScreen menuScreen = new MenuScreen();
        Handler handler = new Handler();
        // Define the code block to be executed

        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                menuScreen.getLocation();
                ReferenceData.sampleBrokenX = locX;
                ReferenceData.sampleBrokenY = locY;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(tContext);
                }
                handler.postDelayed(this, 60000);
                // execute after 1 minute
            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
    }
}

//loop for track location
//                    Timer timer = new Timer();
//
//                    timer.schedule(new TimerTask() {
//                        public void run() {
//                            //Called in a secondary thread.
//                            //GUI update not allowed.
//                            getLocation();
//                            ReferenceData.sampleBrokenX = locationLat;
//                            ReferenceData.sampleBrokenY = locationLong;
//                            InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(getApplicationContext());
//                        }
//                    }, 0,60000);

//                    Handler handler = new Handler();
//                    // Define the code block to be executed
//
//                    Runnable runnableCode = new Runnable() {
//                        @Override
//                        public void run() {
//                            // Do something here on the main thread
//                            Log.d("Handlers", "Called on main thread");
//                            // Repeat this the same runnable code block again another 2 seconds
//                            // 'this' is referencing the Runnable object
//                            getLocation();
//                            ReferenceData.sampleBrokenX = locationLat;
//                            ReferenceData.sampleBrokenY = locationLong;
//                            InsertLocationsToTrackBreakTB.insertLocationsToTrackBreakTB(getApplicationContext());
//                            handler.postDelayed(this, 60000);
//                        }
//                    };
//                    // Start the initial runnable task by posting through the handler
//                    handler.post(runnableCode);
