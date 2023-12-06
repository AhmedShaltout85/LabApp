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

public class GetSampleLabNameFromLabSamples {
    static Connection connection;

    public static void getSampleLabNameFromLabSamples(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

                String updateQuery = "SELECT * FROM lab_samples WHERE lab_code = ?";
//                Statement statement = connection.createStatement();
                ;

                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setString(1, ReadWriteFileFromInternalMem.getLabCodeFromFile());
//                statement.setString(2, ReferenceData.onSiteTestSampleCode);
//                statement.setString(2, String.valueOf(ReferenceData.maxSampleCode));

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {



                    ReferenceData.onSiteTestSampleLabName = resultSet.getString("lab_name");
//                    ReferenceData.onSiteTestSectorName = resultSet.getString("sector_name");
//                    ReferenceData.onSiteTestBatchName = resultSet.getString("batche_name");
//                    ReferenceData.onSiteTestSampleName = resultSet.getString("sample_name");
//                    ReferenceData.onSiteTestSampleKind = resultSet.getString("sample_kind");
//                    ReferenceData.onSiteTestSampleDescription = resultSet.getString("sample_descraption");




//
                    System.out.println("onSiteTestSampleLabName is:   "+ReferenceData.onSiteTestSampleLabName);
//                    System.out.println("onSiteTestSectorName is:   "+ReferenceData.onSiteTestSectorName);





                } else {
                    System.out.println("Can't get Labs Samples DATA");
                    CustomToast.customToast(context, "Can't get Labs Samples DATA");

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
