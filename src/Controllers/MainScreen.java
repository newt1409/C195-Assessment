package Controllers;

import Database.DBAppointments;
import Database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen implements Initializable {

    public ChoiceBox selectUser = new ChoiceBox();
    public TableView<Appointments> appTable = new TableView();

    @FXML private TableColumn<Appointments, Integer> Id = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> Title = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> Desc = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> Loc = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> Type = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> Start = new TableColumn<>();
    @FXML private TableColumn<Appointments, String> End = new TableColumn<>();

    @FXML private TextField customerId;
    @FXML private TextField customerAddress;
    @FXML private TextField customerPostal;
    @FXML private TextField customerPhone;
    @FXML private TextField customerDiv;
    @FXML private TextField customerCountry;

    @FXML private TextField contactId;
    @FXML private TextField contactEmail;


    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    @FXML private ObservableList<Customers> CustomerList = FXCollections.observableArrayList();
    @FXML private ObservableList<Contact> ContactList = FXCollections.observableArrayList();
    @FXML private ObservableList<Divisions> DivisionsList = FXCollections.observableArrayList();
    @FXML private ObservableList<Countries> CountryList = FXCollections.observableArrayList();



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
        Id.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appName"));
        Desc.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Loc.setCellValueFactory(new PropertyValueFactory<>("appLoc"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));
    }

    public void popUserData(ActionEvent actionEvent) throws Exception {
        //System.out.println(selectUser.getValue());
        //System.out.println(AppList);
        //AppList.removeAll();
        //System.out.println(AppList);
        try {
            UserList.addAll(DBUsers.getAllUsers());
        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (User u : UserList) {
            if (u.getUserName() == selectUser.getValue()) {
                AppList.addAll(DBAppointments.getUserAppointments(u.getUserId()));

            }

        }
        System.out.println(AppList);
        appTable.setItems(AppList);
        AppList.removeAll();


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



}
