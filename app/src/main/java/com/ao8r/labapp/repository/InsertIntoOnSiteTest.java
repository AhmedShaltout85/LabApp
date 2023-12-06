package com.ao8r.labapp.repository;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertIntoOnSiteTest {
    static Connection connection;

    public static void insertIntoOnSiteTest(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "INSERT INTO on_site_tests(test_code, test_name_arabic, test_name_english," +
                        " test_max_value, test_min_value, test_unit, spesifc_code, test_sample_kind," +
                        " test_period, pointers_kind, test_result_time, test_value, lab_code, lab_sample, user_id)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setString(1, ReferenceData.testCode);
                statement.setString(2, ReferenceData.testNameArabic);
                statement.setString(3, ReferenceData.testNameEnglish);
                statement.setString(4, String.valueOf(ReferenceData.testMaxValue));
                statement.setString(5, String.valueOf(ReferenceData.testMinValue));
                statement.setString(6, ReferenceData.testUnit);
                statement.setString(7, ReferenceData.specificCode);
                statement.setString(8, ReferenceData.testSampleKind);
                statement.setString(9, ReferenceData.testPeriod);
                statement.setString(10, ReferenceData.pointersKind);
                statement.setString(11, ReferenceData.testResultTime);
                statement.setString(12, ReferenceData.testValue);
                statement.setString(13, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(14, ReferenceData.onSiteTestSampleCode);
                statement.setLong(15, ReferenceData.currentUserId);




                System.out.println("BEFORE--ON--SITE-DATA" +
                        ReferenceData.testCode +
                        ReferenceData.testNameArabic +
                        ReferenceData.testNameEnglish +
                        ReferenceData.testMaxValue +
                        ReferenceData.testMinValue +
                        ReferenceData.testUnit +
                        ReferenceData.specificCode +
                        ReferenceData.testSampleKind +
                        ReferenceData.testPeriod +
                        ReferenceData.pointersKind  +
                        ReferenceData.testResultTime  +
                        ReferenceData.testValue+
                        ReadWriteFileFromInternalMem.getLabCodeFromFile()+
                        ReferenceData.onSiteTestSampleCode+
                        ReferenceData.currentUserId

                );

                statement.executeUpdate();

                System.out.println("AFTER--ON--SITE-DATA" +
                        ReferenceData.testCode +
                        ReferenceData.testNameArabic +
                        ReferenceData.testNameEnglish +
                        ReferenceData.testMaxValue +
                        ReferenceData.testMinValue +
                        ReferenceData.testUnit +
                        ReferenceData.specificCode +
                        ReferenceData.testSampleKind +
                        ReferenceData.testPeriod +
                        ReferenceData.pointersKind  +
                        ReferenceData.testResultTime  +
                        ReferenceData.testValue+
                        ReadWriteFileFromInternalMem.getLabCodeFromFile()+
                        ReferenceData.onSiteTestSampleCode+
                        ReferenceData.currentUserId
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
