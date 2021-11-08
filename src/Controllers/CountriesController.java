package Controllers;

import Database.DBCountries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Countries;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CountriesController implements Initializable {

    @FXML
    private TableView<Countries> CountryTable;
    @FXML
    private TableColumn<Countries, Integer> ID;
    @FXML
    private TableColumn<Countries, String> CountryName;

    //private TableColumn<?, ?> Password;

    ObservableList<Countries> CountryList= FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ID.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        // CustomerName.setCellValueFactory(new PropertyValueFactory<>("address"));
        CountryName.setCellValueFactory(new PropertyValueFactory<>("countryName"));
//       CustomerAddress2.setCellValueFactory(new PropertyValueFactory<>("customerAddress2"));
        //Password.setCellValueFactory(new PropertyValueFactory<>("password"));


        try {
            CountryList.addAll(DBCountries.getAllCountries());


        } catch (Exception ex) {
            Logger.getLogger(addCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        CountryTable.setItems(CountryList);
        //Using Lambda for efficient selection off a tableview

    }
}
