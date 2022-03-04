package Controllers;

import Database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    //@FXML private ObservableList<Customers> CustomerList = FXCollections.observableArrayList();
    //@FXML private ObservableList<Divisions> DivList = FXCollections.observableArrayList();
    //@FXML private ObservableList<Countries> CountryList = FXCollections.observableArrayList();
    @FXML private ObservableList<Reports> rptAppByType = FXCollections.observableArrayList();
    @FXML private ObservableList<Reports> rptAppByContact = FXCollections.observableArrayList();
    @FXML private ObservableList<Reports> rptAppByCustomer = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAppByType_Month.setCellValueFactory(new PropertyValueFactory<>("rptAppByType_Months"));
        colAppByType_Type.setCellValueFactory(new PropertyValueFactory<>("rptAppByType_Type"));
        colAppByType_Count.setCellValueFactory(new PropertyValueFactory<>("rptAppByType_Count"));

        colConApp_Name.setCellValueFactory(new PropertyValueFactory<>("RptContactSchedule_Contact"));
        colConApp_Title.setCellValueFactory(new PropertyValueFactory<>("RptContactSchedule_Title"));
        colConApp_Type.setCellValueFactory(new PropertyValueFactory<>("RptContactSchedule_Type"));
        colConApp_Start.setCellValueFactory(new PropertyValueFactory<>("RptContactSchedule_Start"));
        colConApp_End.setCellValueFactory(new PropertyValueFactory<>("RptContactSchedule_End"));

        colAppCust_Month.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Month"));
        colAppCust_Name.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Name"));
        colAppCust_Phone.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Phone"));
        colAppCust_Div.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Div"));
        colAppCust_Country.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Country"));
        colAppCust_Count.setCellValueFactory(new PropertyValueFactory<>("RptAppByCustomer_Count"));


        try {
            AppList.addAll(Objects.requireNonNull(DBAppointments.getAllAppointments()));
            //CustomerList.addAll(DBCustomers.getAllCustomers());
            //DivList.addAll(DBDivisions.getAllDivisions());
            //CountryList.addAll(DBCountries.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }
        rptAppTypeByMonth();
        rptContactSchedule();
        try {
            rptAppCustByMonth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int counter (String inStr, int inMonth) {
        //Counter for Types in Appointments
        int count = 0;
        for (Appointments m : AppList) {
            if (m.getAppType().equals(inStr) && Integer.valueOf(m.getAppStart().substring(5,7)).equals(inMonth)) {
                count++;
            }
        }
        return count;
    }

    private int counter2 (int inCustID, int inMonth) {
        //Counter for Customers in Appointments
        int count = 0;
        for (Appointments m : AppList) {
            if (m.getcustomerId() == inCustID && Integer.valueOf(m.getAppStart().substring(5,7)).equals(inMonth)) {
                count++;
            }
        }
        return count;
    }

    public void ReportsMainMenuButtonHandler(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 875, 460);
        stage.setTitle("User Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void rptAppTypeByMonth() {
        ArrayList<Integer> appMonths = new ArrayList<>();
        ArrayList<String> appTypes = new ArrayList<>();

        String strMonth = "";
        int count = 0;

        for (Appointments m : AppList) {
            if (! appTypes.contains(m.getAppType())) {
                appTypes.add(m.getAppType());
            }
            if (! appMonths.contains(Integer.valueOf(m.getAppStart().substring(5,7)))) {
                appMonths.add(Integer.valueOf(m.getAppStart().substring(5,7)));
            }
        }
        for (int i : appMonths) {
            if (i == 1) {
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count> 0) {
                        Reports tmpReport = new Reports("January", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 2){
                for (String s : appTypes) {
                    count = counter(s, i);;
                    if (count > 0) {
                        Reports tmpReport = new Reports("February", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 3){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("March", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 4){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("April", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 5){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("May", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 6){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("June", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 7){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("July", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 8){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("August", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 9){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("September", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 10){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("October", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 11){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("November", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 12){
                for (String s : appTypes) {
                    count = counter(s, i);
                    if (count > 0) {
                        Reports tmpReport = new Reports("December", s, count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            }
        }
        //populate the tableview with the reporting
        tblAppByType.setItems(rptAppByType);
    }

    public void rptContactSchedule() {
        Contact tmpContact = null;
        for (Appointments m : AppList) {
            try {
                tmpContact = DBContacts.getContactData(m.getContactId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Reports tmpReport = new Reports(tmpContact.getContactName(), m.getAppTitle(), m.getAppType(), m.getAppStart(), m.getAppEnd());
            rptAppByContact.addAll(tmpReport);
        }
        tblConApp.setItems(rptAppByContact);
        tblConApp.getSortOrder().add(colConApp_Start);
    }

    public void rptAppCustByMonth() throws Exception {
        ArrayList<Integer> appMonths = new ArrayList<>();
        ArrayList<Integer> appCustomers = new ArrayList<>();
        int count = 0;


        for (Appointments m : AppList) {
            if (! appCustomers.contains(m.getcustomerId())) {
                appCustomers.add(m.getcustomerId());
            }
            if (! appMonths.contains(Integer.valueOf(m.getAppStart().substring(5,7)))) {
                appMonths.add(Integer.valueOf(m.getAppStart().substring(5,7)));
            }
        }
        for (int i : appMonths) {
            if (i == 1) {
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("January", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 2){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("February", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 3){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("March", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 4){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("April", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 5){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("May", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 6){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("June", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 7){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("July", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByType.addAll(tmpReport);
                    }
                }
            } else if (i == 8){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("August", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 9){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("September", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 10){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("October", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 11){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("November", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            } else if (i == 12){
                for (Integer c : appCustomers) {
                    count = counter2(c, i);
                    if (count> 0) {
                        Customers tmpCustomer = DBCustomers.getCustomerData(c);
                        Divisions tmpDiv = DBDivisions.getDivisionData(tmpCustomer.getdivId());
                        Countries tmpCountry = DBCountries.getCountryData(tmpDiv.getCountryId());
                        Reports tmpReport = new Reports("December", tmpCustomer.getCustomerName() , tmpCustomer.getCustomerPhone(),
                                tmpDiv.getDivName(), tmpCountry.getCountryName(), count);
                        rptAppByCustomer.addAll(tmpReport);
                    }
                }
            }
        }
        //populate the tableview with the reporting
        tblAppCustomer.setItems(rptAppByCustomer);

    }
}