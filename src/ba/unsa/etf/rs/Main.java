package ba.unsa.etf.rs;
/*
package ba.unsa.etf.rs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
       Parent root = (Parent) FXMLLoader.load(this.getClass().getResource("/fxml/mainform.fxml"));
        primaryStage.setTitle("Aplikacija za raspored casova");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();}
    public static void main(String[] args) {
        launch(args);
    }}*/
   /*     TimetableDAO model = TimetableDAO.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainform.fxml"));
        loader.setController(new mainController(model));
        model.defaultUser();
        model.defaultClass();
        model.defaultSubject();
        Parent root = loader.load();
        primaryStage.setTitle("Library");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();*/
/*    }


    public static void main(String[] args) {
        launch(args);
    }
}*/

import ba.unsa.etf.rs.controller.StartScreenController;
import ba.unsa.etf.rs.database.CountryDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));

        CountryDAO countryDao = CountryDAO.getInstance();

        loader.setController(new StartScreenController(countryDao));
        Parent root = loader.load();
        primaryStage.setTitle("Aplikacija za raspored casova");
        primaryStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        primaryStage.show();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
