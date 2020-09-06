package ba.unsa.etf.rs;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class SubjectController {
    public MenuButton mbtnAddProfesor;
    public ListView profesorsOfSubject;
    public TextField fldSubjectName;
    public Button btnDeleteProfesor;
    private Subject subject;
    private TimetableDAO dao;


    @FXML
    public void initialize() {

        try {
            setProf();
            profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
            fldSubjectName.setText(subject.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        profesorsOfSubject.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            dao.setTrenutniProfesor((Profesor) newKorisnik);
        });


    }

    public SubjectController(TimetableDAO dao, Subject s) {
        this.dao = dao;
        this.subject = s;
    }

    public SubjectController() {
    }

    public void setProf() {
        try {
            mbtnAddProfesor.getItems().clear();
            ObservableList<Profesor> profesors = null;
            profesors = dao.getProfesorsForAdd(subject);

            for (int i = 0; i < profesors.size(); i++) {
                Profesor p = profesors.get(i);
                MenuItem i1 = new MenuItem(profesors.get(i).toString());
                i1.setOnAction(event -> {
                    dao.addProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName()));
                    try {
                        profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    //       mbtnDeleteProfesor.getItems().add(i1);
                    mbtnAddProfesor.getItems().remove(i1);


                });
                mbtnAddProfesor.getItems().add(i1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void btnDeleteProf(ActionEvent actionEvent) {
        if (dao.getTrenutniProfesor() != null) {
            // System.out.println("Brisanje profesora:"+profesorsOfSubject.getSelectionModel().getSelectedItem().toString()+" sa predmeta "+subject.toString());
            int idP = dao.findUserID(dao.getTrenutniProfesor().getJmbg());
            int idS = dao.findSubjectID(subject.getName());
            dao.deleteProfesorToSubject(idP, idS);
            dao.setTrenutniProfesor(null);
            setProf();
            try {
                profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nije oznacen ni jedan profesor");
        }
    }

}
