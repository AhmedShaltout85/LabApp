package com.ao8r.labapp.repository;

import static com.ao8r.labapp.models.ReferenceData.onSiteTestSampleKind;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.OnSiteTestModel;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class GetAllOnSiteListForDropdown {
    static Connection connection;

    public static ArrayList<OnSiteTestModel> getAllOnSiteListForDropdown(Context context) {
        ArrayList<OnSiteTestModel> onSiteTestModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query
//              "SELECT * FROM Branch";

                String updateQuery = "SELECT * FROM [on_site_tests] WHERE test_sample_kind = ?";

                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1,onSiteTestSampleKind);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get ALL DATA
                    OnSiteTestModel onSiteTestModel = new OnSiteTestModel();
                    onSiteTestModel.setTestNameArabicInModel(resultSet.getString("test_name_arabic"));
                    onSiteTestModel.setTestMinValueInModel(resultSet.getDouble("test_min_value"));
                    onSiteTestModel.setTestMaxValueInModel(resultSet.getDouble("test_max_value"));
                    onSiteTestModelArrayList.add(onSiteTestModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getString("test_name_arabic"));
                    System.out.println(resultSet.getDouble("test_min_value"));
                    System.out.println(resultSet.getDouble("test_max_value"));


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
        return onSiteTestModelArrayList;
    }
}








