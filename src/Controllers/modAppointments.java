package Controllers;

import Database.*;
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
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Repeat class of new appoontment but modified as it uses a modify ID to populate the data and save over that id
 * @author Weston Brehe
 */
public class modAppointments implements Initializable {
    /**
     * Textfield of the current appointment ID
     */
    @FXML private TextField appID;
    /**
     * Textfield of the current appointment Description
     */
    @FXML private TextField appDesc;
    /**
     * Textfield of the current appointment Title
     */
    @FXML private TextField appTitle;
    /**
     * Textfield of the current appointment Location
     */
    @FXML private TextField appLoc;
    /**
     * Textfield of the current appointment Type
     */
    @FXML private TextField appType;
    /**
     * Textfield of the current appointment Start time
     */
    @FXML private TextField appTimeStart;
    /**
     * Textfield of the current appointment Start date
     */
    @FXML private DatePicker appDateStart;
    /**
     * Textfield of the current appointment End time
     */
    @FXML private TextField appTimeStop;
    /**
     * Textfield of the current appointment End data
     */
    @FXML private DatePicker appDateStop;
    /**
     * Combo box to be filled with all the Customers
     */
    @FXML private ComboBox appCustomer;
    /**
     * Combo box to be filled with all the Contacts
     */
    @FXML private ComboBox appContact;
    /**
     * Combo box to be filled with all the Users
     */
    @FXML private ComboBox appUser;
    /**
     * Observable list of all Customers in database
     */
    @FXML private ObservableList<Customers> CustomerList = FXCollections.observableArrayList();
    /**
     * Observable list of all Contacts in database
     */
    @FXML private ObservableList<Contact> ContactList = FXCollections.observableArrayList();
    /**
     * Observable list of all Users in the database
     */
    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    /**
     * Date time format for time in the database
     */
    private final DateTimeFormatter appTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * Date time format for date in the database
     */
    private final DateTimeFormatter appDateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    /**
     * Date time format for date and time together in the database
     */
    private final DateTimeFormatter appDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Current local zone id
     */
    private final ZoneId localZoneID = ZoneId.systemDefault();
    /**
     * Current appointment id to be modified
     */
    @FXML private int modAppointmentID;

    /**
     * Initialize the frame and populate all the data from modAppointmentID and the data needed combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate appDate;
        modAppointmentID = MainScreen.getAppointmentId();
        Appointments modAppointment = null;
        try {
            modAppointment = DBAppointments.getAppointment(modAppointmentID);
            CustomerList.addAll(DBCustomers.getAllCustomers());
            ContactList.addAll(DBContacts.getAllContacts());
            UserList.addAll((DBUsers.getAllUsers()));
        } catch (Exception ex) {
            Logger.getLogger(newAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Customers c : CustomerList) {
            appCustomer.getItems().add(c.getCustomerName());
            if (c.getCustomerId() == modAppointment.getcustomerId()) {
                appCustomer.setValue(c.getCustomerName());
            }
        }
        for (Contact c : ContactList) {
            appContact.getItems().add(c.getContactName());
            if (c.getContactID() == modAppointment.getContactId()) {
                appContact.setValue(c.getContactName());
            }
        }
        for (User u : UserList) {
            appUser.getItems().add(u.getUserName());
            if (u.getUserId() == modAppointment.getUserId()) {
                appUser.setValue(u.getUserName());
            }
        }
        appID.setText(String.valueOf(modAppointment.getAppId()));
        appDesc.setText(modAppointment.getAppDesc());
        appTitle.setText(modAppointment.getAppTitle());
        appLoc.setText((modAppointment.getAppLocation()));
        appType.setText(modAppointment.getAppType());
        appTimeStart.setText(modAppointment.getAppStart().substring(11, 16));
        appTimeStop.setText(modAppointment.getAppEnd().substring(11, 16));
        appDate = LocalDate.parse(modAppointment.getAppStart().substring(0,10), formatter);
        appDateStart.setValue(appDate);
        appDate = LocalDate.parse(modAppointment.getAppEnd().substring(0,10), formatter);
        appDateStop.setValue(appDate);

    }

    /**
     * Main method to error check the input data, check overlap of other appointments, convert time to UTC,
     * check for business hours and then send curated data DBAppointment.java to be saved
     * @param actionEvent
     * @throws Exception
     */
    public void Save(ActionEvent actionEvent) throws Exception {
        try {
            String tmpDateStart = appDateStart.getValue().toString();
            String tmpDateStop = appDateStop.getValue().toString();
            if (appDesc.getText().isBlank() || appLoc.getText().isBlank() || appTitle.getText().isBlank() || appType.getText().isBlank()) {
                MainScreen.error_message("You must have a value for every text box");
            } else if (appCustomer.getValue().toString() == ""){
                MainScreen.error_message("No Customer was chosen");
            } else if (!appTimeStart.getText().isBlank() || !appTimeStop.getText().isBlank()) {
                //time craziness
                try {
                    LocalDate appLocalDateStart = appDateStart.getValue();
                    LocalDate appLocalDateEnd = appDateStop.getValue();
                    LocalTime appLocalTimeStart = LocalTime.parse(appTimeStart.getText(), appTimeFormat);
                    LocalTime appLocalTimeStop = LocalTime.parse(appTimeStop.getText(), appTimeFormat);

                    LocalTime startTime = LocalTime.of(8,0,0);
                    LocalTime endTime = startTime.plusHours(9);
                    if ( appLocalTimeStart.isAfter(startTime) && appLocalTimeStop.isBefore(endTime)) {
                        LocalDateTime appStartDateTime = LocalDateTime.of(appLocalDateStart, appLocalTimeStart);
                        LocalDateTime appStopDateTime = LocalDateTime.of(appLocalDateEnd, appLocalTimeStop);
                        ZonedDateTime appStartUTC = appStartDateTime.atZone(localZoneID).withZoneSameInstant(ZoneId.of("UTC"));
                        ZonedDateTime appStopUTC = appStopDateTime.atZone(localZoneID).withZoneSameInstant(ZoneId.of("UTC"));
                        int appCustID = 0;
                        int appContactID = 0;
                        int appUserID =0;
                        for (Customers c : CustomerList ) {
                            if (c.getCustomerName().equals(appCustomer.getValue())) {
                                appCustID = c.getCustomerId();
                            }
                        }
                        for (Contact c : ContactList ) {
                            if (c.getContactName().equals(appContact.getValue())) {
                                appContactID = c.getContactID();
                            }
                        }
                        for (User u : UserList ) {
                            if (u.getUserName().equals(appUser.getValue())) {
                                appUserID = u.getUserId();
                            }
                        }
                        if (!DBAppointments.appOverlap(appUserID, appStartUTC, appStopUTC)) {
                            DBAppointments.modAppointment(modAppointmentID, appTitle.getText(),
                                    appDesc.getText(),
                                    appLoc.getText(),
                                    appType.getText(),
                                    appStartUTC,
                                    appStopUTC,
                                    appCustID,
                                    appContactID,
                                    appUser.getValue().toString(),
                                    appUserID);
                            goBack(actionEvent);
                        } else {
                            MainScreen.error_message("This appointment overlaps with another appointment");
                        }
                    } else {
                        MainScreen.error_message("Appointment Time is outside of work hours");
                    }
                } catch (Exception e) {
                    MainScreen.error_message("Appointment Time format incorrect\n ex: HH:MM:SS");
                }
            } else {
            }
        } catch (Exception e) {
            MainScreen.error_message("No Start/Stop date was entered");
        }
    }

    /**
     * Simple go back to previous frame
     * @param actionEvent
     * @throws IOException
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 875, 460);
        stage.setTitle("User Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
