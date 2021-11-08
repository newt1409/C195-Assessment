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
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class DBCustomers {

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

    public static void addCustomerData(String custName, String custAddress, String custPostal, String custPhone, int divId) throws SQLException, Exception{
        try {

            String createdBy = MainScreen.userLabel.getText();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //Calendar createDateCalendar = stringToCalendar(LocalDateTime.now().toString());
            //Calendar lastUpdateCalendar = stringToCalendar(LocalDateTime.now().toString());
            String sql = "insert into customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) values (" + custName + ", " + custAddress + ", " + custPostal + ", " + custPhone + ", " + LocalDateTime.now().format(formatter) + ", " + createdBy + ", " + LocalDateTime.now().format(formatter) + ", " + createdBy + ", " + divId +")";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            int rs = ps.executeUpdate();
            System.out.println(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
