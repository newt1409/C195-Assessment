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


public class newContact implements Initializable {
    @FXML private TextField conName;
    @FXML private TextField conEmail;
    @FXML private TextField conID;

    public void Save(ActionEvent actionEvent) throws Exception {
        DBContacts.addContactData(conName.getText(), conEmail.getText());
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

    }
}
