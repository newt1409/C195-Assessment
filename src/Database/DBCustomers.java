/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Controllers.MainScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;
/**
 * Database interface for customer methods
 *@author Weston Brehe
 */
public class DBCustomers {
    /**
     * Method to return a singular customer data
     * @param custID
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static Customers getCustomerData(int custID) throws SQLException, Exception{
         try {
             String sql = "select * FROM customers WHERE Customer_ID  = '" + custID + "'";
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) {
                 int custId = rs.getInt("Customer_ID");
                 String custName = rs.getString("Customer_Name");
                 String custAddress = rs.getString("Address");
                 String custPostal = rs.getString("Postal_Code");
                 String custPhone = rs.getString("Phone");
                 String createDate = rs.getString("Create_Date");
                 String createdBy = rs.getString("Created_By");
                 String lastUpdate = rs.getString("Last_Update");
                 String lastUpdateby = rs.getString("Last_Updated_By");
                 Calendar createDateCalendar = stringToCalendar(createDate);
                 Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                 int divId = rs.getInt("Division_ID");
                 Customers custResult = new Customers(custId, custName, custAddress, custPostal, custPhone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, divId);
                 return custResult;
             }
         } catch (SQLException | ParseException throwables) {
             throwables.printStackTrace();
         }
        return null;
     }

    /**
     * Method to return all customer data
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Customers> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customers> allCustomers=FXCollections.observableArrayList();
        try {
            String sql = "select * from customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int custId = rs.getInt("Customer_ID");
                String custName = rs.getString("Customer_Name");
                String custAddress = rs.getString("Address");
                String custPostal = rs.getString("Postal_Code");
                String custPhone = rs.getString("Phone");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdateby = rs.getString("Last_Updated_By");
                Calendar createDateCalendar = stringToCalendar(createDate);
                Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);
                int divId = rs.getInt("Division_ID");
                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                Customers custResult = new Customers(custId, custName, custAddress, custPostal, custPhone, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, divId);
                allCustomers.add(custResult);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Method to add a new customer to the database
     * @param custName
     * @param custAddress
     * @param custPostal
     * @param custPhone
     * @param divId
     * @throws SQLException
     * @throws Exception
     */
    public static void addCustomerData(String custName, String custAddress, String custPostal, String custPhone, int divId) throws SQLException, Exception{
        try {
            String createdBy = MainScreen.validUser.getUserName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //Calendar createDateCalendar = stringToCalendar(LocalDateTime.now().toString());
            //Calendar lastUpdateCalendar = stringToCalendar(LocalDateTime.now().toString());
            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, " + "Last_Updated_By, Division_ID) " +
                         "values ('" + custName + "', '" + custAddress + "', '" + custPostal + "', '" + custPhone + "', '" + LocalDateTime.now().format(formatter) + "', '" + createdBy + "', '" + LocalDateTime.now().format(formatter) + "', '" + createdBy + "', " + divId +")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to modify the customer data of a given customer ID
     * @param custId
     * @param custName
     * @param custAddress
     * @param custPostal
     * @param custPhone
     * @param divId
     * @throws SQLException
     * @throws Exception
     */
    public static void modCustomerData(int custId, String custName, String custAddress, String custPostal, String custPhone, int divId) throws SQLException, Exception{
        try {
            String modifiedBy = MainScreen.validUser.getUserName();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //Calendar createDateCalendar = stringToCalendar(LocalDateTime.now().toString());
            //Calendar lastUpdateCalendar = stringToCalendar(LocalDateTime.now().toString());
            String sql = "update customers set Customer_Name = '" + custName + "', Address = '" + custAddress + "', Postal_Code = '" +
                          custPostal + "', Phone = '" + custPhone + "', Last_Update = '" + LocalDateTime.now().format(formatter) + "', Last_Updated_By = '" +
                          modifiedBy + "' where Customer_ID = " + custId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method to delete a customer from the database
     * @param custId
     * @throws SQLException
     * @throws Exception
     */
    public static void delCustomerData(int custId) throws SQLException, Exception{
        try {
            String sql = "delete from customers where Customer_ID = '" + custId +"'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
