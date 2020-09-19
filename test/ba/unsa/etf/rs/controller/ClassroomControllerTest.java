package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.ClassroomDAO;
import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Classroom;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ClassroomControllerTest {
    Stage theStage;
    ClassroomController controller;
    SubjectDAO daoSubject= SubjectDAO.getInstance();
    ClassroomDAO daoClassroom= ClassroomDAO.getInstance();

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/classroom.fxml"));
        controller = new ClassroomController(daoClassroom);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Add classroom test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();

        theStage = stage;
    }

    @Test
    void btnCancelClassroom(FxRobot robot) {
        daoClassroom.clearAll();
        daoClassroom.defaultData();
        robot.clickOn("#fldClassroomName");
        robot.write("VA");
        robot.clickOn("#fldCapacity");
        robot.write("50");
        robot.clickOn("Cancel");
        assertFalse(theStage.isShowing());
        Classroom expected = new Classroom("VA",50);
        assertFalse(daoSubject.getAllSubjects().contains(expected));
    }

    @Test
    void btnConfirmClassroom(FxRobot robot) {
        daoClassroom.clearAll();
        daoClassroom.defaultData();
        robot.clickOn("#fldClassroomName");
        robot.write("MA");
        robot.clickOn("#fldCapacity");
        robot.write("25");
        robot.clickOn("Confirm");
        assertFalse(theStage.isShowing());
        Classroom expected = new Classroom("MA",25);
        assertEquals(true, daoClassroom.getAllClassrooms().contains(expected));
    }
    @Test
    void btnConfirmClassroom2(FxRobot robot) {

        daoClassroom.clearAll();
        daoClassroom.defaultData();
        robot.clickOn("#fldClassroomName");
        robot.write("AAE");
        TextField name = robot.lookup("#fldClassroomName").queryAs(TextField.class);
        Background bg = name.getBackground();
        Paint yellowgreen = Paint.valueOf("#adff2f");
        boolean colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);

        TextField cap = robot.lookup("#fldCapacity").queryAs(TextField.class);
        robot.clickOn("#fldCapacity");
        robot.write("55");
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        bg = cap.getBackground();
        yellowgreen = Paint.valueOf("#adff2f");
        colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertFalse(colorFound);

        Classroom expected = new Classroom("AAE",55);
        assertEquals(false, daoClassroom.getAllClassrooms().contains(expected));

    }

    @Test
    void valdationClassroom(FxRobot robot) {

        daoClassroom.clearAll();
        daoClassroom.defaultData();
        robot.clickOn("#fldClassroomName");
        robot.write("VA");
        TextField name = robot.lookup("#fldClassroomName").queryAs(TextField.class);
        Background bg = name.getBackground();
        Paint yellowgreen = Paint.valueOf("#adff2f");
        boolean colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);

        TextField cap = robot.lookup("#fldCapacity").queryAs(TextField.class);
        robot.clickOn("#fldCapacity");
        robot.write("50");
         bg = cap.getBackground();
         yellowgreen = Paint.valueOf("#adff2f");
         colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);



    }
    @Test
    void valdationClassroom2(FxRobot robot) {

        daoClassroom.clearAll();
        daoClassroom.defaultData();
        robot.clickOn("#fldClassroomName");
        robot.write("VA");
        TextField name = robot.lookup("#fldClassroomName").queryAs(TextField.class);
        Background bg = name.getBackground();
        Paint yellowgreen = Paint.valueOf("#adff2f");
        boolean colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);

        TextField cap = robot.lookup("#fldCapacity").queryAs(TextField.class);
        robot.clickOn("#fldCapacity");
        robot.write("A");
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        bg = cap.getBackground();
        yellowgreen = Paint.valueOf("#adff2f");
        colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertFalse(colorFound);



    }

}