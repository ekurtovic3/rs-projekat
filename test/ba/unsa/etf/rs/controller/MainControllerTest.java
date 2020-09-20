package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.ClassroomDAO;
import ba.unsa.etf.rs.database.CountryDAO;
import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.database.UserDAO;
import ba.unsa.etf.rs.model.User;
import com.sun.javafx.css.StyleCache;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.EventObject;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {
    Stage thestage;
    SubjectDAO daoSubject= SubjectDAO.getInstance();
    ClassroomDAO daoClassroom= ClassroomDAO.getInstance();
    UserDAO daoUser=UserDAO.getInstance();

    @Start
    public void start (Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));

        CountryDAO countryDao = CountryDAO.getInstance();

        loader.setController(new StartScreenController(countryDao));
        Parent root = loader.load();
        stage.setTitle("");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        thestage=stage;

    }



    @Test
    void logIn(FxRobot robot) {
        robot.clickOn("Log in");
        TextField username = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());

        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        assertNotNull(btn);
        // Zatvori dijalog
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void logInInvalid(FxRobot robot) {
        robot.clickOn("Log in");
        TextField username = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("dwadwa");
        robot.clickOn("Confirm");
        assertTrue(thestage.isShowing());
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn("Log in");
        robot.clickOn(username);
        robot.write("profesor");
        robot.clickOn(pass);
        robot.write("profesor");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        assertNotNull(btn);
        // Zatvori dijalog
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void student(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        robot.clickOn("Log in");
        TextField username = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username);
        robot.write("student");
        robot.clickOn(pass);
        robot.write("student");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        //TabPane a = robot.lookup("#tbUsers").queryAs(TabPane.class);
        robot.clickOn("Users"); // Ne moze pristupiti jer je student
        robot.clickOn("Subjects");
        robot.clickOn("Classrooms");
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        assertNotNull(btn);
        // Zatvori dijalog
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void admin(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        robot.clickOn("Log in");
        TextField username = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        //TabPane a = robot.lookup("#tbUsers").queryAs(TabPane.class);
        robot.clickOn("Users"); //  moze pristupiti jer je admin
        robot.clickOn("Subjects");
        robot.clickOn("Classrooms");
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        assertNotNull(btn);
        // Zatvori dijalog
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void admin2(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        robot.clickOn("Log in");
        TextField username = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        assertNotNull(btn);
        robot.clickOn("Profesors");
        // Zatvori dijalog
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void addUser(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button add = robot.lookup("#btnUserAdd").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn(add);
        robot.clickOn(name);
        robot.write("Test");
        robot.clickOn(surname);
        robot.write("Testovic");
        robot.clickOn(birth);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        robot.write("06/06/1995");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn(jmbg);
        robot.write("0606995170022");
        robot.clickOn(email);
        robot.write("test@gmail.com");

        robot.clickOn(username);
        robot.write("test");
        robot.clickOn("Profesor");
        robot.clickOn("Confirm");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(4,daoUser.getAllUsers().size());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });

    }
    @Test
    void addUserProfesor(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button add = robot.lookup("#btnUserAdd").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn(add);
        robot.clickOn(name);
        robot.write("Test");
        robot.clickOn(surname);
        robot.write("Testovic");
        robot.clickOn(birth);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        robot.write("06/06/1995");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn(jmbg);
        robot.write("0606995170022");
        robot.clickOn(email);
        robot.write("test@gmail.com");

        robot.clickOn(username);
        robot.write("test");
        robot.clickOn("Profesor");
        robot.clickOn("Confirm");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(4,daoUser.getAllUsers().size());
        assertEquals(2, daoUser.getAllSpecificUsers(2).size());
        String statusMsg = robot.lookup("#statusMsg").queryAs(Label.class).getText();

        assertEquals("User added.", statusMsg);
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void addUserInvalid(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button add = robot.lookup("#btnUserAdd").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn(add);
        robot.clickOn(name);
        robot.write("Meho");
        robot.clickOn(surname);
        robot.write("Mehic");
        robot.clickOn(birth);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        String statusMsg = robot.lookup("#statusMsg").queryAs(Label.class).getText();
        assertEquals("Adding new user.", statusMsg);

        robot.write("05/05/1995");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn(jmbg);
        robot.write("0606995170022");
        robot.clickOn(email);
        robot.write("test@gmail.com");

        robot.clickOn(username);
        robot.write("test");
        robot.clickOn("Student");
        robot.clickOn("Confirm");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }

    @Test
    void addUserCancel(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button add = robot.lookup("#btnUserAdd").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        robot.clickOn(add);
        robot.clickOn(name);
        robot.write("Name");
        robot.clickOn(surname);
        robot.write("Surname");
        robot.clickOn(birth);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        robot.write("06/06/1995");
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);
        robot.clickOn(jmbg);
        robot.write("0606995170022");
        robot.clickOn(email);
        robot.write("validail@gmail.com");
        String statusMsg = robot.lookup("#statusMsg").queryAs(Label.class).getText();
        assertEquals("Adding new user.", statusMsg);

        robot.clickOn(username);
        robot.write("username");
        robot.clickOn("Profesor");
        robot.clickOn("Cancel");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);


        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void editUser(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button edit = robot.lookup("#btnUserEdit").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        ListView<User> listView = robot.lookup("#listViewUsers").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(edit);
        robot.clickOn(name);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("Edit");
        robot.clickOn(surname);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("Editing");

        robot.clickOn(username);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("oprst");
        robot.clickOn("Student");
        robot.clickOn("Confirm");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(3,daoUser.getAllUsers().size());
assertEquals("Edit Editing",daoUser.getAllUsers().get(1).toString());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void editUserCancel(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button edit = robot.lookup("#btnUserEdit").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        ListView<User> listView = robot.lookup("#listViewUsers").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(edit);
        robot.clickOn(name);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("Edit");
        robot.clickOn(surname);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("Editing");

        robot.clickOn(username);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("oprst");
        robot.clickOn("Student");
        robot.clickOn("Cancel");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals("Dzan Tabakovic",daoUser.getAllUsers().get(1).toString());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void editUserInvalid(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button edit = robot.lookup("#btnUserEdit").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);
        TextField surname = robot.lookup("#fldSurname").queryAs(TextField.class);
        DatePicker birth =robot.lookup("#dpBirthday").queryAs(DatePicker.class);
        TextField email = robot.lookup("#fldEmail").queryAs(TextField.class);
        TextField jmbg = robot.lookup("#fldJmbg").queryAs(TextField.class);
        TextField username = robot.lookup("#fldUsername").queryAs(TextField.class);
        ListView<User> listView = robot.lookup("#listViewUsers").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(edit);
        robot.clickOn(name);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.clickOn(surname);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("Editing");

        robot.clickOn(username);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("oprst");
        robot.clickOn("Student");
        robot.clickOn(email);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("~a5");
        robot.clickOn("Confirm");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals("Dzan Tabakovic",daoUser.getAllUsers().get(1).toString());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void deleteUser(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button delete = robot.lookup("#btnUserDelete").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);

        ListView<User> listView = robot.lookup("#listViewUsers").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(delete);
        robot.clickOn(name);
        robot.write("dwada");


        robot.clickOn("Confirm");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(2,daoUser.getAllUsers().size());
        assertNotEquals("Dzan Tabakovic",daoUser.getAllUsers().get(1).toString());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
    @Test
    void deleteUserCancel(FxRobot robot) {
        daoUser.clearAll();
        daoUser.defaultData();
        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals(1, daoUser.getAllSpecificUsers(2).size());

        robot.clickOn("Log in");
        TextField username2 = robot.lookup("#tfUsernameLogIn").queryAs(TextField.class);
        PasswordField pass = robot.lookup("#tfPasswordLogIn").queryAs(PasswordField.class);
        robot.clickOn(username2);
        robot.write("admin");
        robot.clickOn(pass);
        robot.write("admin");
        robot.clickOn("Confirm");
        assertFalse(thestage.isShowing());
        robot.clickOn("Users");
        Button delete = robot.lookup("#btnUserDelete").queryAs(Button.class);
        TextField name = robot.lookup("#fldName").queryAs(TextField.class);

        ListView<User> listView = robot.lookup("#listViewUsers").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(delete);
        robot.clickOn(name);
        robot.write("dwada");


        robot.clickOn("Cancel");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

        assertEquals(3,daoUser.getAllUsers().size());
        assertEquals("Dzan Tabakovic",daoUser.getAllUsers().get(1).toString());
        Button btn = robot.lookup("#btnSreach").queryAs(Button.class);
        Platform.runLater( () -> {
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        });


    }
}