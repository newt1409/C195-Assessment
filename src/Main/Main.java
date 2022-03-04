package Main;

import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Reporting.fxml"));
        primaryStage.setTitle("User Login");
        //primaryStage.setScene(new Scene(root, 250, 275));

        primaryStage.setScene(new Scene(root, 850, 535));


        primaryStage.show();
    }


    public static void main(String[] args) {

        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }
}
