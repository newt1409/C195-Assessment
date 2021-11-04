package Controllers;

import Database.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentsController implements Initializable {
    @FXML private TableView<Appointments> appTable;
    @FXML private TableColumn<?, ?> ID;
    @FXML private TableColumn<?, ?> appTitle;
    @FXML private TableColumn<?, ?> Description;
    @FXML private TableColumn<?, ?> Location;
    @FXML private TableColumn<?, ?> Type;
    @FXML private TableColumn<?, ?> Start;
    @FXML private TableColumn<?, ?> End;

    ObservableList<Appointments> appList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appTitle.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        //TITLE.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));




        try {
            appList.addAll(DBAppointments.getAllAppointments());


        } catch (Exception ex) {
            Logger.getLogger(UsersController.class.getName()).log(Level.SEVERE, null, ex);
        }
        appTable.setItems(appList);
    }


}
