package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetMaxGeneralSerialFromLabsDailyTests {

    static Connection connection;

    public static void getMaxGeneralSerial(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

//                String updateQuery = "SELECT MAX(general_serial + 1) AS mGeneralSerial FROM labs_daily_tests WHERE lab_code =?;";
                String updateQuery = "SELECT MAX(general_serial + 1) AS mGeneralSerial FROM labs_daily_tests ;";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

//                statement.setInt(1, Integer.parseInt(ReferenceData.onSiteTestSampleLabCode));

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {

                    ReferenceData.maxGeneralSerial = resultSet.getInt("mGeneralSerial");


//

                    System.out.println("mGeneralSerial is:   "+ReferenceData.maxGeneralSerial);




                } else {
                    CustomToast.customToast(context, "Can not get GeneralSerial");

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
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
