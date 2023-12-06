package com.ao8r.labapp.repository;

import android.content.Context;
import android.content.Intent;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.views.MenuScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfirmLabNameWithSrcData {

    static Connection connection;

    public static void getLabName(final Context context) {

        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");

            } else {

                String query = "SELECT * FROM lab_samples WHERE lab_code = ? AND sample_code = ?;";
//                    Statement statement = connection.createStatement();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(2, ReferenceData.sampleCode);


                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next() == false) {
                    System.out.println("Empty");
                    resultSet.beforeFirst();
                } do {
                    ReferenceData.labName = resultSet.getString("lab_name");
                    ReferenceData.sampleName = resultSet.getString("sample_name");
                    System.out.println(resultSet.getString("lab_name"));
                    System.out.println(resultSet.getString("sample_name"));
//                    System.out.println(resultSet.getInt("src_id"));


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
