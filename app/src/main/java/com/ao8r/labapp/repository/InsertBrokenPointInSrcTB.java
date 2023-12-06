

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
        import java.sql.SQLException;



@RequiresApi(api = Build.VERSION_CODES.O)

public class InsertBrokenPointInSrcTB {

    static Connection connection;

    public static void insertBrokenPointInSrcTB(Context context) {
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بقاعدة البيانات");
            } else {
                String updateQuery = "INSERT INTO lab_samples(sector_name, lab_name, lab_code, batche_name, sample_code, sample_name, sample_kind, sample_descraption, sample_x, sample_y, user_id)VALUES(?,?,?,?,?,?,?,?,?,?,?)";


                PreparedStatement statement = connection.prepareStatement(updateQuery);

                ////////////////
                statement.setString(1, ReferenceData.sectorName);
                statement.setString(2, ReferenceData.labName);
                statement.setString(3, ReadWriteFileFromInternalMem.getLabCodeFromFile());
                statement.setString(4, ReferenceData.batchName);
                statement.setInt(5, ReferenceData.maxSampleCode);
                statement.setString(6, ReferenceData.sampleName);
                statement.setString(7,ReferenceData.sampleKind);
                statement.setString(8, ReferenceData.sampleDescription);
                statement.setString(9, ReferenceData.sampleBrokenX);
                statement.setString(10, ReferenceData.sampleBrokenY);
                statement.setLong(11,ReferenceData.currentUserId);



                System.out.println("BEFORE-SRC-Point-DATA" +
                        ReferenceData.sectorName +
                        ReferenceData.labName +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.batchName +
                        ReferenceData.maxSampleCode +
                        ReferenceData.sampleName +
                        ReferenceData.sampleKind +
                        ReferenceData.currentUserId +
                        ReferenceData.sampleDescription +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
                        ReferenceData.currentUserId


                );

                statement.executeUpdate();

                System.out.println("AFTER-SRC-Point-DATA" +
                        ReferenceData.sectorName +
                        ReferenceData.labName +
                        ReadWriteFileFromInternalMem.getLabCodeFromFile() +
                        ReferenceData.batchName +
                        ReferenceData.maxSampleCode +
                        ReferenceData.sampleName +
                        ReferenceData.sampleKind +
                        ReferenceData.currentUserId +
                        ReferenceData.sampleDescription +
                        ReferenceData.sampleBrokenX +
                        ReferenceData.sampleBrokenY +
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


