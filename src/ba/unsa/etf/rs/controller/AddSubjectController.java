package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;


public class AddSubjectController {


    public  boolean ok=false;
    public TextField fldNameSubject;
    public Button btnCancelSubject;
    public Button btnConfirmSubject;
    private SubjectDAO daoSubject;


    public AddSubjectController() {
    }
    @FXML
    public void initialize() {

        fldNameSubject.textProperty().addListener((obs, oldIme, newIme) -> {
        if (!newIme.isEmpty()) {
            fldNameSubject.getStyleClass().removeAll("poljeNijeIspravno");
            fldNameSubject.getStyleClass().add("poljeIspravno");
        } else {
            fldNameSubject.getStyleClass().removeAll("poljeIspravno");
            fldNameSubject.getStyleClass().add("poljeNijeIspravno");
        }
    });}

    public boolean isOk(){
        return ok;
    }

    public AddSubjectController(SubjectDAO daoSubject) {
        this.daoSubject=daoSubject;
    }

    public void cancelSubject(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();

    }
    public void confirmSubject(ActionEvent actionEvent) {
        if(fldNameSubject.getStyleClass().contains("poljeIspravno") && !daoSubject.getAllSubjects().contains(new Subject(fldNameSubject.getText()))) {
            ok = true;
            daoSubject.addSubject(new Subject(fldNameSubject.getText()));
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }
        else if(fldNameSubject.getStyleClass().contains("poljeIspravno")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information dialog");
            alert.setHeaderText(null);
            alert.setContentText("This subject already exists");

            alert.showAndWait();
        }
        else {
            fldNameSubject.getStyleClass().removeAll("poljeIspravno");
            fldNameSubject.getStyleClass().add("poljeNijeIspravno");
        }

    }

}
