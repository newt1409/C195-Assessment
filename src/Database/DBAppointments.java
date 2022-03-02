/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Controllers.MainScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import model.Appointments;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;

/**
 *
 *
 */
/* typically you would also have create, update and read methods*/

public class DBAppointments {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ZoneId localZoneID = ZoneId.systemDefault();



     public static ObservableList<Appointments> getUserAppointments(int userID) throws SQLException, Exception{

         try {
             String sql = "select * FROM appointments WHERE User_ID  = '" + userID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             ObservableList<Appointments> appResult = FXCollections.observableArrayList();
             while (rs.next()) {

                 //pulls start time from database and converts it into local time zone
                 Timestamp timestampStart = rs.getTimestamp("Start");
                 ZonedDateTime startUTC = timestampStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                 ZonedDateTime newLocalStart = startUTC.withZoneSameInstant(localZoneID);

                 //pulls end time from database and converts it into local time zone
                 Timestamp timestampEnd = rs.getTimestamp("end");
                 ZonedDateTime endUTC = timestampEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
                 ZonedDateTime newLocalEnd = endUTC.withZoneSameInstant(localZoneID);

                 int appId = rs.getInt("Appointment_ID");
                 String appName = rs.getString("Title");
                 String appDesc = rs.getString("Description");
                 String appLoc = rs.getString("Location");
                 String appType = rs.getString("Type");
                 //String appStart = rs.getString("Start");
                 //String appEnd = rs.getString("End");
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
                 Appointments appointment = new Appointments(appId, appName, appDesc, appLoc, appType, newLocalStart.toString(), newLocalEnd.toString(), createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                 appResult.addAll(appointment);
             }
             return appResult;
         } catch (SQLException | ParseException throwables) {
             throwables.printStackTrace();
         }
         return null;
     }

    public static Appointments getAppointment(int inAppID) throws SQLException, Exception {

        try {
            String sql = "select * FROM appointments WHERE Appointment_ID  = '" + inAppID + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ObservableList<Appointments> appResult = FXCollections.observableArrayList();
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
                Appointments appointment = new Appointments(appId, appName, appDesc, appLoc, appType, appStart, appEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                return appointment;
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void modAppointment(int appID, String appName, String appDesc, String appLoc, String appType, ZonedDateTime appStart, ZonedDateTime appEnd, int appCust, int appCont) throws SQLException, Exception{
        try {

            String modifiedBy = MainScreen.validUser.getUserName();
            int modifiedID = MainScreen.validUser.getUserId();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp sqlStartDT = Timestamp.valueOf(appStart.toLocalDateTime());
            Timestamp sqlEndDT = Timestamp.valueOf(appEnd.toLocalDateTime());
            String sql = "update appointments set Title = '" + appName + "', Description = '" + appDesc + "', Location = '" + appLoc +
                    "', Type = '" + appType + "', Start = '" + sqlStartDT + "', End = '" + sqlEndDT + "', Last_Update = '" + LocalDateTime.now().format(formatter) + "', Last_Updated_By = '" +
                    modifiedBy + "', Customer_ID = '" + appCust + "', User_ID = '" + modifiedID + "', Contact_ID = '" + appCont + "' where Appointment_ID = " + appID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        return allAppointments;
    }

    public static void addAppointment(String appName, String appDesc, String appLoc, String appType, ZonedDateTime appStart, ZonedDateTime appEnd, int appCust, int appCont) throws SQLException, Exception{
        try {

            String createdBy = MainScreen.validUser.getUserName();
            int userID = MainScreen.validUser.getUserId();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            Timestamp sqlStartDT = Timestamp.valueOf(appStart.toLocalDateTime());
            Timestamp sqlEndDT = Timestamp.valueOf(appEnd.toLocalDateTime());
            String sql = "insert into appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, " + "Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                         "values ('" + appName + "', '" + appDesc + "', '" + appLoc + "', '" + appType + "', '" + sqlStartDT + "', '" + sqlEndDT + "', '" + LocalDateTime.now().format(formatter) + "', '" +
                         createdBy + "', '" + LocalDateTime.now().format(formatter) + "', '" + createdBy + "', '" + appCust +"', '" + userID +"', '"+ appCont +"')";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delAppointment(int appID) throws SQLException, Exception{
        try {
            String sql = "delete from appointments where Appointment_ID = '" + appID +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
