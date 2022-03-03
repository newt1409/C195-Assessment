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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

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

    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Integer> appMonths = null;
        ArrayList<String> appTypes = null;

        try {
            AppList.addAll(Objects.requireNonNull(DBAppointments.getAllAppointments()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (tabAppByTypes.isSelected()) {
            String strMonth = "";
            int count = 0;

            for (Appointments m : AppList) {
                if (!Arrays.asList(appTypes).contains(m.getAppType())) {
                    appTypes.add(m.getAppType());
                }
                if (Arrays.asList(appMonths).contains(m.getAppStart().substring(5,7))) {
                    appMonths.add(Integer.valueOf(m.getAppStart().substring(5,7)));
                }
            }
            for (int i : appMonths) {
                if (i == 1) {
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count> 0) {
                            tblAppByType.getItems().addAll("January", s, count);
                        }
                    }
                } else if (i == 2){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("February", s, count);
                        }
                    }
                } else if (i == 3){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("March", s, count);
                        }
                    }
                } else if (i == 4){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("April", s, count);
                        }
                    }
                } else if (i == 5){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("May", s, count);
                        }
                    }
                } else if (i == 6){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("June", s, count);
                        }
                    }
                } else if (i == 7){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("July", s, count);
                        }
                    }
                } else if (i == 8){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("August", s, count);
                        }
                    }
                } else if (i == 9){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("September", s, count);
                        }
                    }
                } else if (i == 10){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("October", s, count);
                        }
                    }
                } else if (i == 11){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("November", s, count);
                        }
                    }
                } else if (i == 12){
                    for (String s : appTypes) {
                        count = counter(s);
                        if (count > 0) {
                            tblAppByType.getItems().addAll("December", s, count);
                        }
                    }
                }
            }

        } else if (tabContactSchedule.isSelected()) {

        } else if (tabAppByContact.isSelected()) {

        }
    }
    private int counter (String inStr) {
        int count = 0;
        for (Appointments m : AppList) {
            if (m.getAppType().equals(inStr)) {
                count++;
            }
        }
        return count;
    }

    public void ReportsMainMenuButtonHandler(ActionEvent actionEvent) {
    }
}