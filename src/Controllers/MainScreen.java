package Controllers;

import Database.*;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen implements Initializable {

    @FXML public TableView<Appointments> appTable = new TableView<>();
    @FXML public TableView<Customers> custTable = new TableView<>();
    @FXML public Label userLabel = new Label("");
    @FXML public static User validUser;

    @FXML private TableColumn<Appointments, Integer> ID;
    @FXML private TableColumn<Appointments, String> Title;
    @FXML private TableColumn<Appointments, String> Description;
    @FXML private TableColumn<Appointments, String> Location;
    @FXML private TableColumn<Appointments, String> Type;
    @FXML private TableColumn<Appointments, String> Start;
    @FXML private TableColumn<Appointments, String> End;

    @FXML private TableColumn<Customers, Integer> custID;
    @FXML private TableColumn<Customers, String> custName;
    @FXML private TableColumn<Customers, String> custAddress;
    @FXML private TableColumn<Customers, String> custPostal;
    @FXML private TableColumn<Customers, String> custPhone;
    @FXML private TableColumn<Customers, String> custDiv;
    @FXML private TableColumn<Customers, String> custCountry;

    @FXML private TextField contactName;
    @FXML private TextField contactEmail;


    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    @FXML private Customers appCustomer;
    @FXML private Divisions appDiv;
    @FXML private Countries appCountry;



    @FXML private Contact appContact;




    public void Users(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            UserList.addAll(DBUsers.getAllUsers());
            validUser = LoginController.getValidUser();
            AppList.addAll(DBAppointments.getUserAppointments(validUser.getUserId()));
        } catch (Exception ex) {
            Logger.getLogger(addCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(LoginController.getValidUser().getUserName());
        userLabel.setText(validUser.getUserName());
        //AppList.clear();
        appTable.setItems(AppList);
        ID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
        appTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                try {
                    popAppData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void popAppData() throws Exception {
        if (appTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Integer custInt = appTable.getSelectionModel().getSelectedItem().getcustomerId();
                //Integer divInt = DBCustomers.getCustomerData(custInt).getdivId();
                //Integer countryInt = DBDivisions.getDivisionData(divInt).getCountryId();
                appCustomer = DBCustomers.getCustomerData(custInt);
                //appDiv = DBDivisions.getDivisionData(divInt);
                //appCountry = DBCountries.getCountryData(countryInt);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            //Wrapper rapper = new Wrapper(appCustomer, appDiv, appCountry);
            //ObservableList tableWrapper = null;
            //tableWrapper.setAll(rapper);
            custTable.getItems().add(appCustomer);
            //custTable.getItems().add(DBDivisions.getDivisionData(DBCustomers.getCustomerData(appTable.getSelectionModel().getSelectedItem().getcustomerId())).getDivId())

            custID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerId"));
            custName.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
            custAddress.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerAddress"));
            custPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerPhone"));
            custPostal.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerPostal"));
            custDiv.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerDivision"));
            custCountry.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerCountry"));










            contactName.setText(String.valueOf(DBContacts.getContactData(appTable.getSelectionModel().getSelectedItem().getcustomerId()).getContactName()));
            contactEmail.setText(String.valueOf(DBContacts.getContactData(appTable.getSelectionModel().getSelectedItem().getcustomerId()).getContactEmail()));

/*
            appCustomer = DBCustomers.getCustomerData(selectedCustomer);
            appContact = DBContacts.getContactData(selectedContact);
            appDiv = DBDivisions.getDivisionData(appCustomer.getdivId());
            appCountry = DBCountries.getCountryData(appDiv.getCountryId());


            customerName.setText(String.valueOf(appCustomer.getCustomerName()));
            customerAddress.setText(String.valueOf(appCustomer.getCustomerAddress()));
            customerPostal.setText(String.valueOf(appCustomer.getCustomerPostal()));
            customerPhone.setText(String.valueOf(appCustomer.getCustomerPhone()));
            customerDiv.setText(String.valueOf(appDiv.getDivName()));
            customerCountry.setText(String.valueOf(appCountry.getCountryName()));
*/

            }

    }


    public void Countries(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Appointments.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }


    public void addCustomer(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 270, 320);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
}
