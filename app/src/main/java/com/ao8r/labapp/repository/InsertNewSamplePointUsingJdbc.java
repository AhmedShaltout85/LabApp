package com.ao8r.labapp.repository;

import androidx.annotation.RequiresApi;
import android.content.Context;
import android.os.Build;



import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@RequiresApi(api = Build.VERSION_CODES.O)
public class InsertNewSamplePointUsingJdbc {

     static Connection connection;

    public static void insertNewSample(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "UPDATE lab_samples SET sample_x =?, sample_y =?, user_id =? WHERE lab_code = ? AND sample_code = ?;";

//                Statement statement = connection.createStatement();

                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, ReferenceData.sampleX);
                statement.setString(2, ReferenceData.sampleY);
                statement.setLong(3, ReferenceData.currentUserId);
                statement.setString(4, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(5, ReferenceData.sampleCode);


                System.out.println("BEFORE-SOURCE-DATA"+ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.sampleCode+
                        ReferenceData.sampleX+
                        ReferenceData.sampleY+
                        ReferenceData.currentUserId);

                statement.executeUpdate();

                System.out.println("AFTER-SOURCE-DATA"+ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.sampleCode+
                        ReferenceData.sampleX+
                        ReferenceData.sampleY+
                        ReferenceData.currentUserId);

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
