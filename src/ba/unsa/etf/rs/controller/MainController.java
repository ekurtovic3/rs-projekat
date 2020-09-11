package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.w3c.dom.UserDataHandler;

import java.io.IOException;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {

    public Label statusMsg;
    public ListView listViewSubjects;
    public ListView listViewUsers;
    public ListView listViewClassroom;
    public TextField fldName;
    public TextField fldSurname;
    public TextField fldEmail;
    public TextField fldUsername;
    public TextField fldJmbg;
    public DatePicker dpBirthday;
    public PasswordField fldPassword;
    public RadioButton radioStudent;
    public RadioButton radioProfesor;
    public RadioButton radioAllUsers;
    public Button btnCancelUser;
    public Button btnConfirmUser;
    public Button btnUserAdd;
    public Button btnUserEdit;
    public Button btnUserDelete;

    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;
    private ObservableList<Subject> listSubjects;
    private ObservableList<User> listUsers;
    private ObservableList<Classroom> listClassrooms;
    private Object SubjectController;
    private User user = null;

    private SimpleObjectProperty<User> trenutniKorisnik = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Subject> trenutniSubject = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Classroom> trenutniClassroom = new SimpleObjectProperty<>();

    private Object addSubjectController;
    private Object addClassroomController;
    private Object CalendarController;


    public MainController() throws SQLException {

        daoClass = ClassDAO.getInstance();
        daoClassroom = ClassroomDAO.getInstance();
        daoProfessorToSubjectDAO = ProfessorToSubjectDAO.getInstance();
        daoSubject = SubjectDAO.getInstance();
        daoUser = UserDAO.getInstance();

    }

    public MainController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Student student) {
        this.daoClass = daoClass;
        this.daoClassroom = daoClassroom;
        this.daoProfessorToSubjectDAO = daoProfessorToSubjectDAO;
        this.daoSubject = daoSubject;
        this.daoUser = daoUser;
        this.user = student;
    }

    public MainController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Profesor profesor) {
        this.daoClass = daoClass;
        this.daoClassroom = daoClassroom;
        this.daoProfessorToSubjectDAO = daoProfessorToSubjectDAO;
        this.daoSubject = daoSubject;
        this.daoUser = daoUser;
        this.user = profesor;
    }

    public MainController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, User userByID) {
        this.daoClass = daoClass;
        this.daoClassroom = daoClassroom;
        this.daoProfessorToSubjectDAO = daoProfessorToSubjectDAO;
        this.daoSubject = daoSubject;
        this.daoUser = daoUser;
        this.user = userByID;
    }

    @FXML
    public void initialize() {
        statusMsg.setText("Program started...");
        if (user instanceof Student) System.out.println("Logovan kao student");
        if (user instanceof Profesor) System.out.println("Logovan kao profesor");
        if (user instanceof Admin) System.out.println("Logovan kao admin");
        btnCancelUser.setDisable(true);
        btnConfirmUser.setDisable(true);
        listViewSubjects.setItems(daoSubject.getAllSubjects());
        listViewUsers.setItems(daoUser.getAllUsers());
        radioAllUsers.setSelected(true);
        listViewClassroom.setItems(daoClassroom.getAllClassrooms());
        disable();
        listViewUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniKorisnik((User) newKorisnik);
        });
        listViewSubjects.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniSubject((Subject) newKorisnik);
        });
        listViewClassroom.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            setTrenutniClassroom((Classroom) newKorisnik);
        });
        trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldName.textProperty().unbindBidirectional(oldKorisnik.nameProperty());
                fldSurname.textProperty().unbindBidirectional(oldKorisnik.surnameProperty());
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty());
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty());
                fldJmbg.textProperty().unbindBidirectional(oldKorisnik.jmbgProperty());
                dpBirthday.valueProperty().unbindBidirectional(oldKorisnik.dateOfBirthProperty());
                if (getTrenutniKorisnik() instanceof Student) radioStudent.setSelected(true);
                if (getTrenutniKorisnik() instanceof Profesor) radioProfesor.setSelected(true);

            }
            if (newKorisnik == null) {
                fldName.setText("");
                fldSurname.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldJmbg.setText("");
                dpBirthday.setValue(LocalDate.now());
            } else {
                fldName.textProperty().bindBidirectional(newKorisnik.nameProperty());
                fldSurname.textProperty().bindBidirectional(newKorisnik.surnameProperty());
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty());
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty());
                fldJmbg.textProperty().bindBidirectional(newKorisnik.jmbgProperty());
                dpBirthday.valueProperty().bindBidirectional(newKorisnik.dateOfBirthProperty());
                if (getTrenutniKorisnik() instanceof Student) radioStudent.setSelected(true);
                if (getTrenutniKorisnik() instanceof Profesor) radioProfesor.setSelected(true);

            }
        });
        fldName.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldName.getStyleClass().removeAll("poljeNijeIspravno");
                fldName.getStyleClass().add("poljeIspravno");
            } else {
                fldName.getStyleClass().removeAll("poljeIspravno");
                fldName.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldSurname.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldSurname.getStyleClass().removeAll("poljeNijeIspravno");
                fldSurname.getStyleClass().add("poljeIspravno");
            } else {
                fldSurname.getStyleClass().removeAll("poljeIspravno");
                fldSurname.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldEmail.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                if (newIme.contains(".com") && newIme.contains("@") && newIme.length() >= 3) {
                    if (Character.isLetter(newIme.charAt(0)) && Character.isLetter(newIme.charAt(newIme.length() - 1))) {
                        fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                        fldEmail.getStyleClass().add("poljeIspravno");
                    } else {
                        fldEmail.getStyleClass().removeAll("poljeIspravno");
                        fldEmail.getStyleClass().add("poljeNijeIspravno");
                    }
                } else {
                    fldEmail.getStyleClass().removeAll("poljeIspravno");
                    fldEmail.getStyleClass().add("poljeNijeIspravno");
                }
            } else {
                fldEmail.getStyleClass().removeAll("poljeIspravno");
                fldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });


        fldUsername.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                fldUsername.getStyleClass().add("poljeIspravno");
            } else {
                fldUsername.getStyleClass().removeAll("poljeIspravno");
                fldUsername.getStyleClass().add("poljeNijeIspravno");
            }
        });

        fldJmbg.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (isJmbgValid(n)) {
                    fldJmbg.getStyleClass().removeAll("poljeNijeIspravno");
                    fldJmbg.getStyleClass().add("poljeIspravno");
                } else {
                    fldJmbg.getStyleClass().removeAll("poljeIspravno");
                    fldJmbg.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });
        dpBirthday.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate o, LocalDate n) {
                if (isDateValid(n)) {
                    dpBirthday.getStyleClass().removeAll("poljeNijeIspravno");
                    dpBirthday.getStyleClass().add("poljeIspravno");
                } else {
                    dpBirthday.getStyleClass().removeAll("poljeIspravno");
                    dpBirthday.getStyleClass().add("poljeNijeIspravno");
                }
                if (isJmbgValid(fldJmbg.getText())) {
                    fldJmbg.getStyleClass().removeAll("poljeNijeIspravno");
                    fldJmbg.getStyleClass().add("poljeIspravno");
                } else {
                    fldJmbg.getStyleClass().removeAll("poljeIspravno");
                    fldJmbg.getStyleClass().add("poljeNijeIspravno");
                }
            }


        });


        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() || newIme.length() < 3) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }


    private boolean isJmbgValid(String s) {
        if (s.length() != 13) return false;
        try {
            int dd = Integer.parseInt(fldJmbg.getText(0, 2));
            int mm = Integer.parseInt(fldJmbg.getText(2, 4));
            int ggg = Integer.parseInt(fldJmbg.getText(4, 7));
            if (mm < 1 || mm > 12) return false;
            if (dd < 1 || dd > YearMonth.of(ggg, mm).lengthOfMonth()) return false;
            int rr = Integer.parseInt(fldJmbg.getText(7, 9));
            int bbb = Integer.parseInt(fldJmbg.getText(9, 12));
            int k = Integer.parseInt(fldJmbg.getText(12, 13));
            int l = 11 - ((7 * (Integer.parseInt(fldJmbg.getText(0, 1)) + Integer.parseInt(fldJmbg.getText(6, 7))) +
                    6 * (Integer.parseInt(fldJmbg.getText(1, 2)) + Integer.parseInt(fldJmbg.getText(7, 8))) +
                    5 * (Integer.parseInt(fldJmbg.getText(2, 3)) + Integer.parseInt(fldJmbg.getText(8, 9))) +
                    4 * (Integer.parseInt(fldJmbg.getText(3, 4)) + Integer.parseInt(fldJmbg.getText(9, 10))) +
                    3 * (Integer.parseInt(fldJmbg.getText(4, 5)) + Integer.parseInt(fldJmbg.getText(10, 11))) +
                    2 * (Integer.parseInt(fldJmbg.getText(5, 6)) + Integer.parseInt(fldJmbg.getText(11, 12)))) % 11);
            if (l >= 1 && l <= 9 && l != k) return false;
            if (l > 9 && k != 0) return false;
            //unakrsno sa datumom
            if (ggg != dpBirthday.getValue().getYear() % 1000) return false;
            if (mm != dpBirthday.getValue().getMonthValue()) return false;
            if (dd != dpBirthday.getValue().getDayOfMonth()) return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isDateValid(LocalDate s) {
        if (s == null) return false;
        LocalDate trenutni = LocalDate.now();
        if (s.isAfter(trenutni)) return false;
        return true;
    }

    private boolean style(TextField polje) {
        for (String s : polje.getStyleClass())
            if (s.equals("poljeIspravno")) return true;
        return false;
    }

    private boolean style(DatePicker polje) {
        for (String s : polje.getStyleClass())
            if (s.equals("poljeIspravno")) return true;
        return false;
    }

    private boolean isValidAll() {
        if (style(fldName) && style(fldSurname) && style(fldJmbg) && style(fldEmail) && style(fldUsername) && style(dpBirthday) && (radioProfesor.isSelected() || radioStudent.isSelected()))
            return true;
        return false;
    }

    private void enable() {
        fldName.setDisable(false);
        fldSurname.setDisable(false);
        fldEmail.setDisable(false);
        fldUsername.setDisable(false);
        fldJmbg.setDisable(false);
        dpBirthday.setDisable(false);
        fldPassword.setDisable(false);
        radioProfesor.setDisable(false);
        radioStudent.setDisable(false);
        listViewUsers.setDisable(true);
    }


    private void disable() {
        fldName.setDisable(true);
        fldSurname.setDisable(true);
        fldEmail.setDisable(true);
        fldUsername.setDisable(true);
        fldJmbg.setDisable(true);
        dpBirthday.setDisable(true);
        fldPassword.setDisable(true);
        radioProfesor.setDisable(true);
        radioStudent.setDisable(true);
        listViewUsers.setDisable(false);
    }


    public void addSubject(ActionEvent actionEvent) {
        try {
            Parent root = null;
            Stage myStage = new Stage();
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/addSubject.fxml"));
            loader3.setController(new AddSubjectController(daoSubject));
            root = loader3.load();
            addSubjectController = loader3.getController();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();
            int s = daoSubject.getAllSubjects().size();
            myStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    listViewSubjects.setItems(daoSubject.getAllSubjects());
                    if (s != daoSubject.getAllSubjects().size()) {
                        statusMsg.setText("Subject added.");
                    } else {
                        statusMsg.setText("Subject not added.");
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnSubjectChange(ActionEvent actionEvent) {

        if (!listViewSubjects.getSelectionModel().isEmpty()) {
            Parent root = null;
            try {
                Stage myStage = new Stage();
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/subject.fxml"));
                loader2.setController(new SubjectController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, (Subject) listViewSubjects.getSelectionModel().getSelectedItem()));
                root = loader2.load();
                SubjectController = loader2.getController();
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();
                myStage.setOnHidden(event -> {
                    listViewSubjects.setItems(daoSubject.getAllSubjects());
                    statusMsg.setText("Subject views/edited");
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteSubject(ActionEvent actionEvent) {
        if (getTrenutniSubject() != null) {
            Subject pom = (Subject) listViewSubjects.getSelectionModel().getSelectedItem();
            daoSubject.deleteSubject(((Subject) listViewSubjects.getSelectionModel().getSelectedItem()).getName());
            listViewSubjects.setItems(daoSubject.getAllSubjects());
            statusMsg.setText("Subject deleted.");

        }
    }

    public void AllUsers(ActionEvent actionEvent) {
        listViewUsers.setItems(daoUser.getAllUsers());
    }

    public void Students(ActionEvent actionEvent) {
        listViewUsers.setItems(daoUser.getAllSpecificUsers(1));

    }


    public void Profesors(ActionEvent actionEvent) {
        listViewUsers.setItems(daoUser.getAllSpecificUsers(2));
    }


    public void cancelUser(ActionEvent actionEvent) {

        if (getTrenutniKorisnik() != null && !btnUserDelete.isDisable()) {

            statusMsg.setText("User not deleted.");

        } else if (!btnUserEdit.isDisable()) {
            statusMsg.setText("User not edited.");
        } else if (!btnUserAdd.isDisable()) {
            statusMsg.setText("User not added.");
        }

        btnUserAdd.setDisable(false);
        btnUserDelete.setDisable(false);
        btnUserEdit.setDisable(false);
        setTrenutniKorisnik(null);
        btnCancelUser.setDisable(true);
        btnConfirmUser.setDisable(true);
        fldName.setText("");
        fldSurname.setText("");
        fldEmail.setText("");
        fldUsername.setText("");
        fldJmbg.setText("");
        fldPassword.setText("");
        dpBirthday.setValue(LocalDate.now());
        disable();
    }

    public void confirmUser(ActionEvent actionEvent) {
        if (getTrenutniKorisnik() != null && !btnUserDelete.isDisable()) {
            btnCancelUser.setDisable(true);
            btnConfirmUser.setDisable(true);
            btnUserAdd.setDisable(false);
            btnUserDelete.setDisable(false);
            btnUserEdit.setDisable(false);
            listViewUsers.setDisable(false);
            disable();
            daoUser.deleteUser(getTrenutniKorisnik().getJmbg());
            listViewUsers.setItems(daoUser.getAllUsers());
            statusMsg.setText("User deleted.");

        } else if (getTrenutniKorisnik() == null && !btnUserDelete.isDisable()) {
            System.out.println("Nije oznacen ni jedan korisnik za brisanje");
        } else if (isValidAll() && getTrenutniKorisnik() != null && !btnUserEdit.isDisable()) {
            if (radioProfesor.isSelected()) {
                daoUser.UpdateUser(new Profesor(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), fldPassword.getText());
                statusMsg.setText("User edited.");

            } else if (radioStudent.isSelected()) {
                daoUser.UpdateUser(new Student(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), fldPassword.getText());
                statusMsg.setText("User deleted.");
            }
        } else if (isValidAll() && !btnUserAdd.isDisable()) {
            if (radioProfesor.isSelected()) {
                daoUser.addUser(new Profesor(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), "Neki pass");
                statusMsg.setText("User added.");
            } else if (radioStudent.isSelected()) {
                statusMsg.setText("User added.");
                daoUser.addUser(new Student(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), "Neki pass");
            }


        } else if (!isValidAll() && (!btnUserEdit.isDisable() || !btnUserAdd.isDisable())) {
            System.out.println("Nije validno sve ili ima polje koje nije popunjeno");
        }
        if (isValidAll() && (!btnUserEdit.isDisabled() || !btnUserAdd.isDisabled())) {
            btnCancelUser.setDisable(true);
            btnConfirmUser.setDisable(true);
            btnUserAdd.setDisable(false);
            btnUserDelete.setDisable(false);
            btnUserEdit.setDisable(false);
            listViewUsers.setDisable(false);
            disable();

        }
        listViewUsers.setItems(daoUser.getAllUsers());
    }

    public void btnUserAdd(ActionEvent actionEvent) {
        enable();
        // btnUserAdd.setDisable(true);
        btnUserDelete.setDisable(true);
        btnUserEdit.setDisable(true);
        setTrenutniKorisnik(null);
        btnCancelUser.setDisable(false);
        btnConfirmUser.setDisable(false);
        listViewSubjects.setSelectionModel(null);
        fldName.setText("");
        fldSurname.setText("");
        fldEmail.setText("");
        fldUsername.setText("");
        fldJmbg.setText("");
        dpBirthday.setValue(LocalDate.now());
        statusMsg.setText("Adding new user.");

    }

    public void btnUserEdit(ActionEvent actionEvent) {
        btnCancelUser.setDisable(false);
        btnConfirmUser.setDisable(false);
        btnUserAdd.setDisable(true);
        btnUserDelete.setDisable(true);
        enable();
        if (getTrenutniKorisnik() == null) {
            System.out.println("ERROR nije izabran ni jedan korisnik");
            disable();
        }
        statusMsg.setText("Editing user.");
    }

    public void btnUserDelete(ActionEvent actionEvent) {
        btnUserAdd.setDisable(true);
        btnUserEdit.setDisable(true);
        btnConfirmUser.setDisable(false);
        btnCancelUser.setDisable(false);
        statusMsg.setText("Deliting user.");
    }

    public void addClassroom(ActionEvent actionEvent) {
        try {
            Parent root = null;
            Stage myStage = new Stage();
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/classroom.fxml"));
            loader3.setController(new addClassroomController(daoClassroom));
            root = loader3.load();
            addClassroomController = loader3.getController();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();
            int s = daoClassroom.getAllClassrooms().size();
            myStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    listViewClassroom.setItems(daoClassroom.getAllClassrooms());
                    if (s != daoClassroom.getAllClassrooms().size()) {
                        statusMsg.setText("Classroom added.");
                    } else {
                        statusMsg.setText("Classroom not added.");
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editClassroom(ActionEvent actionEvent) {
        try {
            Parent root = null;
            Stage myStage = new Stage();
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/calendar.fxml"));
            loader3.setController(new CalendarController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, (Classroom) listViewClassroom.getSelectionModel().getSelectedItem()));
            root = loader3.load();
            CalendarController = loader3.getController();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();
            int s = daoClassroom.getAllClassrooms().size();
            myStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    listViewClassroom.setItems(daoClassroom.getAllClassrooms());
                    if (s != daoClassroom.getAllClassrooms().size()) {
                        statusMsg.setText("Classroom viewd/edited");
                    } else {
                        statusMsg.setText("Classroom not viewd/edited.");
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteClassrom(ActionEvent actionEvent) {
        if (getTrenutniClassroom() != null) {
            Classroom pom = (Classroom) listViewClassroom.getSelectionModel().getSelectedItem();
            daoClassroom.deleteClassroom(((Classroom) listViewClassroom.getSelectionModel().getSelectedItem()).getName());
            listViewClassroom.setItems(daoClassroom.getAllClassrooms());
            statusMsg.setText("Classroom deleted.");

        }
    }

    public User getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<User> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(User trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }


    public Subject getTrenutniSubject() {
        return trenutniSubject.get();
    }

    public SimpleObjectProperty<Subject> trenutniSubjectProperty() {
        return trenutniSubject;
    }

    public void setTrenutniSubject(Subject trenutniSubject) {
        this.trenutniSubject.set(trenutniSubject);
    }

    public Classroom getTrenutniClassroom() {
        return trenutniClassroom.get();
    }

    public SimpleObjectProperty<Classroom> trenutniClassroomProperty() {
        return trenutniClassroom;
    }

    public void setTrenutniClassroom(Classroom trenutniClassroom) {
        this.trenutniClassroom.set(trenutniClassroom);
    }
}
