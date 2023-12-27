package com.ao8r.labapp.repository;
import android.content.Context;
import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.MapParamsModel;
import com.ao8r.labapp.models.ReferenceData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllPathLocationsPoints {
    static Connection connection;

    public static ArrayList<MapParamsModel> getAllPathLocationsPoints(Context context) {
        ArrayList<MapParamsModel> mapParamsModelArrayList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query
//              "SELECT * FROM Branch";

                String updateQuery = "SELECT * FROM lab_samples WHERE lab_code = ? AND batche_name = ?;";

                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, ReferenceData.labCodeLt);
                statement.setString(2, ReferenceData.originBatchName);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get ALL DATA
                    MapParamsModel mapParamsModel = new MapParamsModel();
                    mapParamsModel.setMapLocationLat(resultSet.getDouble("sample_x"));
                    mapParamsModel.setMapLocationLong(resultSet.getDouble("sample_y"));
                    mapParamsModel.setMapSampleName(resultSet.getString("sample_name"));

                    mapParamsModelArrayList.add(mapParamsModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getDouble("sample_x"));
                    System.out.println(resultSet.getDouble("sample_y"));
                    System.out.println(resultSet.getString("sample_name"));


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
        return mapParamsModelArrayList;
    }
}
