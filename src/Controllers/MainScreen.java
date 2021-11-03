package Controllers;

import Database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen implements Initializable {

    public ChoiceBox selectUser;
    public TableView appTable;
    public TableColumn<Appointments, Integer> appId;
    public TableColumn<Appointments, String> appTitle;
    public TableColumn<Appointments, String> appDesc;
    public TableColumn<Appointments, String> appLoc;
    public TableColumn<Appointments, String> apptype;
    public TableColumn<Appointments, String> appStart;
    public TableColumn<Appointments, String> appEnd;

    public TextField customerId;
    public TextField customerAddress;
    public TextField customerPostal;
    public TextField customerPhone;
    public TextField customerDiv;
    public TextField customerCountry;

    public TextField contactId;
    public TextField contactEmail;


    ObservableList<User> UserList= FXCollections.observableArrayList();
    ObservableList<Appointments> AppList= FXCollections.observableArrayList();
    ObservableList<Customers> CustomerList= FXCollections.observableArrayList();
    ObservableList<Contact> ContactList= FXCollections.observableArrayList();
    ObservableList<Divisions> DivisionsList= FXCollections.observableArrayList();
    ObservableList<Countries> CountryList= FXCollections.observableArrayList();



    public void Users(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Users.fxml"));
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
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (User u : UserList) {
            selectUser.getItems().add(u.getUserName());
        }
        //selectUser.setItems(UserList.forEach(user -> {user.getUserName();}));
    }

    public void Countries(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Countries.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }
}
