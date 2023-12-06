package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomTimeAndDate;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertIntoLabsDailyTests {
    static Connection connection;

    public static void insertIntoLabsDailyTests(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "INSERT INTO labs_daily_tests(test_code, test_name_arabic, test_name_english," +
                        " test_max_value, test_min_value, test_unit, test_spesifc_code, sample_kind," +
                        " test_period, test_pointers_kind, test_result_time, test_value, lab_code, " +
                        "lab_name, general_serial, batche_name, sample_code, sample_lab_name, sample_lab_code, sample_name, " +
                        "sample_descraption, sample_x, sample_y, test_group_name, test_entering_date, test_date," +
                        "test_hour, test_hour_code, test_value_one_year_ago, chemist_user_name, sector_name," +
                        "test_value_out_of_range, out_of_range_text)" +
                        "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setString(1, ReferenceData.testCode);
                statement.setString(2, ReferenceData.testNameEnglish);
                statement.setString(3, ReferenceData.testNameArabic);
                statement.setString(4, String.valueOf(ReferenceData.testMaxValue));
                statement.setString(5, String.valueOf(ReferenceData.testMinValue));
                statement.setString(6, ReferenceData.testUnit);
                statement.setString(7, ReferenceData.specificCode);
                statement.setString(8, ReferenceData.onSiteTestSampleKind);
                statement.setString(9, ReferenceData.testPeriod);
                statement.setString(10, ReferenceData.pointersKind);
                statement.setString(11, ReferenceData.testResultTime);
                statement.setString(12, ReferenceData.testValue);
                statement.setString(13, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(14, ReferenceData.onSiteTestLabName);
                statement.setInt(15, ReferenceData.maxGeneralSerial);
                statement.setString(16, ReferenceData.onSiteTestBatchName);
                statement.setString(17, ReferenceData.onSiteTestSampleCode);
                statement.setString(18, ReferenceData.onSiteTestLabName);
                statement.setString(19, ReferenceData.onSiteTestSampleLabCode);
                statement.setString(20, ReferenceData.onSiteTestSampleName);
                statement.setString(21, ReferenceData.onSiteTestSampleDescription);
                statement.setString(22, ReferenceData.onTestXLoc);
                statement.setString(23, ReferenceData.onTestYLoc);
                statement.setString(24, ReferenceData.onSiteTestGroupName);
                statement.setDate(25, CustomTimeAndDate.getCurrentDate());
                statement.setDate(26, CustomTimeAndDate.getCurrentDate());
                statement.setString(27, ReferenceData.onSiteTestHour);
                statement.setString(28, ReferenceData.onSiteTestHourCode);
                statement.setDouble(29,ReferenceData.onSiteTestValueOneYearAgo);
                statement.setString(30,ReferenceData.onSiteTestChemistUserName);
                statement.setString(31,ReferenceData.onSiteTestSectorName);
                statement.setString(32,ReferenceData.testValueOutOfRange);
                statement.setString(33,ReferenceData.outOfRangeText);





                System.out.println("BEFORE-DAILY-Labs--tests-DATA" +
                        ReferenceData.testCode +
                        ReferenceData.testNameArabic +
                        ReferenceData.testNameEnglish +
                        ReferenceData.testMaxValue +
                        ReferenceData.testMinValue +
                        ReferenceData.testUnit +
                        ReferenceData.specificCode +
                        ReferenceData.onSiteTestSampleKind +
                        ReferenceData.testPeriod +
                        ReferenceData.pointersKind  +
                        ReferenceData.testResultTime  +
                        ReferenceData.testValue+
                        ReferenceData.onSiteTestSampleLabCode+
                        ReferenceData.onSiteTestSampleCode+
                        ReferenceData.currentUserId

                );

                statement.executeUpdate();

                System.out.println("AFTER-DAILY-Labs--tests-DATA" +
                        ReferenceData.testCode +
                        ReferenceData.testNameArabic +
                        ReferenceData.testNameEnglish +
                        ReferenceData.testMaxValue +
                        ReferenceData.testMinValue +
                        ReferenceData.testUnit +
                        ReferenceData.specificCode +
                        ReferenceData.onSiteTestSampleKind +
                        ReferenceData.testPeriod +
                        ReferenceData.pointersKind  +
                        ReferenceData.testResultTime  +
                        ReferenceData.testValue+
                        ReferenceData.onSiteTestSampleLabCode+
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
