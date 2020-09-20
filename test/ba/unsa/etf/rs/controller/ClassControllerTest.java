package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.Admin;
import ba.unsa.etf.rs.model.Class;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.junit.jupiter.api.Test;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(ApplicationExtension.class)
class ClassControllerTest {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/timetable.fxml"));
        ClassController controller = new ClassController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, classroom, subject, date,user);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle(null);
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();

        thestage = stage;
    }


    @Test
    void addClass(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());

        Button btnAdd = robot.lookup("#btnClassAdd").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        robot.clickOn(btnAdd);
        Integer i = (Integer)spiner.getValueFactory().getValue();
        assertEquals(8,i); //Default vrijednost 8
         i = (Integer)period.getValueFactory().getValue();
        assertEquals(0,i); //Default vrijednost 8
        robot.clickOn(choiceType);
        robot.clickOn("Tutorial");
        robot.clickOn(choiceSubject);
        robot.clickOn("UUP");
        robot.clickOn(choiceclassroom);
        robot.clickOn(choiceclassroom.getItems().get(0).toString());
        robot.clickOn("Confirm");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(4,daoClass.getAllClass().size());
        assertEquals(3,daoClass.initializeClass(date,classroom,subject).size());

    }

    @Test
    void addClassExist(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());

        Button btnAdd = robot.lookup("#btnClassAdd").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        robot.clickOn(btnAdd);
        Integer i = (Integer)spiner.getValueFactory().getValue();
        assertEquals(8,i); //Default vrijednost 8
        i = (Integer)period.getValueFactory().getValue();
        assertEquals(0,i); //Default vrijednost 8
        robot.clickOn(spiner);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.write("12");
        robot.clickOn(choiceType);
        robot.clickOn("Tutorial");
        robot.clickOn(choiceSubject);
        robot.clickOn("UUP");
        robot.clickOn(choiceclassroom);
        robot.clickOn(choiceclassroom.getItems().get(0).toString());
        robot.clickOn("Confirm");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3,daoClass.getAllClass().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        robot.press(KeyCode.ENTER).release(KeyCode.ENTER);

    }

    @Test
    void editClass(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        Button edit = robot.lookup("#btnClassEdit").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        ListView<Class> listView = robot.lookup("#listViewClass").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(1).toString());
        robot.clickOn(edit);
        Integer i = (Integer)spiner.getValueFactory().getValue();
       // assertEquals(13,i);
        assertEquals(false,period.isVisible()); //Ne prikazuje se tokom edita
        robot.clickOn(spiner);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("16");
        robot.clickOn(choiceType);
        robot.clickOn("Exercises");
        robot.clickOn(choiceSubject);
        robot.clickOn("UUP");
        robot.clickOn(choiceclassroom);
        robot.clickOn(choiceclassroom.getItems().get(0).toString());
        robot.clickOn("Confirm");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3,daoClass.getAllClass().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        assertEquals("UUP 16:00-17:00 0-1 Exercises",daoClass.initializeClass(date,classroom,subject).get(1).toString());
    }


    @Test
    void editClassCancel(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        Button edit = robot.lookup("#btnClassEdit").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        ListView<Class> listView = robot.lookup("#listViewClass").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(0).toString());
        robot.clickOn(edit);
        Integer i = (Integer)spiner.getValueFactory().getValue();
        // assertEquals(13,i);
        assertEquals(false,period.isVisible()); //Ne prikazuje se tokom edita
        robot.clickOn(spiner);
        robot.press(KeyCode.CONTROL).press(KeyCode.A).press(KeyCode.BACK_SPACE).release(KeyCode.A).release(KeyCode.BACK_SPACE).release(KeyCode.CONTROL);
        robot.write("16");
        robot.clickOn(choiceType);
        robot.clickOn("Exercises");
        robot.clickOn(choiceSubject);
        robot.clickOn("UUP");
        robot.clickOn(choiceclassroom);
        robot.clickOn(choiceclassroom.getItems().get(0).toString());
        robot.clickOn("Cancel");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(3,daoClass.getAllClass().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        assertEquals("UUP 13:00-14:00 0-1 Lectures",daoClass.initializeClass(date,classroom,subject).get(1).toString());
    }

    @Test
    void deleteClassConfrim(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        Button delete = robot.lookup("#btnClassDelete").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        ListView<Class> listView = robot.lookup("#listViewClass").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(0).toString());
        robot.clickOn(delete);
        Integer i = (Integer)spiner.getValueFactory().getValue();
         assertEquals(12,i);
         robot.clickOn("Confirm");
        assertEquals(2,daoClass.getAllClass().size());
        assertEquals(1,daoClass.initializeClass(date,classroom,subject).size());
    }
    @Test
    void deleteClassCancel(FxRobot robot) {
        daoClass.clearAll();
        daoClass.defaultData();
        assertEquals(3,daoClassroom.getAllClassrooms().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
        Button delete = robot.lookup("#btnClassDelete").queryAs(Button.class);
        Spinner spiner= robot.lookup("#spStart").queryAs(Spinner.class);
        ChoiceBox choiceType = robot.lookup("#choiceType").queryAs(ChoiceBox.class);
        ChoiceBox choiceSubject = robot.lookup("#choiceSubject").queryAs(ChoiceBox.class);
        ChoiceBox choiceclassroom = robot.lookup("#choiceClassroom").queryAs(ChoiceBox.class);
        Spinner period= robot.lookup("#spPeriod").queryAs(Spinner.class);
        ListView<Class> listView = robot.lookup("#listViewClass").queryAs( ListView.class);
        robot.clickOn(listView.getItems().get(0).toString());
        robot.clickOn(delete);
        Integer i = (Integer)spiner.getValueFactory().getValue();
        assertEquals(12,i);
        robot.clickOn("Cancel");
        assertEquals(3,daoClass.getAllClass().size());
        assertEquals(2,daoClass.initializeClass(date,classroom,subject).size());
    }

}