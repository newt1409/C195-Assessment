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

/**
 * Main class for all actions in the program
 * @author Weston Brehe
 */
public class MainScreen implements Initializable {
    /**
     * Tableview to show the upcoming appointments
     */
    @FXML public TableView<Appointments> appTable = new TableView<>();
    /**
     * Tableview to show the customers associated with the appointment from appTable
     */
    @FXML public TableView<Customers> custTable = new TableView<>();
    /**
     * Label to show the current logged in user
     */
    @FXML public Label userLabel = new Label("");
    /**
     * autheticated user logged into the program
     */
    @FXML public static User validUser;
    /**
     * radial to show appointments within the next week
     */
    @FXML private RadioButton radWeek;
    /**
     * radial to show appointments within the next month
     */
    @FXML private RadioButton radMonth;
    /**
     * textfield to show the current time
     */
    @FXML private TextField time;
    /**
     * Boolean if the current view is week or not
     */
    @FXML private Boolean strWeekly = true;
    /**
     * Tablecolumn for appointments value ID
     */
    @FXML private TableColumn<Appointments, Integer> ID;
    /**
     * Tablecolumn for appointments value Title
     */
    @FXML private TableColumn<Appointments, String> Title;
    /**
     * Tablecolumn for appointments value Description
     */
    @FXML private TableColumn<Appointments, String> Description;
    /**
     * Tablecolumn for appointments value Location
     */
    @FXML private TableColumn<Appointments, String> Location;
    /**
     * Tablecolumn for appointments value Type
     */
    @FXML private TableColumn<Appointments, String> Type;
    /**
     * Tablecolumn for appointments value Start time
     */
    @FXML private TableColumn<Appointments, String> Start;
    /**
     * Tablecolumn for appointments value Stop time
     */
    @FXML private TableColumn<Appointments, String> End;
    /**
     * Tablecolumn for appointments value UserID
     */
    @FXML private TableColumn<Appointments, Integer> UserID;
    /**
     * Tablecolumn for customer value customer id
     */
    @FXML private TableColumn<Customers, Integer> custID;
    /**
     * Tablecolumn for customer value Name
     */
    @FXML private TableColumn<Customers, String> custName;
    /**
     * Tablecolumn for customer value Address
     */
    @FXML private TableColumn<Customers, String> custAddress;
    /**
     * Tablecolumn for customer value Postal code
     */
    @FXML private TableColumn<Customers, String> custPostal;
    /**
     * Tablecolumn for customer value phone number
     */
    @FXML private TableColumn<Customers, String> custPhone;
    /**
     * Tablecolumn for customer value state or province
     */
    @FXML private TableColumn<Customers, String> custDiv;
    /**
     * Tablecolumn for customer value country
     */
    @FXML private TableColumn<Customers, String> custCountry;
    /**
     * Text field to show the current appointments contact name
     */
    @FXML private TextField contactName;
    /**
     * Text field to show the current appointments contact email
     */
    @FXML private TextField contactEmail;
    /**
     * Observable list of users when adding or modify appointments
     */
    @FXML private ObservableList<User> UserList = FXCollections.observableArrayList();
    /**
     * Observable list of appointments, used through program to maintain list of appointments in the database
     */
    @FXML private ObservableList<Appointments> AppList = FXCollections.observableArrayList();
    /**
     * Customer data of the current appointment selected
     */
    @FXML private Customers appCustomer;
    /**
     * State or Province data of the selected customer
     */
    @FXML private Divisions appDiv;
    /**
     * Country data of the selected customer
     */
    @FXML private Countries appCountry;
    /**
     * Contact data of the selected appointment
     */
    @FXML private Contact appContact;
    /**
     * Appointment ID that will be modified
     */
    @FXML private static int modAppointmentID;

    /**
     * appointment id getter for modAppointments.java
     * @return appointment id
     */
    @FXML public static Integer getAppointmentId () { return modAppointmentID; }
    /**
     * Customer ID that will be modified
     */
    @FXML private static int modCustomerId;
    /**
     * customer id getter for modCustomer.java
     * @return customer id
     */
    @FXML public static Integer getModCustomerId () { return modCustomerId; }
    /**
     * contact id that will be modified
     */
    @FXML private static int modContactId;

    /**
     * Contact ID getter for modContact.java
     * @return contact id
     */
    @FXML public static Integer getModContactId () { return modContactId; }

    /**
     * Date time formatter for the time format in the database
     */
    @FXML private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Current zoneid for the local machine
     */
    @FXML private final ZoneId localZoneID = ZoneId.systemDefault();
    /**
     * Current zoneid for the UTC conversion
     */
    @FXML private final ZoneId utcZoneID = ZoneId.of("UTC");

    /**
     * Initialize the main form, populate the current appointment in the database for the currently logged in user
     *
     * LAMBDA Expression for detecting when an appointment is selected to populate the customer and contact data.  This waits for the user
     * to select an appointment before displaying the data and is very clean way of waiting on the user.
     *
     * Display a current clock to show time so you dont miss appointments
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            popAppointments();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //LAMBDA
        appTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                try {
                    popAppData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //show a current clock
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
    }

    /**
     * Populate the appointment data into the TableView so the user can select one to modify or view
     *
     * LAMBDA Expression populate only a week or month of appointments.
     * @throws Exception
     */
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
        //show only the next week
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
        //show the next month
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
        UserID.setCellValueFactory(new PropertyValueFactory<>("UserId"));
    }

    /**
     * Populate the customer and contact data for the appointment selected
     * @throws Exception
     */
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
            //clear out data
            custTable.getItems().clear();
            custTable.getSelectionModel().clearSelection();
        }
    }

    /**
     * Radial to switch between weekly and monthly view ---> Weekly View
     * @param actionEvent
     * @throws Exception
     */
    public void radWeekly(ActionEvent actionEvent) throws Exception {
        strWeekly = true;
        radWeek.setSelected(true);
        radMonth.setSelected(false);
        popAppointments();
    }

    /**
     * Radial to switch between weekly and monthly view ---> Month View
     * @param actionEvent
     * @throws Exception
     */
    public void radMonthly(ActionEvent actionEvent) throws Exception {
        strWeekly = false;
        radWeek.setSelected(false);
        radMonth.setSelected(true);
        popAppointments();
    }

    /**
     * Error check and then start the new Customer class
     * @param actionEvent
     * @throws Exception
     */
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

    /**
     * Error check and then start the modify Customer class
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Error check and then warn before deleting a customer
     *
     * LAMBDA Expression to present the DELETE or cancel text on the buttons
     * @param actionEvent
     */
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

    /**
     * start the new Appointment class
     * @param actionEvent
     * @throws IOException
     */
    public void newApp(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/newAppointment.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 300, 450);
        stage.setTitle("New Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Error check and then start the modify Appointment class
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Error check and then warn before deleting a appointment
     *
     * LAMBDA Expression to present the DELETE or cancel text on the buttons
     * @param actionEvent
     */
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

    /**
     * Simple errror message to be used as needed from class
     * @param inMsg
     */
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

    /**
     * Error check and then start the new Contact class
     * @param actionEvent
     * @throws Exception
     */
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

    /**
     * Error check and then start the modify Contact class
     * @param actionEvent
     * @throws IOException
     */
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

    /**
     * Error check and then warn before deleting a appointment
     *
     * LAMBDA Expression to present the DELETE or cancel text on the buttons
     * @param actionEvent
     */
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

    /**
     * Start the Report class
     * @param actionEvent
     * @throws IOException
     */
    public void btnReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Reporting.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 535);
        stage.setTitle("Reporting");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Quit the program
     * @param actionEvent
     */
    public void btnExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}
