package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.Profesor;
import ba.unsa.etf.rs.model.Student;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import javafx.beans.property.SimpleObjectProperty;
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
   // private TimetableDAO dao;
    private User user;
    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;
    private SimpleObjectProperty<User> trenutniProfesor = new SimpleObjectProperty<>();

    /*

    public SubjectController(TimetableDAO dao, Student student) {
        this.dao=dao;
        this.user=student;
    }*/

    public SubjectController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Subject selectedItem) {
        this.subject=selectedItem;
        this.daoClass=daoClass;
        this.daoClassroom=daoClassroom;
        this.daoProfessorToSubjectDAO=daoProfessorToSubjectDAO;
        this.daoSubject=daoSubject;
        this.daoUser=daoUser;
    }


    @FXML
    public void initialize() {
        try {
            profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getProfesorsOfSubject(subject));
            fldSubjectName.setText(subject.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        profesorsOfSubject.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniProfesor((Profesor) newKorisnik);
        });
         setProf();
    }
/*
    public SubjectController(TimetableDAO dao, Subject s) {
        this.dao = dao;
        this.subject = s;
    }*/

    public SubjectController() {
    }

    public void setProf() {
        try {
            mbtnAddProfesor.getItems().clear();
            ObservableList<Profesor> profesors = null;
            profesors = daoProfessorToSubjectDAO.getProfesorsForAdd(subject);

            for (int i = 0; i < profesors.size(); i++) {
                Profesor p = profesors.get(i);
                MenuItem i1 = new MenuItem(profesors.get(i).toString());
                i1.setOnAction(event -> {
                    daoProfessorToSubjectDAO.addProfesorToSubject(daoUser.findUserID(p.getJmbg()), daoSubject.findSubjectID(subject.getName()));
                    try {
                        profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getProfesorsOfSubject(subject));
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
        if (getTrenutniProfesor() != null) {
            // System.out.println("Brisanje profesora:"+profesorsOfSubject.getSelectionModel().getSelectedItem().toString()+" sa predmeta "+subject.toString());
            int idP = daoUser.findUserID(getTrenutniProfesor().getJmbg());
            int idS = daoSubject.findSubjectID(subject.getName());
            daoProfessorToSubjectDAO.deleteProfesorToSubject(idP, idS);
            setTrenutniProfesor(null);
            setProf();
            try {
                profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getProfesorsOfSubject(subject));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Nije oznacen ni jedan profesor");
        }
    }

    public User getTrenutniProfesor() {
        return trenutniProfesor.get();
    }

    public SimpleObjectProperty<User> trenutniProfesorProperty() {
        return trenutniProfesor;
    }

    public void setTrenutniProfesor(User trenutniProfesor) {
        this.trenutniProfesor.set(trenutniProfesor);
    }
}
