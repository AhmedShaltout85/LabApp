package com.ao8r.labapp.models;

public class BreakPointsModel {
    private double mapBreakLocLat;
    private double mapBreakLocLong;
    private String mapBreakTime;
    private String mapBreakLabCode;

    public BreakPointsModel() {
    }

    public BreakPointsModel(String mapBreakLabCode) {
        this.mapBreakLabCode = mapBreakLabCode;
    }

    public BreakPointsModel(double mapBreakLocLat, double mapBreakLocLong, String mapBreakTime) {
        this.mapBreakLocLat = mapBreakLocLat;
        this.mapBreakLocLong = mapBreakLocLong;
        this.mapBreakTime = mapBreakTime;
    }

    public BreakPointsModel(double mapBreakLocLat, double mapBreakLocLong, String mapBreakTime, String mapBreakLabCode) {
        this.mapBreakLocLat = mapBreakLocLat;
        this.mapBreakLocLong = mapBreakLocLong;
        this.mapBreakTime = mapBreakTime;
        this.mapBreakLabCode = mapBreakLabCode;
    }

    public double getMapBreakLocLat() {
        return mapBreakLocLat;
    }

    public void setMapBreakLocLat(double mapBreakLocLat) {
        this.mapBreakLocLat = mapBreakLocLat;
    }

    public double getMapBreakLocLong() {
        return mapBreakLocLong;
    }

    public void setMapBreakLocLong(double mapBreakLocLong) {
        this.mapBreakLocLong = mapBreakLocLong;
    }

    public String getMapLabCode() {
        return mapBreakLabCode;
    }

    public void setMapLabCode(String mapBreakLabCode) {
        this.mapBreakLabCode = mapBreakLabCode;
    }

    public String getMapBreakTime() {
        return mapBreakTime;
    }

    public void setMapBreakTime(String mapBreakTime) {
        this.mapBreakTime = mapBreakTime;
    }

    @Override
    public String toString() {
        return  mapBreakLabCode;
//                "BreakPointsModel{" +
//                "mapBreakLocLat=" + mapBreakLocLat +
//                ", mapBreakLocLong=" + mapBreakLocLong +
//                ", mapBreakTime='" + mapBreakTime + '\'' +
//                ", mapBreakLabCode='" + mapBreakLabCode + '\'' +
//                '}';
    }
}
