package Controllers;
import Database.DBContacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 * Creates a new contact to be added to the database
 * @author Weston Brehe
 */
public class newContact implements Initializable {
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
     * Nothing special done here
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    /**
     * Save function to save the new information
     * @param actionEvent
     * @throws Exception
     */
    public void Save(ActionEvent actionEvent) throws Exception {
        DBContacts.addContactData(conName.getText(), conEmail.getText());
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
