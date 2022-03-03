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
import javafx.scene.layout.AnchorPane;
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

public class ReportController implements Initializable {

    @FXML private Tab tabAppByTypes;
    @FXML private TableView tblAppByType;
    @FXML private TableColumn colAppByType_Month;
    @FXML private TableColumn colAppByType_Type;
    @FXML private TableColumn colAppByType_Count;
    @FXML private Tab tabContactSchedule;
    @FXML private TableView tblConApp;
    @FXML private TableColumn colConApp_Name;
    @FXML private TableColumn colConApp_Title;
    @FXML private TableColumn colConApp_Type;
    @FXML private TableColumn colConApp_Start;
    @FXML private TableColumn colConApp_End;
    @FXML private Tab tabAppByContact;
    @FXML private TableView tblAppCustomer;
    @FXML private TableColumn colAppCust_Month;
    @FXML private TableColumn colAppCust_Name;
    @FXML private TableColumn colAppCust_Phone;
    @FXML private TableColumn colAppCust_Div;
    @FXML private TableColumn colAppCust_Country;
    @FXML private TableColumn colAppCust_Count;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ReportsMainMenuButtonHandler(ActionEvent actionEvent) {
    }
}