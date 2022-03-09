package Controllers;

import Database.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainScreen implements Initializable {

    @FXML public TableView<Appointments> appTable = new TableView<>();
    @FXML public TableView<Customers> custTable = new TableView<>();
    @FXML public Label userLabel = new Label("");
    @FXML public static User validUser;
    @FXML private RadioButton radWeek;
    @FXML private RadioButton radMonth;
    @FXML private TextField time;
    @FXML private Boolean strWeekly = true;

    @FXML private TableColumn<Appointments, Integer> ID;
    @FXML private TableColumn<Appointments, String> Title;
    @FXML private TableColumn<Appointments, String> Description;
    @FXML private TableColumn<Appointments, String> Location;
    @FXML private TableColumn<Appointments, String> Type;
    @FXML private TableColumn<Appointments, String> Start;
    @FXML private TableColumn<Appointments, String> End;

    @FXML private TableColumn<Customers, Integer> custID;
    @FXML private TableColumn<Customers, String> custName;
    @FXML private TableColumn<Customers, String> custAddress;
    @FXML private TableColumn<Customers, String> custPostal;
    @FXML private TableColumn<Customers, String> custPhone;
    @FXML private TableColumn<Customers, String> custDiv;
    @FXML private TableColumn<Customers, String> custCountry;

    @FXML private TextField contactName;
    @FXML private TextField contactEmail;


    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    @FXML private Customers appCustomer;
    @FXML private Divisions appDiv;
    @FXML private Countries appCountry;
    @FXML private Contact appContact;

    @FXML private static int modAppointmentID;
    @FXML public static Integer getAppointmentId () { return modAppointmentID; }

    @FXML private static int modCustomerId;
    @FXML public static Integer getModCustomerId () { return modCustomerId; }

    @FXML private static int modContactId;
    @FXML public static Integer getModContactId () { return modContactId; }

    //time craziness
    @FXML private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML private final ZoneId localZoneID = ZoneId.systemDefault();
    @FXML private final ZoneId utcZoneID = ZoneId.of("UTC");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            popAppointments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        appTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                try {
                    popAppData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        time.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getMinute() < 10 && currentTime.getHour() > 9) {
                time.setText(currentTime.getHour() + ":0" + currentTime.getMinute() + ":" + currentTime.getSecond() + currentTime);
            } else if (currentTime.getMinute() > 9 && currentTime.getHour() < 10){
                time.setText("0" + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            } else {
                time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


        //compare time of appintments to within 15 minutes

    }

    private void popAppointments () throws Exception {
        AppList.clear();
        custTable.getItems().clear();
        contactName.setText("");
        contactEmail.setText("");
        try {
            UserList.addAll(DBUsers.getAllUsers());
            validUser = Login.getValidUser();
            AppList.addAll(Objects.requireNonNull(DBAppointments.getUserAppointments(validUser.getUserId())));
        } catch (Exception ex) {
            Logger.getLogger(newCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (strWeekly) {
            //filter appointments for week
            LocalDate now = LocalDate.now();
            LocalDate nowPlus1Week = now.plusWeeks(1);

            //lambda expression used to efficiently filter appointments by week
            FilteredList<Appointments> filteredData = new FilteredList<>(AppList);
            filteredData.setPredicate(row -> {

                LocalDate rowDate = LocalDate.parse(row.getAppStart(), formatter);

                return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Week);
            });
            appTable.setItems(filteredData);
        } else {
            //filter appointments for month
            LocalDate now = LocalDate.now();
            LocalDate nowPlus1Month = now.plusMonths(1);

            //lambda expression used to efficiently filter appointments by month
            FilteredList<Appointments> filteredData = new FilteredList<>(AppList);
            filteredData.setPredicate(row -> {

                LocalDate rowDate = LocalDate.parse(row.getAppStart(), formatter);

                return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Month);
            });

            appTable.setItems(filteredData);
        }

        userLabel.setText(validUser.getUserName());
        //appTable.setItems(AppList);
        ID.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Title.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        Description.setCellValueFactory(new PropertyValueFactory<>("appDesc"));
        Location.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        Type.setCellValueFactory(new PropertyValueFactory<>("appType"));
        Start.setCellValueFactory(new PropertyValueFactory<>("appStart"));
        End.setCellValueFactory(new PropertyValueFactory<>("appEnd"));






    }

    private void popAppData() throws Exception {

        if (appTable.getSelectionModel().getSelectedItem() != null) {
            int custInt = appTable.getSelectionModel().getSelectedItem().getcustomerId();
            int conID = appTable.getSelectionModel().getSelectedItem().getContactId();
            try {
                appCustomer = DBCustomers.getCustomerData(custInt);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            custTable.getItems().clear();
            custTable.getItems().add(appCustomer);
            custID.setCellValueFactory(new PropertyValueFactory<Customers, Integer>("customerId"));
            custName.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerName"));
            custAddress.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerAddress"));
            custPhone.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerPhone"));
            custPostal.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerPostal"));
            custDiv.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerDivision"));
            custCountry.setCellValueFactory(new PropertyValueFactory<Customers, String>("customerCountry"));
            contactName.setText(String.valueOf(DBContacts.getContactData(conID).getContactName()));
            contactEmail.setText(String.valueOf(DBContacts.getContactData(conID).getContactEmail()));
        } else {

            custTable.getItems().clear();
            custTable.getSelectionModel().clearSelection();
        }

    }

    public void radWeekly(ActionEvent actionEvent) throws Exception {
        strWeekly = true;
        radWeek.setSelected(true);
        radMonth.setSelected(false);
        popAppointments();
    }

    public void radMonthly(ActionEvent actionEvent) throws Exception {
        strWeekly = false;
        radWeek.setSelected(false);
        radMonth.setSelected(true);
        popAppointments();
    }

    public void newCustomer(ActionEvent actionEvent) throws Exception {
        //change scenes
        if (appTable.getSelectionModel().getSelectedItem() != null ) {
            error_message("You have an appointment selected, you cannot add more than one customer per appointment");
            appTable.getSelectionModel().clearSelection();
            popAppData();

        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/newCustomer.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 240, 330);
            stage.setTitle("New Customer");
            stage.setScene(scene);
            stage.show();
        }
    }
    public void modCustomer(ActionEvent actionEvent) throws IOException {
        //change scenes
        if (custTable.getSelectionModel().getSelectedItem() != null) {
            modCustomerId = custTable.getSelectionModel().getSelectedItem().getCustomerId();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/modCustomer.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 240, 330);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        } else {
            error_message("You must selected a customer to modify");
        }
    }

    public void delCustomer(ActionEvent actionEvent) {
        if (custTable.getSelectionModel().getSelectedItem() != null) {
            modCustomerId = custTable.getSelectionModel().getSelectedItem().getCustomerId();
            String appDeleteData = "";
            for (Appointments a : AppList) {
                if (a.getcustomerId() == modCustomerId) {
                    appDeleteData = appDeleteData + "\t" + a.getAppId() + "\t" + a.getAppType() + "\n";
                }
            }
            ButtonType foo = new ButtonType("DELETE", ButtonBar.ButtonData.OK_DONE);
            ButtonType bar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", foo, bar);
            alert.setTitle("WARNING");
            alert.setHeaderText("Are you sure you want to do that?\n THIS WILL DELETE THE ASSOCIATED APPOINTMENT(S):\n" + appDeleteData );
            alert.setContentText("Deleting Record!!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == foo) {
                    try {
                        DBCustomers.delCustomerData(modCustomerId);
                        popAppointments();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            error_message("No Customer was selected");
        }

    }

    public void newApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/newAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 300, 450);
        stage.setTitle("New Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void modApp(ActionEvent actionEvent) throws IOException {
        if (appTable.getSelectionModel().getSelectedItem() != null) {
            modAppointmentID = appTable.getSelectionModel().getSelectedItem().getAppId();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/modAppointment.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 300, 450);
            stage.setTitle("Modify Contact");
            stage.setScene(scene);
            stage.show();
        } else {
            error_message("You must selected an appointment to modify");
        }
    }

    public void delApp(ActionEvent actionEvent) {
        if (appTable.getSelectionModel().getSelectedItem() != null) {
            modAppointmentID = appTable.getSelectionModel().getSelectedItem().getAppId();
            ButtonType foo = new ButtonType("DELETE", ButtonBar.ButtonData.OK_DONE);
            ButtonType bar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING, "", foo, bar);
            alert.setTitle("WARNING");
            alert.setHeaderText("Are you sure you want to do that?\n THIS WILL DELETE THE APPOINTMENT:" + "\n\t" + modAppointmentID + "\t" + appTable.getSelectionModel().getSelectedItem().getAppType());
            alert.setContentText("Deleting Record!!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == foo) {
                    try {
                        DBAppointments.delAppointment(modAppointmentID);
                        popAppointments();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            error_message("No Appointment was selected");
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

    public void newContact(ActionEvent actionEvent) throws Exception {
        if (appTable.getSelectionModel().getSelectedItem() != null ) {
            error_message("You have an appointment selected, you cannot add more than one contact per appointment");
            appTable.getSelectionModel().clearSelection();
            popAppData();
        } else {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/newContact.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 240, 330);
            stage.setTitle("New Contact");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void modContact(ActionEvent actionEvent) throws IOException {
        if (appTable.getSelectionModel().getSelectedItem() != null) {
            modContactId = appTable.getSelectionModel().getSelectedItem().getContactId();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/modContact.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 240, 330);
            stage.setTitle("Modify Contact");
            stage.setScene(scene);
            stage.show();
        } else {
            error_message("You must selected an appointment to modify");
        }

    }

    public void delContact(ActionEvent actionEvent) {
        int delContact = appTable.getSelectionModel().getSelectedItem().getContactId();
        String appDeleteData = "";
        for (Appointments a : AppList) {
            if (a.getContactId() == delContact) {
                appDeleteData = appDeleteData + "\t" + a.getAppId() + "\t" + a.getAppType() + "\n";
            }
        }
        ButtonType foo = new ButtonType("DELETE", ButtonBar.ButtonData.OK_DONE);
        ButtonType bar = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.WARNING, "", foo, bar);
        alert.setTitle("WARNING");
        alert.setHeaderText("Are you sure you want to do that?\n THIS WILL DELETE THE ASSOCIATED APPOINTMENT(S):\n" + appDeleteData);
        alert.setContentText("Deleting Record!!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == foo) {
                try {
                    DBContacts.delContactData((delContact));
                    popAppointments();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void btnReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Reporting.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 535);
        stage.setTitle("Reporting");
        stage.setScene(scene);
        stage.show();
    }

    public void btnExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
