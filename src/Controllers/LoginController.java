package Controllers;

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
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private static User validatedUser;
    @FXML
    private ObservableList<User> UserList = FXCollections.observableArrayList();


    public static User getValidUser() { return validatedUser;}



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            UserList.addAll(DBUsers.getAllUsers());
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Login(ActionEvent actionEvent) throws IOException {
        boolean validUser = false;
        for (User u : UserList ) {
            if (u.getUserName().equals(txtUsername.getText())) {
                if (u.getPassword().equals(txtPassword.getText())) {
                    validatedUser = u;
                    Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 500);
                    stage.setTitle("Main Interface");
                    stage.setScene(scene);
                    stage.show();
                    validUser = true;
                } else {
                    break;
                }
            }
        }
        if (!validUser){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Incorrect Credentials");
            alert.setHeaderText("Error!:  Incorrect Credentials");
            alert.setContentText("Bummer, should of wrote it down");
            alert.showAndWait();
        }
    }
}
