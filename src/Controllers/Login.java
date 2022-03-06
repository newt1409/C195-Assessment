package Controllers;

import Database.DBAppointments;
import Database.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointments;
import model.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private static User validatedUser;
    @FXML
    private ObservableList<User> UserList = FXCollections.observableArrayList();

    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ZoneId localZoneID = ZoneId.systemDefault();

    public static User getValidUser() { return validatedUser;}

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        try {
            UserList.addAll(DBUsers.getAllUsers());
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Login(ActionEvent actionEvent) throws Exception {
        boolean validUser = false;
        String loginLog = "login_activity.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(loginLog, true));
        for (User u : UserList ) {
            if (u.getUserName().equals(txtUsername.getText())) {
                if (u.getPassword().equals(txtPassword.getText())) {
                    validatedUser = u;

                    writer.append(LocalDateTime.now() + " " + validatedUser.getUserName() + " Successful Login" + "\n");
                    writer.flush();
                    writer.close();
                    //checkAppointments(); //Cant figure out why the lamba blows up with time added
                    Parent root = FXMLLoader.load(getClass().getResource("../Views/MainScreen.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 900, 500);
                    stage.setTitle("Main Interface");
                    stage.setScene(scene);
                    stage.show();
                    validUser = true;
                } else {
                    writer.append(LocalDateTime.now() + " " + validatedUser.getUserName() + " Unsuccessful Login" + "\n");
                    writer.flush();
                    writer.close();
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

    private void checkAppointments() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appPlus15 = now.plusMinutes(15);
        try {
            AppList.addAll(Objects.requireNonNull(DBAppointments.getUserAppointments(validatedUser.getUserId())));
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }

        FilteredList<Appointments> filteredData = new FilteredList<>(AppList);
        //lambda expression used to efficiently identify any appointment starting within the next 15 minutes
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = LocalDateTime.parse(row.getAppStart().substring(0, 16), formatter);
            return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(appPlus15);
        });
        if (! filteredData.isEmpty()) {
            error_message("Reminder - You have an appointment starting with the next 15 min \n DONT BE LATE!" );
        }

    }

    public static void error_message (String inMsg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(inMsg);
        alert.setContentText("click ok to return");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }
}
