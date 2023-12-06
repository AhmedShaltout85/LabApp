
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
        import java.sql.ResultSet;
        import java.sql.SQLException;


@RequiresApi(api = Build.VERSION_CODES.O)

public class GetLabAndSectorNameForBrokenPoint {

    static Connection connection;

    public static void getLabAndSectorNameForBrokenPoint(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {

                String updateQuery = "SELECT lab_name, sector_name FROM lab_samples WHERE lab_code = ?";
//                Statement statement = connection.createStatement();
               ;

                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setString(1, ReadWriteFileFromInternalMem.getLabCodeFromFile());
//                statement.setString(2, String.valueOf(ReferenceData.maxSampleCode));

                ResultSet resultSet = statement.executeQuery();


                if (resultSet.next()) {



                    ReferenceData.labName = resultSet.getString("lab_name");
                    ReferenceData.sectorName = resultSet.getString("sector_name");


//
                    System.out.println("LabCode is:   "+ReferenceData.labName);
                    System.out.println("SampleCode is:   "+ReferenceData.sectorName);





                } else {
                    System.out.println("Can't get lab, sector name");
                    CustomToast.customToast(context, "Can't get labName and sectorName");

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



