package Controllers;

import Database.DBAppointments;
import Database.DBCustomers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addAppointmentsController implements Initializable {

    @FXML private TextField appID;
    @FXML private TextField appDesc;
    @FXML private TextField appTitle;
    @FXML private TextField appLoc;
    @FXML private TextField appType;
    @FXML private TextField appTimeStart;
    @FXML private DatePicker appDateStart;
    @FXML private TextField appTimeStop;
    @FXML private DatePicker appDateStop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void Save(ActionEvent actionEvent) throws Exception {
        try {
            String tmpDateStart = appDateStart.getValue().toString();
            String tmpDateStop = appDateStop.getValue().toString();
            if (appDesc.getText().isBlank() || appLoc.getText().isBlank() || appTitle.getText().isBlank() || appType.getText().isBlank()) {
                MainScreen.error_message("You must have a value for every text box");
            } else if (!appTimeStart.getText().isBlank() || !appTimeStop.getText().isBlank()) {
                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String appStartDateTime = appDateStart.getValue().toString() + " " + appTimeStart.getText().toString() + ":00";
                String appStopDateTime = appDateStop.getValue().toString() +  " " + appTimeStop.getText().toString() + ":00";
                //LocalDateTime appStartTime = LocalDateTime.parse(appStartDateTime, formatter);
                //LocalDateTime appStopTime = LocalDateTime.parse(appStopDateTime, formatter);
                //DBAppointments.addAppointment(appTitle.getText(), appDesc.getText(), appLoc.getText(), appType.getText(), appStart.getText(), appStop.getText() );
            } else {
            }
        } catch (Exception e) {
            MainScreen.error_message("No Start/Stop date was entered");
        }



        goBack(actionEvent);
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