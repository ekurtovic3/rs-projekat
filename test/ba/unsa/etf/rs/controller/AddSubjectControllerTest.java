package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Subject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class AddSubjectControllerTest {

    Stage theStage;
    AddSubjectController controller;
    SubjectDAO daoSubject= SubjectDAO.getInstance();

    @Start
    public void start (Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"));
        controller = new AddSubjectController(daoSubject);
        loader.setController(controller);
        Parent root = loader.load();
        stage.setTitle("Add subject test");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();

        theStage = stage;
    }


    @Test
    void cancelSubject(FxRobot robot) {
        robot.clickOn("#fldNameSubject");
        robot.write("RS");
        Button confirm = robot.lookup("#btnConfirmSubject").queryAs(Button.class);
        Button cancel = robot.lookup("#btnCancelSubject").queryAs(Button.class);
        assertNotNull(confirm);
        assertNotNull(cancel);
        robot.clickOn("#btnCancelSubject");
        assertFalse(theStage.isShowing());

    }
    @Test
    void cancelSubject2(FxRobot robot) {
        robot.clickOn("#fldNameSubject");
        robot.write("UUP");
        Button confirm = robot.lookup("#btnConfirmSubject").queryAs(Button.class);
        Button cancel = robot.lookup("#btnCancelSubject").queryAs(Button.class);
        assertNotNull(confirm);
        assertNotNull(cancel);
        String expected = "UUP";
        assertFalse(daoSubject.getAllSubjects().contains(expected));
        robot.clickOn("#btnCancelSubject");
        assertFalse(theStage.isShowing());

    }

    @Test
    void confirmSubject(FxRobot robot) {
        daoSubject.clearAll();
        daoSubject.defaultData();
        robot.clickOn("#fldNameSubject");
        robot.write("OBP");
        Button confirm = robot.lookup("#btnConfirmSubject").queryAs(Button.class);
        Button cancel = robot.lookup("#btnCancelSubject").queryAs(Button.class);
        assertNotNull(confirm);
        assertNotNull(cancel);
        Subject expected = new Subject("OBP");
        robot.clickOn(confirm);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(true, daoSubject.getAllSubjects().contains(expected));
        assertFalse(theStage.isShowing());

    }
    @Test
    void valdationSubject(FxRobot robot) {
        daoSubject.clearAll();
        daoSubject.defaultData();
        robot.clickOn("#fldNameSubject");
        robot.write("OBP");
        TextField autor = robot.lookup("#fldNameSubject").queryAs(TextField.class);
        Background bg = autor.getBackground();
        Paint yellowgreen = Paint.valueOf("#adff2f");
        boolean colorFound = false;
        for (
                BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        colorFound = false;
        for ( BackgroundFill bf : bg.getFills())
            if (bf.getFill().equals(yellowgreen))
                colorFound = true;
        assertTrue(colorFound);
    }



}