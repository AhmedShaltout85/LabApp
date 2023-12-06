package com.ao8r.labapp.customiz;

public class CustomTimeAndDate {

    //Get Current Date in format ("2022-1-11")
    public static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
    //Get Current Time in format ("12:00:00")
    public static java.sql.Time getCurrentTime() {
        java.util.Date tToday = new java.util.Date();
        return new java.sql.Time(tToday.getTime());
    }

    //Convert Current Time into String format ("12")
    public static int convertCurrentTimeToString() {
        java.util.Date tToday = new java.util.Date();
        java.util.Date currentTime =  new java.sql.Time(tToday.getTime());
        String sCurrentTime = currentTime.toString();
        String[] spiltCurrentTime = sCurrentTime.split(":");
//        return spiltCurrentTime[0];
        //        int intTime = Integer.parseInt(spiltCurrentTime[0]);
        return Integer.parseInt(spiltCurrentTime[0]);
    }




}
