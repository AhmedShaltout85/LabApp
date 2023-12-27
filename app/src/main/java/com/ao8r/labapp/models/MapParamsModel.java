package com.ao8r.labapp.models;

public class MapParamsModel {
    private double mapLocationLat;
    private double mapLocationLong;
    private String mapSampleName;


    public MapParamsModel() {
    }

    public MapParamsModel(double mapLocationLat, double mapLocationLong, String mapSampleName) {
        this.mapLocationLat = mapLocationLat;
        this.mapLocationLong = mapLocationLong;
        this.mapSampleName = mapSampleName;
    }

    public double getMapLocationLat() {
        return mapLocationLat;
    }

    public void setMapLocationLat(double mapLocationLat) {
        this.mapLocationLat = mapLocationLat;
    }

    public double getMapLocationLong() {
        return mapLocationLong;
    }

    public void setMapLocationLong(double mapLocationLong) {
        this.mapLocationLong = mapLocationLong;
    }

    public String getMapSampleName() {
        return mapSampleName;
    }

    public void setMapSampleName(String mapSampleName) {
        this.mapSampleName = mapSampleName;
    }

    @Override
    public String toString() {
        return "MapParamsModel{" +
                "mapLocationLat=" + mapLocationLat +
                ", mapLocationLong=" + mapLocationLong +
                ", mapSampleName='" + mapSampleName + '\'' +
                '}';
    }
}
