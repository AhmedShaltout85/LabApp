package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.BreakPointsModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllBreakPointTrackList {

    static Connection connection;

    public static ArrayList<BreakPointsModel> getAllBreakPointTrackList(Context context) {
        ArrayList<BreakPointsModel> breakPointsModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى تحديث ال TRACK");
                //Query
//              "SELECT * FROM Branch";

                String updateQuery = "SELECT * FROM Track_break_locations WHERE lab_code = ? AND break_date = ?;";

                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setDate(2, CustomTimeAndDate.getCurrentDate());

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get ALL DATA
                    BreakPointsModel breakPointsModel = new BreakPointsModel();
                    breakPointsModel.setMapBreakLocLat(resultSet.getDouble("location_x"));
                    breakPointsModel.setMapBreakLocLong(resultSet.getDouble("location_y"));
                    breakPointsModel.setMapBreakTime(resultSet.getString("break_time"));

                    breakPointsModelArrayList.add(breakPointsModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getDouble("location_x"));
                    System.out.println(resultSet.getDouble("location_y"));
                    System.out.println(resultSet.getString("break_time"));


                }


            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return breakPointsModelArrayList;
    }
}
