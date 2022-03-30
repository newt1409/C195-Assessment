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
/**
 * Repeat class of new contact but modified as it uses a modify ID to populate the data and save over that id
 * @author Weston Brehe
 */
public class modContact implements Initializable {
    /**
     * Text field for the contact Name
     */
    @FXML private TextField conName;
    /**
     * Text field for the contact Email
     */
    @FXML private TextField conEmail;
    /**
     * Text field for the contact ID
     */
    @FXML private TextField conID;
    /**
     * Contact data of the contact to be modified
     */
    @FXML private Contact modContact;
    /**
     * Contact ID of the contact to be modified
     */
    @FXML private static int modConctactId;

    /**
     * Initialize the frame and collect the data for the contact to be modified
     * @param url
     * @param resourceBundle
     */
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
    /**
     * Save function to correct the contacts information
     * @param actionEvent
     * @throws Exception
     */
    public void Save(ActionEvent actionEvent) throws Exception {
        DBContacts.modContactData(modConctactId,conName.getText(), conEmail.getText());
        goBack(actionEvent);
    }
    /**
     * Simple go back to previous frame
     * @param actionEvent
     * @throws IOException
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        //change scenes
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/MainScreen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 875, 460);
        stage.setTitle("User Appointments");
        stage.setScene(scene);
        stage.show();
    }
}
