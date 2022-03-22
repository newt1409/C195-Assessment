package Controllers;

import Database.DBAppointments;
import Database.DBConnection;
import Database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utilities.TimeFiles.stringToCalendar;

public class Login implements Initializable {

    @FXML private Button btnLogin;
    @FXML private Label lblTitle;
    @FXML private Label lblZone;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private static User validatedUser;
    @FXML
    private ObservableList<User> UserList = FXCollections.observableArrayList();

    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static ZoneId localZoneID = ZoneId.systemDefault();

    public static User getValidUser() { return validatedUser;}
    ResourceBundle rssBundle;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        rssBundle = ResourceBundle.getBundle("Properties.Login", Locale.getDefault());
        lblTitle.setText(rssBundle.getString("title"));
        txtUsername.setPromptText(rssBundle.getString("username"));
        txtPassword.setPromptText(rssBundle.getString("password"));
        btnLogin.setText(rssBundle.getString("login"));
        lblZone.setText(localZoneID.toString());

        try {
            UserList.addAll(DBUsers.getAllUsers());
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Login(ActionEvent actionEvent) throws Exception {
        boolean validUser = false;
        //write to file if the username supplied is a successful or unsuccesful
        String loginLog = "login_activity.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(loginLog, true));
        //cycle through the database of users and check the password
        for (User u : UserList ) {
            if (u.getUserName().equals(txtUsername.getText())) {
                if (u.getPassword().equals(txtPassword.getText())) {
                    validatedUser = u;
                    //write to log of successful login
                    writer.append(LocalDateTime.now() + " " + validatedUser.getUserName() + " Successful Login" + "\n");
                    writer.flush();
                    writer.close();
                    checkAppointments(); //Cant figure out why the lamba blows up with time added
                    Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 500);
                    stage.setTitle("Main Interface");
                    stage.setScene(scene);
                    stage.show();
                    validUser = true;
                } else {
                    //write to log of unsuccessful login
                    writer.append(LocalDateTime.now() + " " + u.getUserName() + " Unsuccessful Login" + "\n");
                    writer.flush();
                    writer.close();
                    break;
                }
            }
        }
        if (!validUser){
            //alert catch for any issue with login
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rssBundle.getString("error"));
            alert.setHeaderText(rssBundle.getString("error"));
            alert.setContentText(rssBundle.getString("incorrect"));
            alert.showAndWait();
        }
    }

    private void checkAppointments() throws Exception {
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(
                    "SELECT appointments.Appointment_ID, appointments.Customer_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, "
                            + "appointments.`Start`, appointments.`End`, customers.Customer_ID, customers.Customer_Name, appointments.Created_By "
                            + "FROM appointments, customers "
                            + "WHERE appointments.Customer_ID = customers.Customer_ID AND appointments.Created_By = ? "
                            + "ORDER BY `start`");
            ps.setString(1, validatedUser.getUserName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //pulls start time from database and converts it into local time zone

                Timestamp timestampStart = Timestamp.valueOf(rs.getString("Start"));
                ZonedDateTime startUTC = timestampStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalStart = startUTC.withZoneSameInstant(localZoneID);
                Timestamp outStart = Timestamp.valueOf(newLocalStart.toLocalDateTime());


                //pulls end time from database and converts it into local time zone
                Timestamp timestampEnd = Timestamp.valueOf(rs.getString("End"));
                ZonedDateTime endUTC = timestampEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
                ZonedDateTime newLocalEnd = endUTC.withZoneSameInstant(localZoneID);
                Timestamp outEnd = Timestamp.valueOf(newLocalEnd.toLocalDateTime());


                //grab the rest of the data to create an instance of an appointment
                int appID = Integer.valueOf(rs.getString("Appointment_ID"));
                String appName = rs.getString("Title");
                String appDesc = rs.getString("Description");
                String appLoc = rs.getString("Location");
                String appType = rs.getString("Type");
                String customerName = rs.getString("Customer_Name");

                AppList.add(new Appointments(appID, appName, appDesc, appLoc, appType, outStart.toString(), outEnd.toString(), customerName));
            }
        } catch (SQLException e) {
            System.out.println("There is an error from SQL server");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appPlus15 = now.plusMinutes(15);
        FilteredList<Appointments> filteredData = new FilteredList<>(AppList);
        //lambda expression used to efficiently identify any appointment starting within the next 15 minutes
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getAppStart().substring(0, 16), formatter);
            return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(appPlus15);
        });
        if (! filteredData.isEmpty()) {
            error_message("Reminder - You have an appointment starting within the next 15 min \n DONT BE LATE!\n\tAppointment ID: " + filteredData.get(0).getAppId() +
                                "\n\tAppointment Date: " + filteredData.get(0).getAppStart().substring(0, 10) + "\n\tAppointment Time: " + filteredData.get(0).getAppStart().substring(11,16));
        } else {
            error_message("You have no coming up appointments within 15 minutes");
        }


    }

    public static void error_message (String inMsg) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(inMsg);
        alert.setContentText("click ok to return");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
