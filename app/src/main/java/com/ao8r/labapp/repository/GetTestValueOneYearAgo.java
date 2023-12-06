package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTestValueOneYearAgo {
    static Connection connection;

    public static void getTestValueOneYearAgo(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

//
//                SELECT ISNULL((SELECT Top 1 [test_value]
//                FROM [awco_labs].[dbo].[labs_daily_tests]
//                Where test_code = '442'
//                AND lab_code = '1'
//                AND test_date = CONVERT (DATE,GETDATE()-365)),
//                (SELECT Top 1 [test_value]
//                FROM [awco_labs].[dbo].[labs_daily_tests]
//                Where test_code = '442'
//                AND lab_code = '1'
//                AND CONVERT (DATE,test_date)
//                between CONVERT(DATE,GETDATE()-395) AND CONVERT(DATE,GETDATE()-365)));
//                String updateQuery = "SELECT test_value FROM labs_daily_tests WHERE test_date = ?";

                String updateQuery = "\n" +
                        "  SELECT ISNULL((SELECT Top 1 test_value\n" +
                        "  FROM labs_daily_tests\n" +
                        "  Where test_code = ?\n" +
                        "  AND lab_code = ?\n" +
                        "  AND test_date = CONVERT (DATE,GETDATE()-365)),(SELECT Top 1 test_value\n" +
                        "  FROM labs_daily_tests\n" +
                        "  Where test_code = ?\n" +
                        "  AND lab_code = ?\n" +
                        "  AND CONVERT (DATE,test_date) between CONVERT(DATE,GETDATE()-395) AND CONVERT(DATE,GETDATE()-365)))";

//                Statement statement = connection.createStatement();
                ;

                PreparedStatement statement = connection.prepareStatement(updateQuery);


//                statement.setString(1, "GETDATE() - 365");
                statement.setString(1, ReferenceData.testCode);
//                statement.setString(2, ReadWriteFileFromInternalMem.getLabCodeFromFile()); // edited 7-05-2023
                statement.setString(2, ReferenceData.onSiteTestSampleLabCode);
                statement.setString(3, ReferenceData.testCode);
//                statement.setString(4, ReferenceData.labCode); // edited 7-05-2023
                statement.setString(4, ReferenceData.onSiteTestSampleLabCode);


                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {

                    ReferenceData.onSiteTestValueOneYearAgo = resultSet.getFloat("test_value_one_year_ago");


//
                    System.out.println("تم جلب قيمة test_value" + "\n");

                    CustomToast.customToast(context, "تم جلب قيمة test_value" + "\n");


                } else {
                    System.out.println("لم يتم جلب قيمة test_value");
                    CustomToast.customToast(context, "لم يتم جلب قيمة test_value");

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
