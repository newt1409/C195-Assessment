/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class DBAppointments {

     public static Appointments getUserAppointments(int userID) throws SQLException, Exception{

         try {
             String sql = "select * FROM appointments WHERE User_ID  = '" + userID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 int appId = rs.getInt("Appointment_ID");
                 String appName = rs.getString("Title");
                 String appDesc = rs.getString("Description");
                 String appLoc = rs.getString("Location");
                 String appType = rs.getString("Type");
                 String appStart = rs.getString("Start");
                 String appEnd = rs.getString("End");
                 String createDate = rs.getString("Create_Date");
                 String createdBy = rs.getString("Created_By");
                 String lastUpdate = rs.getString("Last_Update");
                 String lastUpdateby = rs.getString("Last_Updated_By");
                 Calendar createDateCalendar = stringToCalendar(createDate);
                 Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                 int custId = rs.getInt("Customer_ID");
                 int userId = rs.getInt("User_ID");
                 int contactId = rs.getInt("Contact_ID");

                 //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                 Appointments appResult = new Appointments(appId, appName, appDesc, appLoc, appType, appStart, appEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                 return appResult;

             }
         } catch (SQLException | ParseException throwables) {
             throwables.printStackTrace();
         }
         return null;
     }
    public static ObservableList<Appointments> getAllAppointments() throws SQLException, Exception{
        ObservableList<Appointments> allAppointments=FXCollections.observableArrayList();
        //DBConnection.openConnection();
        try {
            String sql = "select * from appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appId=rs.getInt("Appointment_ID");
                String appName=rs.getString("Title");
                String appDesc=rs.getString("Description");
                String appLoc=rs.getString("Location");
                String appType=rs.getString("Type");
                String appStart=rs.getString("Start");
                String appEnd=rs.getString("End");
                //String password=rs.getString("Password");
                //int active=result.getInt("active");
                //if(active==1) act=true;
                String createDate=rs.getString("Create_Date");
                String createdBy=rs.getString("Created_By");
                String lastUpdate=rs.getString("Last_Update");
                String lastUpdateby=rs.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);

                int custId=rs.getInt("Customer_ID");
                int userId=rs.getInt("User_ID");
                int contactId=rs.getInt("Contact_ID");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                Appointments appResult= new Appointments(appId, appName, appDesc, appLoc, appType, appStart, appEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                allAppointments.add(appResult);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DBConnection.closeConnection();
        return allAppointments;
    } 
}