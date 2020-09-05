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
            profesors2= dao.getProfesorsForAdd(subject);
            profesorsOfSubject.setItems(profesors);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < profesors2.size(); i++) {
            Profesor p = profesors2.get(i);
            MenuItem i1 = new MenuItem(profesors2.get(i).toString());
            i1.setOnAction(event -> {
                dao.addProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName()));
                try {
                    profesorsOfSubject.setItems(dao.getProfesorsOfSubject(subject));
                   // System.out.println("DUGME ADD");
                    mbtnDeleteProfesor.getItems().add(i1);
                    mbtnAddProfesor.getItems().remove(i1);
                 //   i1.setOnAction(event2 -> { dao.deleteProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName())); });

                    //postavi();
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
                    mbtnDeleteProfesor.getItems().remove(i2);
                    mbtnAddProfesor.getItems().add(i2);
                 //   i2.setOnAction(event2 -> { dao.deleteProfesorToSubject(dao.findUserID(p.getJmbg()), dao.findSubjectID(subject.getName())); });
             //   postavi();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            mbtnDeleteProfesor.getItems().add(i2);
        }


    }



    public SubjectController(TimetableDAO dao, Subject s) {
        this.dao = dao;
        this.subject = s;
    }

    public SubjectController() {
    }


    public void deleteProfesor(ActionEvent actionEvent) {
    }
}
