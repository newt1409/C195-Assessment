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
 * Database interface for appointment methods
 *@author Weston Brehe
 */
public class DBAppointments {
    /**
     * Date time formatter for database format
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Local zone identifier
     */
    private static ZoneId localZoneID = ZoneId.systemDefault();
    /**
     * zone id for UTC
     */
    private static ZoneId utcZoneID = ZoneId.of("UTC");

    /**
     * Method to return the appointments for a given user "userID"
     * @param userID
     * @return appointments
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointments> getUserAppointments(int userID) throws SQLException, Exception{
         //call database to for user appointments
         try {
             String sql = "select * FROM appointments WHERE User_ID  = '" + userID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             ObservableList<Appointments> appResult = FXCollections.observableArrayList();
             while (rs.next()) {
                 int appId = rs.getInt("Appointment_ID");
                 String appName = rs.getString("Title");
                 String appDesc = rs.getString("Description");
                 String appLoc = rs.getString("Location");
                 String appType = rs.getString("Type");
                 String appStart = rs.getString("Start").substring(0, 19);
                 String appEnd = rs.getString("End").substring(0, 19);
                 String createDate = rs.getString("Create_Date");
                 String createdBy = rs.getString("Created_By");
                 String lastUpdate = rs.getString("Last_Update");
                 String lastUpdateby = rs.getString("Last_Updated_By");
                 Calendar createDateCalendar = stringToCalendar(createDate);
                 Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);

                 //convert utc to local
                 LocalDateTime utcStart = LocalDateTime.parse(appStart, formatter);
                 LocalDateTime utcEnd = LocalDateTime.parse(appEnd, formatter);

                 //convert utz zoneid to local zoneid
                 ZonedDateTime localZoneStart = utcStart.atZone(utcZoneID).withZoneSameInstant(localZoneID);
                 ZonedDateTime localZoneEnd = utcEnd.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                 //convert datetime variables to strings for easier manipulation
                 String localStart = localZoneStart.format(formatter);
                 String localEnd = localZoneEnd.format(formatter);

                 int custId = rs.getInt("Customer_ID");
                 int userId = rs.getInt("User_ID");
                 int contactId = rs.getInt("Contact_ID");

                 //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                 Appointments appointment = new Appointments(appId, appName, appDesc, appLoc, appType, localStart, localEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                 appResult.addAll(appointment);
             }
             return appResult;
         } catch (SQLException | ParseException throwables) {
             throwables.printStackTrace();
         }
         return null;
     }

    /**
     * Method to get a single appointment data
     * @param inAppID
     * @return appointment
     * @throws SQLException
     * @throws Exception
     */
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

                //convert utc to local
                LocalDateTime utcStart = LocalDateTime.parse(appStart, formatter);
                LocalDateTime utcEnd = LocalDateTime.parse(appEnd, formatter);

                //convert utz zoneid to local zoneid
                ZonedDateTime localZoneStart = utcStart.atZone(utcZoneID).withZoneSameInstant(localZoneID);
                ZonedDateTime localZoneEnd = utcEnd.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                //convert datetime variables to strings for easier manipulation
                String localStart = localZoneStart.format(formatter);
                String localEnd = localZoneEnd.format(formatter);

                int custId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                Appointments appointment = new Appointments(appId, appName, appDesc, appLoc, appType, localStart, localEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                return appointment;
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * Method to modify a given appointment and store that appointment into the database
     * @param appID
     * @param appName
     * @param appDesc
     * @param appLoc
     * @param appType
     * @param appStart
     * @param appEnd
     * @param appCust
     * @param appCont
     * @param appUserName
     * @param appUserId
     * @throws SQLException
     * @throws Exception
     */
    public static void modAppointment(int appID, String appName, String appDesc, String appLoc, String appType, ZonedDateTime appStart, ZonedDateTime appEnd, int appCust, int appCont, String appUserName, int appUserId) throws SQLException, Exception{
        try {
            String modifiedBy = appUserName;
            int modifiedID = appUserId;
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

    /**
     * Getter method to return all appointments in the database
     * @return
     * @throws SQLException
     * @throws Exception
     */
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
                String createDate=rs.getString("Create_Date");
                String createdBy=rs.getString("Created_By");
                String lastUpdate=rs.getString("Last_Update");
                String lastUpdateby=rs.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);

                //convert utc to local
                LocalDateTime utcStart = LocalDateTime.parse(appStart, formatter);
                LocalDateTime utcEnd = LocalDateTime.parse(appEnd, formatter);

                //convert utz zoneid to local zoneid
                ZonedDateTime localZoneStart = utcStart.atZone(utcZoneID).withZoneSameInstant(localZoneID);
                ZonedDateTime localZoneEnd = utcEnd.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                //convert datetime variables to strings for easier manipulation
                String localStart = localZoneStart.format(formatter);
                String localEnd = localZoneEnd.format(formatter);

                int custId=rs.getInt("Customer_ID");
                int userId=rs.getInt("User_ID");
                int contactId=rs.getInt("Contact_ID");

                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                Appointments appResult= new Appointments(appId, appName, appDesc, appLoc, appType, localStart, localEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                allAppointments.add(appResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    /**
     * Method to add a new appointment into the database
     * @param appName
     * @param appDesc
     * @param appLoc
     * @param appType
     * @param appStart
     * @param appEnd
     * @param appCust
     * @param appCont
     * @param appUserName
     * @param appUserId
     * @throws SQLException
     * @throws Exception
     */
    public static void addAppointment(String appName, String appDesc, String appLoc, String appType, ZonedDateTime appStart, ZonedDateTime appEnd, int appCust, int appCont, String appUserName, int appUserId) throws SQLException, Exception{
        try {
            String createdBy = appUserName;
            int userID = appUserId;
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

    /**
     * Method deletes an appointment out of the database
     * @param appID
     * @throws SQLException
     * @throws Exception
     */
    public static void delAppointment(int appID) throws SQLException, Exception{
        try {
            String sql = "delete from appointments where Appointment_ID = '" + appID +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to check for overlap of appointments in the database, method done here as SQL can do this easier
     * @param userID
     * @param appStart
     * @param appEnd
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static boolean appOverlap (int userID, ZonedDateTime appStart, ZonedDateTime appEnd) throws SQLException, Exception {
        boolean isOverlap = false;
        try {
            String tmpString = "SELECT * FROM appointments WHERE ('" + Timestamp.valueOf(appStart.toLocalDateTime()) + "' BETWEEN Start AND End OR '"
                    + Timestamp.valueOf(appEnd.toLocalDateTime()) + "' BETWEEN Start AND End OR '" + Timestamp.valueOf(appStart.toLocalDateTime()) + "' < Start AND '"
                     + Timestamp.valueOf(appEnd.toLocalDateTime()) + "' > End) AND (User_ID = " + userID + " )";
            PreparedStatement pst = DBConnection.getConnection().prepareStatement(tmpString);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Overlap Error\n" + e);
        }
        return isOverlap;
    }


}
