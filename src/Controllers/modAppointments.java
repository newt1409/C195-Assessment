package Controllers;

import Database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class modAppointments implements Initializable {



    @FXML private TextField appID;
    @FXML private TextField appDesc;
    @FXML private TextField appTitle;
    @FXML private TextField appLoc;
    @FXML private TextField appType;
    @FXML private TextField appTimeStart;
    @FXML private DatePicker appDateStart;
    @FXML private TextField appTimeStop;
    @FXML private DatePicker appDateStop;

    @FXML private ComboBox appCustomer;
    @FXML private ComboBox appContact;
    @FXML private ObservableList<Customers> CustomerList = FXCollections.observableArrayList();
    @FXML private ObservableList<Contact> ContactList = FXCollections.observableArrayList();

    private final DateTimeFormatter appTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final DateTimeFormatter appDateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd");
    private final DateTimeFormatter appDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ZoneId localZoneID = ZoneId.systemDefault();
    @FXML private int modAppointmentID;

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
        appID.setText(String.valueOf(modAppointment.getAppId()));
        appDesc.setText(modAppointment.getAppDesc());
        appTitle.setText(modAppointment.getAppTitle());
        appLoc.setText((modAppointment.getAppLocation()));
        appType.setText(modAppointment.getAppTitle());
        appTimeStart.setText(modAppointment.getAppStart().substring(11, 16));
        appTimeStop.setText(modAppointment.getAppEnd().substring(11, 16));

        appDate = LocalDate.parse(modAppointment.getAppStart().substring(0,10), formatter);
        appDateStart.setValue(appDate);
        appDate = LocalDate.parse(modAppointment.getAppEnd().substring(0,10), formatter);
        appDateStop.setValue(appDate);
    }

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
                    LocalDateTime appStartDateTime = LocalDateTime.of(appLocalDateStart, appLocalTimeStart);
                    LocalDateTime appStopDateTime = LocalDateTime.of(appLocalDateEnd, appLocalTimeStop);
                    ZonedDateTime appStartUTC = appStartDateTime.atZone(localZoneID).withZoneSameInstant(ZoneId.of("UTC"));
                    ZonedDateTime appStopUTC = appStopDateTime.atZone(localZoneID).withZoneSameInstant(ZoneId.of("UTC"));

                    //String appStartDateTime = appDateStart.getValue().toString() + " " + appTimeStart.getText().toString() + ":00";
                    //String appStopDateTime = appDateStop.getValue().toString() +  " " + appTimeStop.getText().toString() + ":00";

                    int appCustID = 0;
                    int appContactID = 0;
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
                    DBAppointments.modAppointment(Integer.valueOf(appID.getText()), appTitle.getText(),
                            appDesc.getText(),
                            appLoc.getText(),
                            appType.getText(),
                            appStartUTC,
                            appStopUTC,
                            appCustID,
                            appContactID);
                    goBack(actionEvent);
                } catch (Exception e) {
                    MainScreen.error_message("Appointment Time format incorrect\n ex: HH:MM:SS");
                }
            } else {
            }
        } catch (Exception e) {
            MainScreen.error_message("No Start/Stop date was entered");
        }




    }

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
