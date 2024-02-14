package com.ao8r.labapp.repository;



import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.BreakPointsModel;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GetAllLabCodeListForBreakPointsDropdown {
    static Connection connection;

    public static ArrayList<BreakPointsModel> getAllLabCodeListForBreakPointsDropdown(Context context) {
        ArrayList<BreakPointsModel> breakPointsModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query
//              "SELECT * FROM Branch";

                String updateQuery = "SELECT Distinct lab_code FROM Track_break_locations ORDER BY Lab_Code ASC";


                PreparedStatement statement = connection.prepareStatement(updateQuery);
//                statement.setString(1,onSiteTestSampleKind);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get ALL DATA
                    BreakPointsModel breakPointsModel = new BreakPointsModel();
                    breakPointsModel.setMapLabCode(resultSet.getString("lab_code"));

                    breakPointsModelArrayList.add(breakPointsModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getString("lab_code"));



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








