package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.*;
import ba.unsa.etf.rs.model.Class;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class ClassController {
    public ListView<Class> listViewClass;
    public Spinner<Integer> spStart;
    public Spinner<Integer> spPeriod;
    public ChoiceBox<Subject> choiceSubject;
    public ChoiceBox<Classroom> choiceClassroom;
    public ChoiceBox<Class.Type> choiceType;
    public Button btnClassAdd;
    public Button btnClassEdit;
    public Button btnClassDelete;
    public Button btnCancelClass;
    public Button btnConfirmClass;
    public Label msgStatus;
    public Label lbPeriod;


    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;
    private Classroom classroom;
    private Subject subject;
    private Date date;
    private User user=null;

    private SimpleObjectProperty<Class> trenutniClass = new SimpleObjectProperty<>();
    Calendar cal = Calendar.getInstance();

    public ClassController() {
        this.daoClass = ClassDAO.getInstance();
        this.daoClassroom = ClassroomDAO.getInstance();
        this.daoProfessorToSubjectDAO = ProfessorToSubjectDAO.getInstance();
        this.daoSubject = SubjectDAO.getInstance();
        this.daoUser = UserDAO.getInstance();
        this.classroom = new Classroom("0-1",10);
        this.subject = new Subject("UUP");
        this.date = Date.valueOf(LocalDate.now());
        this.user=daoUser.getAllUsers().get(0);
    }

    public ClassController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Classroom classroom, Subject subject, Date selectedItem, User user) {
        this.daoClass = daoClass;
        this.daoClassroom = daoClassroom;
        this.daoProfessorToSubjectDAO = daoProfessorToSubjectDAO;
        this.daoSubject = daoSubject;
        this.daoUser = daoUser;
        this.classroom = classroom;
        this.subject = subject;
        this.date = selectedItem;
        this.user=user;
    }



    @FXML
    public void initialize() throws SQLException {
        if (user instanceof Profesor){
            choiceSubject.setItems(daoProfessorToSubjectDAO.getSubjectOfProfesor(daoUser.findUserID2(user.getJmbg())));
        }
        else if   (user instanceof Student){
            btnClassAdd.setDisable(true);
            btnClassDelete.setDisable(true);
            btnClassEdit.setDisable(true);
       }
           else if (user instanceof Admin) System.out.println("Logovao se admin");
spPeriod.setVisible(false);
        lbPeriod.setVisible(false);
    if(date!=null && classroom!=null && subject!=null)   { ObservableList<Class> result = daoClass.initializeClass(date, classroom, subject);
        listViewClass.setItems(result);}
        choiceSubject.setItems(daoSubject.getAllSubjects());
        choiceClassroom.setItems(daoClassroom.getAllClassrooms());

        choiceType.getItems().setAll(Class.Type.values());
        disable();
        listViewClass.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniClass((Class) newKorisnik);
        });

        trenutniClassProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                spStart.getValueFactory().setValue(oldKorisnik.getStart());
                spPeriod.getValueFactory().setValue(oldKorisnik.getPeriod());
                choiceSubject.setValue(oldKorisnik.getSubject());
                choiceClassroom.setValue(oldKorisnik.getClassroom());
                choiceType.setValue(oldKorisnik.getType());


            }
            if (newKorisnik == null) {
                spStart.getValueFactory().setValue(8);
                spPeriod.getValueFactory().setValue(0);
                choiceSubject.getSelectionModel().clearSelection();
                choiceClassroom.getSelectionModel().clearSelection();
                choiceType.getSelectionModel().clearSelection();

            } else {
                spStart.getValueFactory().setValue(newKorisnik.getStart());
                spPeriod.getValueFactory().setValue(newKorisnik.getPeriod());
                choiceSubject.setValue(newKorisnik.getSubject());
                choiceClassroom.setValue(newKorisnik.getClassroom());
                choiceType.setValue(newKorisnik.getType());
            }

        });
    }

    public void addClass(ActionEvent actionEvent) {
        spPeriod.setVisible(true);
        lbPeriod.setVisible(true);
        enable();
        btnClassDelete.setDisable(true);
        btnClassEdit.setDisable(true);
        spStart.getValueFactory().setValue(8);
        spPeriod.getValueFactory().setValue(0);
        choiceSubject.getSelectionModel().clearSelection();
        choiceClassroom.getSelectionModel().clearSelection();
        choiceType.getSelectionModel().clearSelection();
        setTrenutniClass(null);

    }

    public void editClass(ActionEvent actionEvent) {
        enable();
        btnClassDelete.setDisable(true);
        btnClassAdd.setDisable(true);
    }

    public void deleteClass(ActionEvent actionEvent) {
        disable();
        btnConfirmClass.setDisable(false);
        btnCancelClass.setDisable(false);
        btnClassEdit.setDisable(true);
        btnClassAdd.setDisable(true);
    }

    public void cancelClass(ActionEvent actionEvent) {
        if (getTrenutniClass() != null && !btnClassDelete.isDisable()) {

            msgStatus.setText("Class not deleted.");

        } else if (!btnClassEdit.isDisable()) {
            msgStatus.setText("Class not edited.");
        } else if (!btnClassAdd.isDisable()) {
            spPeriod.setVisible(false);
            lbPeriod.setVisible(false);
            msgStatus.setText("Class not added.");
        }

        disable();
        btnClassEdit.setDisable(false);
        btnClassAdd.setDisable(false);
        btnClassDelete.setDisable(false);

    }

    public void confirmClass(ActionEvent actionEvent) {
        if (getTrenutniClass() != null && !btnClassDelete.isDisable()) {
            disable();
            btnClassAdd.setDisable(false);
            btnClassEdit.setDisable(false);
            daoClass.deleteClass(getTrenutniClass().getId());

            listViewClass.setItems(daoClass.initializeClass(date, classroom, subject));
            msgStatus.setText("Class deleted.");

        } else if (getTrenutniClass() == null && !btnClassDelete.isDisable()) {
            System.out.println("Nije oznacen ni jedan cas za brisanje");
        } else if (getTrenutniClass() != null && !btnClassEdit.isDisable()) {
            daoClass.UpdateClass(new Class(spStart.getValue(), spStart.getValue() + 1, spPeriod.getValue(), classroom, subject, choiceType.getValue(), date),getTrenutniClass().getId());
            disable();
            btnClassAdd.setDisable(false);
            btnClassDelete.setDisable(false);

        } else if (!btnClassAdd.isDisable() ) {
            msgStatus.setText("Class added.");
            btnClassDelete.setDisable(false);
            btnClassEdit.setDisable(false);
            spPeriod.setVisible(false);
            lbPeriod.setVisible(false);
            disable();
            int i=0;
            cal.setTime(date);
            for (i=0;i<=spPeriod.getValue();i++){
                LocalDate today = LocalDate.now();
                today = today.plus(i, ChronoUnit.WEEKS);
                if(choiceSubject.getSelectionModel().getSelectedItem()==null || choiceType.getSelectionModel().getSelectedItem()==null || choiceClassroom.getSelectionModel().getSelectedItem()==null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Some fields are empty, please try again!");
                    alert.showAndWait();
                    msgStatus.setText("Class not added.");
                }
               else if(daoClass.isClassFree(Date.valueOf(today),choiceClassroom.getValue(),choiceSubject.getValue(),spStart.getValue())) {
                    daoClass.addClass(new Class(spStart.getValue(), spStart.getValue() + 1, spPeriod.getValue(), choiceClassroom.getValue(), choiceSubject.getValue(), choiceType.getValue(), Date.valueOf(today)));

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("This classrom is busy on "+today+" at "+spStart.getValue()+":00");
                    alert.showAndWait();
                    msgStatus.setText("Class not added.");

                }
            }

        }

        listViewClass.setItems(daoClass.initializeClass(date, classroom, subject));
    }

    public void enable() {
        listViewClass.setDisable(true);
        spStart.setDisable(false);
        spPeriod.setDisable(false);
        choiceSubject.setDisable(false);
        choiceClassroom.setDisable(false);
        choiceType.setDisable(false);
        btnCancelClass.setDisable(false);
        btnConfirmClass.setDisable(false);

    }

    public void disable() {
        listViewClass.setDisable(false);
        spStart.setDisable(true);
        spPeriod.setDisable(true);
        choiceSubject.setDisable(true);
        choiceClassroom.setDisable(true);
        choiceType.setDisable(true);
        btnCancelClass.setDisable(true);
        btnConfirmClass.setDisable(true);

    }


    public Class getTrenutniClass() {
        return trenutniClass.get();
    }

    public SimpleObjectProperty<Class> trenutniClassProperty() {
        return trenutniClass;
    }

    public void setTrenutniClass(Class trenutniClass) {
        this.trenutniClass.set(trenutniClass);
    }



}

