
package com.ao8r.labapp.repository;

        import android.content.Context;
        import android.os.Build;

        import androidx.annotation.RequiresApi;

        import com.ao8r.labapp.controller.ConnectionHelper;
        import com.ao8r.labapp.customiz.CustomTimeAndDate;
        import com.ao8r.labapp.customiz.CustomToast;
        import com.ao8r.labapp.customiz.ReadWriteFileFromInternalMem;
        import com.ao8r.labapp.models.ReferenceData;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.SQLException;


@RequiresApi(api = Build.VERSION_CODES.O)

public class InsertBrokenPointInDailyTB {

    static Connection connection;

    public static void insertBrokenPointInDailyTB(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "INSERT INTO location_details(d_t, qr_lab_code, x_lati, y_longi, notes, qr_sample_code, sampling_lab_code, user_id, test_time, distance_in_meter)VALUES(?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement statement = connection.prepareStatement(updateQuery);


                statement.setDate(1, CustomTimeAndDate.getCurrentDate());
                statement.setString(2, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(3, ReferenceData.sampleBrokenX);
                statement.setString(4, ReferenceData.sampleBrokenY);
                statement.setString(5, ReferenceData.notesBroken);
                statement.setString(6, String.valueOf(ReferenceData.maxSampleCode));
                statement.setString(7,ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setLong(8, ReferenceData.currentUserId);
                statement.setTime(9, CustomTimeAndDate.getCurrentTime());
                statement.setString(10,String.valueOf(ReferenceData.disBetweenTowPoints).substring(0,3));



                System.out.println("BEFORE-DAILY-Point-DATA" +
                        CustomTimeAndDate.getCurrentDate() +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
                        ReferenceData.notesBroken +
                        ReferenceData.maxSampleCode +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.currentUserId +
                        CustomTimeAndDate.getCurrentTime()+
                        ReferenceData.disBetweenTowPoints

                );

                statement.executeUpdate();

                System.out.println("AFTER-DAILY-Point-DATA" +
                        CustomTimeAndDate.getCurrentDate() +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
                        ReferenceData.notesBroken +
                        ReferenceData.maxSampleCode +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.currentUserId +
                        CustomTimeAndDate.getCurrentTime()+
                        ReferenceData.disBetweenTowPoints
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


