package Controllers;

import Database.DBContacts;
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class modContact implements Initializable {
    @FXML private TextField conName;
    @FXML private TextField conEmail;
    @FXML private TextField conID;

    @FXML private Contact modContact;
    @FXML private static int modConctactId;

    public void Save(ActionEvent actionEvent) throws Exception {
        DBContacts.modContactData(modConctactId,conName.getText(), conEmail.getText());
        goBack(actionEvent);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/MainScreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 875, 460);
        stage.setTitle("User Appointments");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modConctactId= MainScreen.getModContactId();
        try {
            modContact = DBContacts.getContactData(modConctactId);
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        conID.setText(String.valueOf(modContact.getContactID()));
        conName.setText(modContact.getContactName());
        conEmail.setText(modContact.getContactEmail());
    }
}
