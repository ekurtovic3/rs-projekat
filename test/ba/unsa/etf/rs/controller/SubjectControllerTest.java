package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.Admin;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
class SubjectControllerTest {
    Stage thestage;
    SubjectDAO daoSubject= SubjectDAO.getInstance();
    ClassroomDAO daoClassroom= ClassroomDAO.getInstance();
    UserDAO daoUser=UserDAO.getInstance();
    ClassDAO daoClass= ClassDAO.getInstance();
    ProfessorToSubjectDAO daoProfessorToSubjectDAO=ProfessorToSubjectDAO.getInstance();

    Classroom classroom = new Classroom("0-1",10);
    Subject subject = new Subject("UUP");
    Date date = Date.valueOf(LocalDate.now());
    Admin user= (Admin) daoUser.getAllUsers().get(0);



    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/subject.fxml"));
        SubjectController controller = new SubjectController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, subject);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle(null);
        stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();

        thestage = stage;
    }
    @Test
    void btnDeleteProf(FxRobot robot) {

        daoProfessorToSubjectDAO.clearAll();
        daoProfessorToSubjectDAO.defaultData();
        ListView<User> listView = robot.lookup("#profesorsOfSubject").queryAs( ListView.class);
        MenuButton addStudent = robot.lookup("#mbtnAddStudent").queryAs(MenuButton.class);
        assertNotNull(addStudent);
        MenuButton addProfesor = robot.lookup("#mbtnAddProfesor").queryAs(MenuButton.class);
        Button delete = robot.lookup("#btnDeleteProfesor").queryAs(Button.class);
        assertNotNull(addProfesor);
        robot.clickOn("Emir Kurtovic");
        robot.clickOn(delete);
        assertEquals(0,listView.getItems().size());
    }

}