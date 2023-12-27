package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetOriginXandY {
    static Connection connection;

    public static void getSampleXAndY(final Context context, String labCodeTem, String sampleCodeTem) {

        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");

            } else {

                String query = "SELECT * FROM lab_samples WHERE lab_code = ? AND sample_code = ?;";
//                    Statement statement = connection.createStatement();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, labCodeTem);
                statement.setString(2, sampleCodeTem);


                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next() == false) {
                    System.out.println("Empty");
                    CustomToast.customToast(context.getApplicationContext(), "عفو هذه النقطه غير مدرجة  فى هذا المسار");
                    resultSet.beforeFirst();
                } do {
                    ReferenceData.originX = resultSet.getString("sample_x");
                    ReferenceData.originY = resultSet.getString("sample_y");
                    ReferenceData.originSampleName = resultSet.getString("sample_name");
                    ReferenceData.originBatchName = resultSet.getString("batche_name");
                    System.out.println("API "+resultSet.getString("sample_x"));
                    System.out.println("API "+resultSet.getString("sample_y"));
                    System.out.println("API "+resultSet.getString("sample_name"));
                    System.out.println("API "+resultSet.getString("batche_name"));



                }while (resultSet.next());
            }
        } catch (SQLException e) {
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

