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
    public MenuButton mbtnDeleteProfesor;
    public ListView profesorsOfSubject;
    public TextField fldSubjectName;
    private Subject subject;
    private TimetableDAO dao;

    @FXML
    public void initialize() {
        mbtnAddProfesor.getItems().clear();
        mbtnDeleteProfesor.getItems().clear();
        fldSubjectName.setText(subject.toString());
        ObservableList<Profesor> profesors = null;
        ObservableList<Profesor> profesors2 = null;
        try {
            profesors = dao.getProfesorsOfSubject(subject);
           // profesors2= dao.getProfesorsForAdd(subject);
            profesorsOfSubject.setItems(profesors);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            });
            mbtnAddProfesor.getItems().add(i1);
        }

        for (int i = 0; i < profesors.size(); i++) {
            User p = profesors.get(i);
            MenuItem i2 = new MenuItem(profesors.get(i).toString());
            i2.setOnAction(event -> {
                dao.deleteProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName()));
                try {
                    profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            mbtnDeleteProfesor.getItems().add(i2);
        }


    }
    /*
    public void postavi() throws SQLException {
        mbtnDeleteProfesor.getItems().clear();
        ObservableList<Profesor> profesors3 = null;
        profesors3 = dao.getProfesorsOfSubject(subject);
       if(profesors3!=null){ for (int i = 0; i < profesors3.size(); i++) {

            User p = profesors3.get(i);
            MenuItem i2 = new MenuItem(profesors3.get(i).toString());
                dao.deleteProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName()));
                try {
                    profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            mbtnDeleteProfesor.getItems().add(i2);
        }}
       // profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
    }*/

    public SubjectController(TimetableDAO dao, Subject s) {
        this.dao = dao;
        this.subject = s;
    }

    public SubjectController() {
    }


    public void deleteProfesor(ActionEvent actionEvent) {
    }
}
