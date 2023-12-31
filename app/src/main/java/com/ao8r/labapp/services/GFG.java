package com.ao8r.labapp.services;

// Java program to calculate Distance Between
// Two Points on Earth

import java.util.*;
import java.lang.*;

public class GFG {

//    public static double distance(double lat1,
//                                  double lat2,
//                                  double lon1,
//                                  double lon2)
//    {
//
//        // The math module contains a function
//        // named toRadians which converts from
//        // degrees to radians.
//        lon1 = Math.toRadians(lon1);
//        lon2 = Math.toRadians(lon2);
//        lat1 = Math.toRadians(lat1);
//        lat2 = Math.toRadians(lat2);
//
//        // Haversine formula
//        double dlon = lon2 - lon1;
//        double dlat = lat2 - lat1;
//        double a = Math.pow(Math.sin(dlat / 2), 2)
//                + Math.cos(lat1) * Math.cos(lat2)
//                * Math.pow(Math.sin(dlon / 2),2);
//
//        double c = 2 * Math.asin(Math.sqrt(a));
//
//        // Radius of earth in kilometers. Use 3956
//        // for miles
//        double r = 6371;
//
//        // calculate the result
//        return(c * r);
//    }

//    // driver code
//    public static void main(String[] args)
//    {
//        double lat1 = 53.32055555555556;
//        double lat2 = 53.31861111111111;
//        double lon1 = -1.7297222222222221;
//        double lon2 = -1.6997222222222223;
//        System.out.println(distance(lat1, lat2,
//                lon1, lon2) + " K.M");
//    }
//}

// This code is contributed by Prasad Kshirsagar
//
//public static double distance(double lat1, double lat2, double lon1,
//                              double lon2, double el1, double el2) {
//
//    final int R = 6371; // Radius of the earth
//
//    double latDistance = Math.toRadians(lat2 - lat1);
//    double lonDistance = Math.toRadians(lon2 - lon1);
//    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
//            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
//            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
//    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//    double distance = R * c * 1000; // convert to meters
//
//    double height = el1 - el2;
//
//    distance = Math.pow(distance, 2) + Math.pow(height, 2);
//
//    return Math.sqrt(distance);
//}

//chatGPT3

    public static double distance(double lat1,
                                  double lon1,
                                  double lat2,
                                  double lon2) {
        final double R = 6371e3; // Earth's radius (in meters)
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2 - lat1);
        double Δλ = Math.toRadians(lon2 - lon1);

        double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Distance in meters
    }

//    kiloMeter
//public static final double RADIUS_OF_EARTH_KM = 6371;
//
//    public static double distance(double lat1, double lon1, double lat2, double lon2) {
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//        return RADIUS_OF_EARTH_KM * c;
//    }
}