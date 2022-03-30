package Controllers;
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBDivisions;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
/**
 * Repeat class of new customer but modified as it uses a modify ID to populate the data and save over that id
 * @author Weston Brehe
 */
public class modCustomer implements Initializable {
    /**
     * Combobox of the Customer states or provinces
     */
    @FXML private ComboBox customerDiv;
    /**
     * Combobox of the Customer Country
     */
    @FXML private ComboBox customerCountry;
    /**
     * Textfield of the customer Name
     */
    @FXML private TextField customerName;
    /**
     * Textfield of the customer Address
     */
    @FXML private TextField customerAddress;
    /**
     * Textfield of the customer Postal code
     */
    @FXML private TextField customerPostal;
    /**
     * Textfield of the customer Phone number
     */
    @FXML private TextField customerPhone;
    /**
     * Observable list of all the countries
     */
    @FXML private ObservableList<Countries> CountryList = FXCollections.observableArrayList();
    /**
     * Observable list of all the states and provinces
     */
    @FXML private ObservableList<Divisions> DivisionLIst = FXCollections.observableArrayList();
    /**
     * Customer ID that is to be modified
     */
    @FXML private TextField customerID;
    /**
     * Customer data of the ID that will be modified
     */
    @FXML private Customers modCustomer;
    /**
     * Customer id from MainScreen for passing data between classes
     */
    @FXML private static int modCustomerId;

    /**
     * Initialize all the combo boxes and observable lists.  Then populate all the data of the customer to be modified
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modCustomerId = MainScreen.getModCustomerId();
        try {
            CountryList.addAll(DBCountries.getAllCountries());
            DivisionLIst.addAll(DBDivisions.getAllDivisions());
            modCustomer = DBCustomers.getCustomerData(modCustomerId);
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Countries c : CountryList) {
            customerCountry.getItems().add(c.getCountryName());
        }
        customerID.setText(String.valueOf(modCustomer.getCustomerId()));
        customerName.setText(modCustomer.getCustomerName());
        customerAddress.setText((modCustomer.getCustomerAddress()));
        customerPostal.setText(modCustomer.getCustomerPostal());
        customerPhone.setText(modCustomer.getCustomerPhone());
        customerCountry.setValue(modCustomer.getCustomerCountry());
        customerDiv.setValue(modCustomer.getCustomerDivision());
    }

    /**
     * Save the modified data to the customer id
     * @param actionEvent
     * @throws Exception
     */
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

    /**
     * nested for loops to populate the combo boxes
     * @param actionEvent
     * @throws Exception
     */
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
