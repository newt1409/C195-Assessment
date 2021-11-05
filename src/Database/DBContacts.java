/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Customers;

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
            String sql = "select * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int contId = rs.getInt("Customer_ID");
                String conName = rs.getString("Customer_Name");
                String conEmail = rs.getString("Address");
                Contact conResult = new Contact(contId, conName, conEmail);
                allContacts.add(conResult);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allContacts;
    } 
}
