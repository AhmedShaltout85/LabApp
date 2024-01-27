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

@RequiresApi(api = Build.VERSION_CODES.O)

public class InsertLocationsToTrackBreakTB {

    static Connection connection;

    public static void insertLocationsToTrackBreakTB(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "INSERT INTO Track_break_locations(" +
                        "break_date,"+
                        "break_id,"+
                        "break_time,"+
                        "lab_code, " +
                        "lab_name, " +
                        "location_x, " +
                        "location_y, " +
                        "sector_name, " +
                        "user_id)" +
                        "VALUES" +
                        "(?,?,?,?,?,?,?,?,?)";


                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setDate(1, CustomTimeAndDate.getCurrentDate());
                statement.setInt(2, 0);
                statement.setTime(3, CustomTimeAndDate.getCurrentTime());
                statement.setString(4, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(5, ReferenceData.labName);
                statement.setString(6, ReferenceData.sampleBrokenX);
                statement.setString(7,ReferenceData.sampleBrokenY);
                statement.setString(8, ReferenceData.sectorName);
                statement.setLong(9,ReferenceData.currentUserId);



                System.out.println("Periodic-Insert-BROKEN-LOCATIONS" +
                        CustomTimeAndDate.getCurrentDate() +
                        0 +
                        CustomTimeAndDate.getCurrentTime() +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.labName +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
                        ReferenceData.sectorName +
                        ReferenceData.currentUserId


                );

                statement.executeUpdate();

                System.out.println("AFTER-Periodic-Insert-BROKEN-LOCATIONS" +
                        CustomTimeAndDate.getCurrentDate() +
                        0 +
                        CustomTimeAndDate.getCurrentTime() +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.labName +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
                        ReferenceData.sectorName +
                        ReferenceData.currentUserId
                );

//                CustomToast.customToast(context, "تم الحفظ بنجاح");


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


