/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class DBUsers {

    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers=FXCollections.observableArrayList();    
        //DBConnection.openConnection();
        try {
            String sql = "select * from users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int userid=rs.getInt("User_ID");
                String userNameG=rs.getString("User_Name");
                String password=rs.getString("Password");
                //int active=result.getInt("active");
                //if(active==1) act=true;
                String createDate=rs.getString("Create_Date");
                String createdBy=rs.getString("Created_By");
                String lastUpdate=rs.getString("Last_Update");
                String lastUpdateby=rs.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                User userResult= new User(userid, userNameG, password, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allUsers.add(userResult);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //DBConnection.closeConnection();
        return allUsers;
    } 
}
