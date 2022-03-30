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
/**
 * Login Controller for all login actions
 * @author Weston Brehe
 */
public class Login implements Initializable {

    /**
     * button to initialize the username and password check
     */
    @FXML private Button btnLogin;
    /**
     * label for the title of the frame
     */
    @FXML private Label lblTitle;
    /**
     * label for what zone the time frame is
     */
    @FXML private Label lblZone;
    /**
     * text field for the username
     */
    @FXML private TextField txtUsername;
    /**
     * text field for the password
     */
    @FXML private PasswordField txtPassword;
    /**
     * authenticated user
     */
    @FXML private static User validatedUser;
    /**
     * Observable list of Users to authenticate with
     */
    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    /**
     * Observable list of appointments in the database
     */
    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    /**
     * Date time formatter for the time format in the database
     */
    @FXML private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    /**
     * Current zoneid for the local machine
     */
    @FXML private static ZoneId localZoneID = ZoneId.systemDefault();

    /**
     * Used by multiple classes for the currently logged-in user
     * @return
     */
    public static User getValidUser() { return validatedUser;}
    ResourceBundle rssBundle;

    /**
     * Initialize the frame and if convert to french if the local bundle is selected
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Main method for the Login class, as it checks if the user input is correct
     * @param actionEvent login button
     * @throws Exception
     */
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
                    checkAppointments(); //determine if an upcoming appointment is within 15min
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
    /**
     * Check appointments that begin within 15min and alert if there is one or if there isnt any
     *
     * LAMBDA expression used to identify any appointment starting within the next 15 minutes.  This makes time
     * calculations easier and more efficient.
     *
     */
    private void checkAppointments() throws Exception {
        try {
            //pull all neccessary data to determine if an appointment is within 15min
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
        //create temp variables for 15min from local time
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appPlus15 = now.plusMinutes(15);

        //using lambda to quickly find if an appointment exists within 15
        FilteredList<Appointments> filteredData = new FilteredList<>(AppList);
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getAppStart().substring(0, 16), formatter);
            return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(appPlus15);
        });
        //if filterdata has not empty then an appointment was found
        if (! filteredData.isEmpty()) {
            error_message("Reminder - You have an appointment starting within the next 15 min \n DONT BE LATE!\n\tAppointment ID: " + filteredData.get(0).getAppId() +
                                "\n\tAppointment Date: " + filteredData.get(0).getAppStart().substring(0, 10) + "\n\tAppointment Time: " + filteredData.get(0).getAppStart().substring(11,16));
        } else {
            error_message("You have no coming up appointments within 15 minutes");
        }
    }

    /**
     * Simple errror message to be used as needed from class
     * @param inMsg
     */
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
