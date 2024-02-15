package com.ao8r.labapp.models;

public class GetBreakIdTrackLocationsModel {
    private String breakId;

    public GetBreakIdTrackLocationsModel() {
    }

    public GetBreakIdTrackLocationsModel(String breakId) {
        this.breakId = breakId;
    }

    public String getBreakId() {
        return breakId;
    }

    public void setBreakId(String breakId) {
        this.breakId = breakId;
    }

    @Override
    public String toString() {
        return breakId;
//                "GetBreakIdTrackLocationsModel{" +
//                "breakId='" + breakId + '\'' +
//                '}';
    }
}
