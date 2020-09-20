package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.Profesor;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class SubjectController
{
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
    private  ProfessorToSubjectDAO daoDaoProfessorToSubject=ProfessorToSubjectDAO.getInstance();
    private SimpleObjectProperty<User> trenutniProfesor = new SimpleObjectProperty<>();


    public SubjectController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Subject selectedItem) {
        this.subject=selectedItem;
        this.daoClass=daoClass;
        this.daoClassroom=daoClassroom;
        this.daoProfessorToSubjectDAO=daoProfessorToSubjectDAO;
        this.daoSubject=daoSubject;
        this.daoUser=daoUser;

    }

    public SubjectController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Subject selectedItem,User user) {
        this.subject=selectedItem;
        this.daoClass=daoClass;
        this.daoClassroom=daoClassroom;
        this.daoProfessorToSubjectDAO=daoProfessorToSubjectDAO;
        this.daoSubject=daoSubject;
        this.daoUser=daoUser;
        this.user=user;
    }


    @FXML
    public void initialize()
    {
        try
        {
            profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getUsersOfSubject(subject));
            fldSubjectName.setText(subject.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        profesorsOfSubject.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniProfesor((User) newKorisnik);
        });

        setProf();
        setStud();
        if (user instanceof Profesor) mbtnAddProfesor.setDisable(true);
    }


    public SubjectController() {
    }

    public void setProf()
    {
            mbtnAddProfesor.getItems().clear();
            ObservableList<User> profesors = daoProfessorToSubjectDAO.getAllProfessorsThatAreNoProfessorsOnASubject(subject);

            for (int i = 0; i < profesors.size(); i++)
            {
                User p = profesors.get(i);
                MenuItem i1 = new MenuItem(profesors.get(i).toString());

                i1.setOnAction(event -> {
                    try
                    {
                        daoProfessorToSubjectDAO.addProfesorToSubject(daoUser.findUserID(p.getJmbg()), daoSubject.findSubjectID(subject.getName()));
                        profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getUsersOfSubject(subject));
                        mbtnAddProfesor.getItems().remove(i1);

                    }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }

                });
                mbtnAddProfesor.getItems().add(i1);
            }

    }
    public void setStud()
    {
        mbtnAddStudent.getItems().clear();

        ObservableList<User> students = daoProfessorToSubjectDAO.getAllStudentsThatAreNotOnASubject(subject);
        for (int i = 0; i < students.size(); i++)
        {
            User p = students.get(i);
            MenuItem i1 = new MenuItem(students.get(i).toString());

            i1.setOnAction(event -> {
                try
                {
                    daoProfessorToSubjectDAO.addProfesorToSubject(daoUser.findUserID(p.getJmbg()), daoSubject.findSubjectID(subject.getName()));
                    profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getUsersOfSubject(subject));
                    mbtnAddStudent.getItems().remove(i1);
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            });
            mbtnAddStudent.getItems().add(i1);
        }
    }


    public void btnDeleteProf(ActionEvent actionEvent)
    {
        if (getTrenutniProfesor() != null)
        {
            if(getTrenutniProfesor() instanceof Profesor && user instanceof Profesor){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("You dont have permission to delete profesors of this subject!.");
                alert.showAndWait();

            }
            else{

                int idP = daoUser.findUserID(getTrenutniProfesor().getJmbg());
                int idS = daoSubject.findSubjectID(subject.getName());
                daoProfessorToSubjectDAO.deleteProfesorToSubject(idP, idS);
                setTrenutniProfesor(null);
            }

            try
            {
                profesorsOfSubject.setItems(daoProfessorToSubjectDAO.getUsersOfSubject(subject));
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You have to select a user in order to delete it.");
            alert.showAndWait();
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
