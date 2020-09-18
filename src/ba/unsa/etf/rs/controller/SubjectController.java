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
    public MenuButton mbtnAddStudent;
    public ListView<User> profesorsOfSubject;
    public Label fldSubjectName;
    public Button btnDeleteProfesor;

    private Subject subject;
    private User user;
    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;
    private SimpleObjectProperty<User> trenutniProfesor = new SimpleObjectProperty<>();

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
            setTrenutniProfesor((User) newKorisnik); });

        setProf();
        setStud();
    }


    public SubjectController() {
    }

    public void setProf() {

            mbtnAddProfesor.getItems().clear();
            ObservableList<User> profesors = daoUser.getAllSpecificUsers(2);
            for (int i = 0; i < profesors.size(); i++) {
                User p = profesors.get(i);
                MenuItem i1 = new MenuItem(profesors.get(i).toString());
                i1.setOnAction(event -> {
                    try {
                        daoProfessorToSubjectDAO.addProfesorToSubject(daoUser.findUserID(p.getJmbg()), daoSubject.findSubjectID(subject.getName()));
                        profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getProfesorsOfSubject(subject));
                        mbtnAddProfesor.getItems().remove(i1);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });
                    if(!profesorsOfSubject.getItems().contains(p)) {
                        System.out.println("OVOG nema "+p);
                    mbtnAddProfesor.getItems().add(i1);}

            }

    }
    public void setStud() {
        mbtnAddStudent.getItems().clear();

        ObservableList<User> students = daoUser.getAllSpecificUsers(1);
        for (int i = 0; i < students.size(); i++) {
            User p = students.get(i);
            MenuItem i1 = new MenuItem(students.get(i).toString());
            i1.setOnAction(event -> {
                try {
                    daoProfessorToSubjectDAO.addProfesorToSubject(daoUser.findUserID(p.getJmbg()), daoSubject.findSubjectID(subject.getName()));
                    profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getProfesorsOfSubject(subject));
                    mbtnAddStudent.getItems().remove(i1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            try {
                if(!daoProfessorToSubjectDAO.getProfesorsOfSubject(subject).contains(p)) {
                    mbtnAddStudent.getItems().add(i1);}
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }

    }


    public void btnDeleteProf(ActionEvent actionEvent) {
        if (getTrenutniProfesor() != null) {
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
        setProf();
        setStud();
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
