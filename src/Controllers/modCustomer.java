/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Database.DBCountries;
import Database.DBCustomers;
import Database.DBDivisions;
import Database.DBUsers;
import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.Divisions;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author carolyn.sher
 */
public class modCustomer implements Initializable {

    @FXML private ComboBox customerDiv;
    @FXML private ComboBox customerCountry;
    @FXML private TextField customerName;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPostal;
    @FXML private TextField customerPhone;
    @FXML private ObservableList<Countries> CountryList = FXCollections.observableArrayList();
    @FXML private ObservableList<Divisions> DivisionLIst = FXCollections.observableArrayList();
    @FXML private Customers modCustomer;

    @FXML private static int modCustomerId = MainScreen.getModCustomerId();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CountryList.addAll(DBCountries.getAllCountries());
            DivisionLIst.addAll(DBDivisions.getAllDivisions());
            modCustomer = DBCustomers.getCustomerData(modCustomerId);
        } catch (Exception ex) {
            Logger.getLogger(addCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Countries c : CountryList) {
            customerCountry.getItems().add(c.getCountryName());
        }
        customerName.setText(modCustomer.getCustomerName());
        customerAddress.setText((modCustomer.getCustomerAddress()));
        customerPostal.setText(modCustomer.getCustomerPostal());
        customerPhone.setText(modCustomer.getCustomerPhone());
        customerCountry.setValue(modCustomer.getCustomerCountry());
        customerDiv.setValue(modCustomer.getCustomerDivision());
    }
    public void Save(ActionEvent actionEvent) throws Exception {
        int custDiv = 0;
        for (Divisions d : DivisionLIst) {
            if (d.getDivName().equals(customerDiv.getValue())) {
                custDiv = d.getDivId();
            }
        }
        DBCustomers.modCustomerData(modCustomerId, customerName.getText(), customerAddress.getText(), customerPostal.getText(), customerPhone.getText(), custDiv);
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

    public void popDiv(ActionEvent actionEvent) throws Exception {
        customerDiv.getItems().clear();
        for (Countries c : CountryList) {
            if (customerCountry.getValue().equals(c.getCountryName())) {
                for (Divisions d : DivisionLIst) {
                    if (c.getCountryId() == d.getCountryId()) {
                        customerDiv.getItems().add(d.getDivName());
                    }
                }
            }

        }

    }
}
