
package com.ao8r.labapp.repository;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;

        import androidx.annotation.RequiresApi;

        import com.ao8r.labapp.controller.ConnectionHelper;

        import com.ao8r.labapp.customiz.CustomToast;
        import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
        import com.ao8r.labapp.models.ReferenceData;


        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;


@RequiresApi(api = Build.VERSION_CODES.O)

public class GetMaxSampleCodeForBrokenPoint {

    static Connection connection;

    public static void getMaxSampleCode(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

                String updateQuery = "SELECT MAX(sample_code + 1) AS mSampleCode FROM lab_samples WHERE lab_code = ?;";

                PreparedStatement statement = connection.prepareStatement(updateQuery);

               statement.setInt(1, Integer.parseInt(ReadWriteFileFromInternalMem.getLabCodeFromFile()));

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {

                    ReferenceData.maxSampleCode = resultSet.getInt("mSampleCode");


//
                    System.out.println("LabCode is:   "+ReferenceData.labCode);
                    System.out.println("maxSampleCode is:   "+ReferenceData.maxSampleCode);


                } else {
                    CustomToast.customToast(context, "Can not get labCode");

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


