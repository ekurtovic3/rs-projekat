package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.SubjectDAO;
import ba.unsa.etf.rs.model.Subject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;


public class AddSubjectController {


    public  boolean ok=false;
    public TextField fldNameSubject;
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
        if(fldNameSubject.getStyleClass().contains("poljeIspravno")) {
            ok = true;
            daoSubject.addSubject(new Subject(fldNameSubject.getText()));
            Node n = (Node) actionEvent.getSource();
            Stage stage = (Stage) n.getScene().getWindow();
            stage.close();
        }

    }

}
