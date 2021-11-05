/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class DBDivisions {

    public static Divisions getDivisionData(int divID) throws SQLException, Exception{

         try {
             String sql = "select * FROM first_level_divisions WHERE Division_ID  = '" + divID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 int divId = rs.getInt("Division_ID");
                 String divName = rs.getString("Division");
                 String createDate = rs.getString("Create_Date");
                 String createdBy = rs.getString("Created_By");
                 String lastUpdate = rs.getString("Last_Update");
                 String lastUpdateby = rs.getString("Last_Updated_By");
                 Calendar createDateCalendar = stringToCalendar(createDate);
                 Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                 int divCountry = rs.getInt("Country_ID");
                 Divisions divResult = new Divisions(divId, divName, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, divCountry);

                 return divResult;
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
        return null;
     }
    public static ObservableList<Divisions> getAllContacts() throws SQLException, Exception{
        ObservableList<Divisions> allDivisions=FXCollections.observableArrayList();
        try {
            String sql = "select * from first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int divId = rs.getInt("Division_ID");
                String divName = rs.getString("Division");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdateby = rs.getString("Last_Updated_By");
                Calendar createDateCalendar = stringToCalendar(createDate);
                Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                int divCountry = rs.getInt("Country_ID");
                Divisions divResult = new Divisions(divId, divName, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, divCountry);
                allDivisions.add(divResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allDivisions;
    } 
}
