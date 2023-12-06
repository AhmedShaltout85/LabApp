package com.ao8r.labapp.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class GetAllValuesOfOnSiteTests {
    static Connection connection;

    public static void getAllValuesOfOnSiteTests(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

                String updateQuery = "SELECT * FROM on_site_tests " +
                        " WHERE test_name_arabic = ? " +
                        " And test_sample_kind = ? " +
                        " And test_min_value = ? " ;



                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setString(1, ReferenceData.testNameArabic);
                statement.setString(2, ReferenceData.onSiteTestSampleKind);
                statement.setDouble(3, ReferenceData.testMinValue);
//                statement.setDouble(4, ReferenceData.testMaxValue);

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {



                    ReferenceData.testCode = resultSet.getString("test_code");
                    ReferenceData.testNameEnglish = resultSet.getString("test_name_english");
//                    ReferenceData.testMaxValue = resultSet.getLong("test_max_value");
//                    ReferenceData.testMinValue = resultSet.getLong("test_min_value");
                    ReferenceData.testUnit = resultSet.getString("test_unit");
                    ReferenceData.specificCode = resultSet.getString("spesifc_code");
//                    ReferenceData.testSampleKind = resultSet.getString("test_sample_kind");
                    ReferenceData.testPeriod = resultSet.getString("test_period");
                    ReferenceData.pointersKind = resultSet.getString("pointers_kind");
                    ReferenceData.testResultTime = resultSet.getString("test_result_time");


//
                    System.out.println("تم جلب البيانات من ON_Site_TESTS"+ "\n");
                    System.out.println(ReferenceData.testCode +"\n");
                    System.out.println(ReferenceData.testNameEnglish +"\n");
                    System.out.println(resultSet.getString("spesifc_code"));
                    CustomToast.customToast(context, "تم جلب البيانات من ON_Site_TESTS"+ "\n");






                } else {
                    System.out.println("لم يتم جلب البيانات من on_Site_TESTS");
                    CustomToast.customToast(context, "لم يتم جلب البيانات من labs_tests");

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
