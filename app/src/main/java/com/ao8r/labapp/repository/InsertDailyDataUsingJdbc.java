package com.ao8r.labapp.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiresApi(api = Build.VERSION_CODES.O)

public class InsertDailyDataUsingJdbc {

    static Connection connection;

    public static void insertDailySample(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
//                String updateQuery = "INSERT INTO location_details(d_t, qr_lab_code, x_lati, y_longi, notes, qr_sample_code, sampling_lab_code, user_id, test_time, distance_in_meter)VALUES(?,?,?,?,?,?,?,?,?,?)";
//                String updateQuery = "INSERT INTO location_details(d_t, qr_lab_code, x_lati, y_longi, notes, qr_sample_code, sampling_lab_code, user_id, test_time, distance_in_meter)VALUES(?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE x_lati = ?, y_longi = ?, test_time = ?, distance_in_meter = ?;";
                String updateQuery = "IF EXISTS(SELECT * FROM location_details WHERE d_t=? AND qr_lab_code=? AND qr_sample_code=?) \n" +
                        " UPDATE location_details SET x_lati = ?, y_longi = ?, test_time = ?, distance_in_meter = ? \n" +
                        "WHERE d_t=? AND qr_lab_code=? AND qr_sample_code=?\n" +
                        "ELSE INSERT INTO location_details(d_t, qr_lab_code, x_lati, y_longi, notes, qr_sample_code," +
                        "sampling_lab_code, user_id, test_time, distance_in_meter)VALUES(?,?,?,?,?,?,?,?,?,?);";

//                Statement statement = connection.createStatement();

                PreparedStatement statement = connection.prepareStatement(updateQuery);
//                ReferenceData referenceData = new ReferenceData();
//                statement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
                statement.setDate(1, CustomTimeAndDate.getCurrentDate());
                statement.setString(2, ReferenceData.labCodeLt);
                statement.setString(3, ReferenceData.sampleCodeLt);
                statement.setString(4, ReferenceData.xLatitude);
                statement.setString(5, ReferenceData.yLongitude);
                statement.setTime(6, CustomTimeAndDate.getCurrentTime());
                statement.setString(7,String.valueOf(ReferenceData.disBetweenTowPoints).substring(0,3));
                statement.setDate(8, CustomTimeAndDate.getCurrentDate());
                statement.setString(9, ReferenceData.labCodeLt);
                statement.setString(10, ReferenceData.sampleCodeLt);
                ////////////////
                statement.setDate(11, CustomTimeAndDate.getCurrentDate());
                statement.setString(12, ReferenceData.labCodeLt);
                statement.setString(13, ReferenceData.xLatitude);
                statement.setString(14, ReferenceData.yLongitude);
                statement.setString(15, ReferenceData.notes);
                statement.setString(16, ReferenceData.sampleCodeLt);
                statement.setString(17, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setLong(18, ReferenceData.currentUserId);
                statement.setTime(19, CustomTimeAndDate.getCurrentTime());
                statement.setString(20,String.valueOf(ReferenceData.disBetweenTowPoints).substring(0,3));



                System.out.println("BEFORE-DAILY-DATA" +
                        CustomTimeAndDate.getCurrentDate() +
                        ReferenceData.labCodeLt +
                        ReferenceData.xLatitude +
                        ReferenceData.yLongitude +
                        ReferenceData.notes +
                        ReferenceData.sampleCodeLt +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.currentUserId +
                        CustomTimeAndDate.getCurrentTime()+
                        ReferenceData.disBetweenTowPoints

                );

                statement.executeUpdate();

                System.out.println("AFTER-DAILY-DATA" +
                        CustomTimeAndDate.getCurrentDate() +
                        ReferenceData.labCodeLt +
                        ReferenceData.xLatitude +
                        ReferenceData.yLongitude +
                        ReferenceData.notes +
                        ReferenceData.sampleCodeLt +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.currentUserId +
                        CustomTimeAndDate.getCurrentTime()+
                        ReferenceData.disBetweenTowPoints
                );

                CustomToast.customToast(context, "تم الحفظ بنجاح");


            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            CustomToast.customToast(context, "لم يتم الارسال");

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}


