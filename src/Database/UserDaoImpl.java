/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utilities.TimeFiles.stringToCalendar;

/**
 *
 * @author carolyn.sher
 */
/* typically you would also have create, update and read methods*/
public class UserDaoImpl {
    static boolean act;
     public static User getUser(String userName) throws SQLException, Exception{
        // type is name or phone, value is the name or the phone #
        DBConnection.openConnection();
        String sqlStatement="select * FROM user WHERE userName  = '" + userName+ "'";
         //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
           User userResult;
           ResultSet result=Query.getResult();
           while(result.next()){
                int userid=result.getInt("userid");
                String userNameG=result.getString("userName");
                String password=result.getString("password");
                int active=result.getInt("active");
                if(active==1) act=true;
                String createDate=result.getString("createDate");
                String createdBy=result.getString("createBy");
                String lastUpdate=result.getString("lastUpdate");
                String lastUpdateby=result.getString("lastUpdatedBy");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
             //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                userResult= new User(userid, userName, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                return userResult;
           }
             DBConnection.closeConnection();
        return null;
    }
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers=FXCollections.observableArrayList();    
        DBConnection.openConnection();
            String sqlStatement="select * from users";
            Query.makeQuery(sqlStatement);
            ResultSet result=Query.getResult();
             while(result.next()){
                int userid=result.getInt("User_ID");
                String userNameG=result.getString("User_Name");
                String password=result.getString("Password");
                //int active=result.getInt("active");
                //if(active==1) act=true;
                String createDate=result.getString("Create_Date");
                String createdBy=result.getString("Created_By");
                String lastUpdate=result.getString("Last_Update");
                String lastUpdateby=result.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
             //   s(int addressId, String address, String address2, int cityId, String postalCode, String phone, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy)
                User userResult= new User(userid, userNameG, password, act, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                allUsers.add(userResult);
                
            }
             DBConnection.closeConnection();
        return allUsers;
    } 
}
