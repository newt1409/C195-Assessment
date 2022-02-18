/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Controllers.MainScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class DBContacts {

    public static Contact getContactData(int conID) throws SQLException, Exception{

         try {
             String sql = "select * FROM contacts WHERE Contact_ID  = '" + conID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 int contId = rs.getInt("Contact_ID");
                 String conName = rs.getString("Contact_Name");
                 String conEmail = rs.getString("Email");
                 Contact conResult = new Contact(contId, conName, conEmail);

                 return conResult;
             }
         } catch (SQLException throwables) {
             throwables.printStackTrace();
         }
        return null;
     }

    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        ObservableList<Contact> allContacts=FXCollections.observableArrayList();
        try {
            String sql = "select * from contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contId = rs.getInt("Contact_ID");
                String conName = rs.getString("Contact_Name");
                String conEmail = rs.getString("Email");
                Contact conResult = new Contact(contId, conName, conEmail);
                allContacts.add(conResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    }

    public static void addContactData(String conName, String conEmail) throws SQLException, Exception{
        try {
            String sql = "insert into contacts (Contact_Name, Email) " +
                         "values ('" + conName + "', '" + conEmail + "')";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void modContactData(int conID, String inConName, String inEmail) throws SQLException, Exception{

        try {
            String sql = "update contacts set Contact_Name = '" + inConName + "', Email = '" + inEmail + "' WHERE Contact_ID  = '" + conID + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delContactData(int contactId) throws SQLException, Exception{
        try {
            String sql = "delete from contacts where Contact_ID = '" + contactId + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
