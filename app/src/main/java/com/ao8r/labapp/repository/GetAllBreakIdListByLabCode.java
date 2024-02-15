package com.ao8r.labapp.repository;

import android.content.Context;

import com.ao8r.labapp.controller.ConnectionHelper;
import com.ao8r.labapp.customiz.CustomToast;
import com.ao8r.labapp.models.GetBreakIdTrackLocationsModel;
import com.ao8r.labapp.models.ReferenceData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllBreakIdListByLabCode {
    static Connection connection;

    public static ArrayList<GetBreakIdTrackLocationsModel> getAllBreakIdListByLabCodeBreakPointsDropdown(Context context) {
        ArrayList<GetBreakIdTrackLocationsModel> getBreakIdTrackLocationsModels = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();

            if (connection == null) {
                CustomToast.customToast(context, "عفو لايمكن الأتصال بالخادم");
            } else {
                CustomToast.customToast(context, "جارى الأتصال بقاعدة البيانات");
                //Query

//                Select Distinct break_ID From Track_break_locations Where lab_code = ? Order By Break_ID Asc

                String updateQuery = "SELECT Distinct break_id FROM Track_break_locations WHERE lab_code = ? ORDER BY break_id ASC";


                PreparedStatement statement = connection.prepareStatement(updateQuery);
                statement.setString(1, ReferenceData.TRACK_LAB_CODE);

                ResultSet resultSet = statement.executeQuery();


                while (resultSet.next()) {
// get ALL DATA
                    GetBreakIdTrackLocationsModel getBreakIdTrackLocationsModel = new GetBreakIdTrackLocationsModel();
                    getBreakIdTrackLocationsModel.setBreakId(resultSet.getString("break_id"));

                    getBreakIdTrackLocationsModels.add(getBreakIdTrackLocationsModel);

//                    CustomToast.customToast(context, "تم جلب البيانات");
                    System.out.println(resultSet.getString("break_id"));



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
        return getBreakIdTrackLocationsModels;
    }
}
