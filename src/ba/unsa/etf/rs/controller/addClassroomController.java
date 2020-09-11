package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.ClassroomDAO;
import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addClassroomController {
    public TextField fldClassroomName;
    public TextField fldCapacity;
    private ClassroomDAO daoClassroom;
    private boolean ok;


    public addClassroomController(ClassroomDAO daoClassroom) {
this.daoClassroom=daoClassroom;
    }
    @FXML
    public void initialize() {

        fldClassroomName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldClassroomName.getStyleClass().removeAll("poljeNijeIspravno");
                fldClassroomName.getStyleClass().add("poljeIspravno");
            } else {
                fldClassroomName.getStyleClass().removeAll("poljeIspravno");
                fldClassroomName.getStyleClass().add("poljeNijeIspravno");
            }
        });

           fldCapacity.textProperty().addListener((obs, oldIme, newIme) -> {
        if ((isNumeric(newIme))) {
            fldCapacity.getStyleClass().removeAll("poljeNijeIspravno");
            fldCapacity.getStyleClass().add("poljeIspravno");
        } else {
            fldCapacity.getStyleClass().removeAll("poljeIspravno");
            fldCapacity.getStyleClass().add("poljeNijeIspravno");
        }
    });}

        private boolean isNumeric(String strNum) {
            if (strNum == null) {
                return false;
            }
            try {
                double d = Double.parseDouble(strNum);
            } catch (NumberFormatException nfe) {
                return false;
            }
            return true;
        }

    public void btnCancelClassroom(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void btnConfirmClassroom(ActionEvent actionEvent) {
        ok=true;
        if(fldClassroomName.getStyleClass().contains("poljeIspravno") && fldCapacity.getStyleClass().contains("poljeIspravno"))
        daoClassroom.addClassroom(new Classroom(fldClassroomName.getText(),Integer.parseInt(fldCapacity.getText())));
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }
}
