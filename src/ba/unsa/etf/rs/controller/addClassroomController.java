package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.ClassroomDAO;
import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addClassroomController {
    public TextField fldClassroomName;
    public TextField fldCapacity;
    private ClassroomDAO daoClassroom;
    private boolean ok=false;
public boolean edit=false;
public Classroom classroom=null;
    public addClassroomController(ClassroomDAO daoClassroom) {
this.daoClassroom=daoClassroom;
    }

    public addClassroomController(ClassroomDAO daoClassroom, Classroom trenutniClassroom) {
        this.daoClassroom=daoClassroom;
        this.classroom=trenutniClassroom;
        edit=true;
    }

    @FXML
    public void initialize() {
if(edit==true){
    fldClassroomName.setText(classroom.getName());
    fldCapacity.setText(String.valueOf(classroom.getCapacity()));
}
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

    public boolean isOk() {
        return this.ok;
    }

    public  boolean isEdit() {
        return this.edit;
    }

    public void btnCancelClassroom(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }

    public void btnConfirmClassroom(ActionEvent actionEvent) {
        ok=true;
        if(!edit && fldClassroomName.getStyleClass().contains("poljeIspravno") && fldCapacity.getStyleClass().contains("poljeIspravno") && !daoClassroom.getAllClassrooms().contains(new Classroom(fldClassroomName.getText(),Integer.parseInt(fldCapacity.getText()))))
        {
            daoClassroom.addClassroom(new Classroom(fldClassroomName.getText(), Integer.parseInt(fldCapacity.getText())));
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
         else if (!edit  && fldClassroomName.getStyleClass().contains("poljeIspravno") && fldCapacity.getStyleClass().contains("poljeIspravno"))
        {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText(null);
            alert.setContentText("This classroom already exists");

            alert.showAndWait();
        }
         else if(edit   && fldClassroomName.getStyleClass().contains("poljeIspravno") && fldCapacity.getStyleClass().contains("poljeIspravno"))
        {
            int pom=daoClassroom.findClassroomID(classroom.getName());
            daoClassroom.updateClassrom(new Classroom(fldClassroomName.getText(),Integer.parseInt(fldCapacity.getText())),pom);
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();

        }
    }
}
