package com.ao8r.labapp.repository;

import android.content.Context;
import android.content.Intent;


import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.ReferenceData;
import com.ao8r.labapp.views.MenuScreen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginUsingJdbc {
    static Connection connection;

    public static void loginUsingJdbc(final Context context, final String loginPassword) {

        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "الخادم غير متصل(offline)");

            } else {

                String query = "SELECT * FROM user_details WHERE user_name= ? AND user_password = ?";
//                    Statement statement = connection.createStatement();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, ReferenceData.loginUser);
                statement.setString(2, loginPassword);
//                statement.setInt(3, ReferenceData.currentUserId);

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {
                    CustomToast.customToast(context, "تم تسجيل الدخول بنجاح");
                    ReferenceData.currentUserId = resultSet.getInt("id");
                    ReferenceData.userControl = resultSet.getInt("user_control");
//
                    Intent intent = new Intent(context, MenuScreen.class);
                    context.startActivity(intent);

                } else {
                    CustomToast.customToast(context, "بيانات خاطئة");

                }
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
