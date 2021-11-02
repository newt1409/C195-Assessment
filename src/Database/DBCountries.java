package Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static utilities.TimeFiles.stringToCalendar;

public class DBCountries {


    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> clist = FXCollections.observableArrayList();

        try{
            String sql = "select * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                String createDate=rs.getString("Create_Date");
                String createdBy=rs.getString("Created_By");
                String lastUpdate=rs.getString("Last_Update");
                String lastUpdateby=rs.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Countries C = new Countries(countryId, countryName, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby);
                clist.add(C);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }

}
