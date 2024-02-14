package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.BreakPointsModel;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllBreakPointTrackListToDisplay {

    static Connection connection;

    public static ArrayList<BreakPointsModel> getAllBreakPointTrackListToDisplay(Context context) {
        ArrayList<BreakPointsModel> breakPointsModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى تحديث ال TRACK");
                //Query
//              "SELECT * FROM Branch";

//                String updateQuery = "SELECT * FROM Track_break_locations WHERE lab_code = ? AND break_date = ? ORDER BY break_time ASC;";
                String updateQuery = "SELECT * \n" +
                        "FROM \"Track_break_locations\" \n" +
                        "WHERE lab_code = ? AND break_id = ?\n" +
                        "AND break_date = ? \n" +
                        "ORDER BY break_time ASC ;";

                PreparedStatement statement = connection.prepareStatement(updateQuery);
//                statement.setString(1, ReadWriteFileFromInternalMem.getLabCodeFromFile());
//                statement.setDate(2, CustomTimeAndDate.getCurrentDate());

                statement.setString(1, ReferenceData.TRACK_LAB_CODE);
                statement.setString(2, ReferenceData.TRACK_BREAK_ID);
                statement.setDate(3, Date.valueOf(ReferenceData.TRACK_BREAK_DATE));

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
