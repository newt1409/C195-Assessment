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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class newAppointments implements Initializable {



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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CustomerList.addAll(DBCustomers.getAllCustomers());
            ContactList.addAll(DBContacts.getAllContacts());
        } catch (Exception ex) {
            Logger.getLogger(newAppointments.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Customers c : CustomerList) {
            appCustomer.getItems().add(c.getCustomerName());
        }
        for (Contact c : ContactList) {
            appContact.getItems().add(c.getContactName());
        }
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
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String appStartDateTime = appDateStart.getValue().toString() + " " + appTimeStart.getText().toString() + ":00";
                String appStopDateTime = appDateStop.getValue().toString() +  " " + appTimeStop.getText().toString() + ":00";
                //LocalDateTime appStartTime = LocalDateTime.parse(appStartDateTime, formatter);
                //LocalDateTime appStopTime = LocalDateTime.parse(appStopDateTime, formatter);
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
                DBAppointments.addAppointment(appTitle.getText(),
                        appDesc.getText(),
                        appLoc.getText(),
                        appType.getText(),
                        appStartDateTime,
                        appStopDateTime,
                        appCustID,
                        appContactID);
                goBack(actionEvent);
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
