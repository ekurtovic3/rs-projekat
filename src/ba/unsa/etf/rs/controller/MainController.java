package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.exceptions.EmptyField;
import ba.unsa.etf.rs.exceptions.InvalidParam;
import ba.unsa.etf.rs.exceptions.ObjectAlredyExist;
import ba.unsa.etf.rs.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Scanner;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class MainController {

    public Label statusMsg;
    public Label lbYear;
    public Menu menuLogOut;
    public ListView listViewSubjects;
    public ListView listViewUsers;
    public ListView listViewClassroom;
    public ListView listViewCalendar;
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
    public ChoiceBox cbSubject;
    public ChoiceBox cbClassroom;
    public Tab timetable;
    public Button  btnSreach;
    public Tab tbUsers;
    public Tab tbClassrooms;
    public Tab tbSubjects;
    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;
    private String pw;

    private ObservableList<Subject> listSubjects;
    private ObservableList<User> listUsers;
    private ObservableList<Classroom> listClassrooms;
    private Object SubjectController;
    private User user = null;
    //int tip usera

    private SimpleObjectProperty<User> trenutniKorisnik = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Subject> trenutniSubject = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Classroom> trenutniClassroom = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> trenutniDate = new SimpleObjectProperty<>();

    private Object addSubjectController;
    private Object addClassroomController;
    private Object ClassController;

    int day = LocalDate.now().getDayOfYear();

    private int year = 2020;
    private Object StartScreenController;


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
    public MainController(ClassDAO daoClass, ClassroomDAO daoClassroom, ProfessorToSubjectDAO daoProfessorToSubjectDAO, SubjectDAO daoSubject, UserDAO daoUser, Admin admin) {
        this.daoClass = daoClass;
        this.daoClassroom = daoClassroom;
        this.daoProfessorToSubjectDAO = daoProfessorToSubjectDAO;
        this.daoSubject = daoSubject;
        this.daoUser = daoUser;
        this.user = admin;

    }

    @FXML
    public void initialize() {


        cbSubject.setItems(daoSubject.getAllSubjects());
        cbClassroom.setItems(daoClassroom.getAllClassrooms());

       // timetable.setDisable(true);
        statusMsg.setText("Program started...");
        if (user instanceof Student) {
            tbClassrooms.setDisable(true);
            tbSubjects.setDisable(true);
            tbUsers.setDisable(true);
        }
        if (user instanceof Profesor){
            tbClassrooms.setDisable(true);
            tbUsers.setDisable(true);
        }
        if (user instanceof Admin) System.out.println("Logovan kao admin");

        lbYear.setText(String.valueOf(year));
        setLvCalendar();
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
        listViewCalendar.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
           if(newKorisnik!=null)  setTrenutniDate((Date) Date.valueOf((LocalDate) newKorisnik));
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
                if (getTrenutniKorisnik() instanceof Admin) {
                    btnUserEdit.setDisable(true);
                }
                else btnUserEdit.setDisable(false);

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
    private String generisiPassword(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    private boolean isDateValid(LocalDate s) {
        if (s == null) return false;
        LocalDate trenutni = LocalDate.now();
        if (s.isAfter(trenutni)) return false;
        return true;
    }

    private boolean style(TextField polje) {
        return polje.getStyleClass().stream().anyMatch(s -> s.equals("poljeIspravno"));

    }

    private boolean style(DatePicker polje) {
        return polje.getStyleClass().stream().anyMatch(s -> s.equals("poljeIspravno"));

    }

    private boolean isValidAll() {

        if (  style(fldName) && style(fldSurname) && style(fldJmbg)  && style(fldUsername) && style(dpBirthday) && (radioProfesor.isSelected() || radioStudent.isSelected()))
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
                ObservableList<Subject> s=daoSubject.getAllSubjects();
                myStage.setOnHidden(event -> {
                    listViewSubjects.setItems(daoSubject.getAllSubjects());

                        statusMsg.setText("Subject edited.");

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
        listViewUsers.getSelectionModel().selectFirst();
        if(getTrenutniKorisnik()!=null){
        fldName.setText(getTrenutniKorisnik().getName());
        fldSurname.setText(getTrenutniKorisnik().getSurname());
        fldEmail.setText(getTrenutniKorisnik().getEmail());
        fldUsername.setText(getTrenutniKorisnik().getUsername());
        fldJmbg.setText(getTrenutniKorisnik().getJmbg());}

        dpBirthday.setValue(LocalDate.of(dpBirthday.getValue().getYear(), dpBirthday.getValue().getMonth(), dpBirthday.getValue().getDayOfMonth()));
        btnUserAdd.setDisable(false);
        btnUserDelete.setDisable(false);
        btnUserEdit.setDisable(false);
        btnCancelUser.setDisable(true);
        btnConfirmUser.setDisable(true);
        disable();
        listViewUsers.setItems(daoUser.getAllUsers());
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
            try {
                int pom=daoUser.EditUserExist(fldUsername.getText(),fldJmbg.getText(),daoUser.findUserID2(fldJmbg.getText()));
                if(checkEmail(fldEmail.getText())){
                    if (radioProfesor.isSelected()) {
                        daoUser.UpdateUser(new Profesor(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())));
                        statusMsg.setText("User edited.");

                    } else if (radioStudent.isSelected()) {
                        daoUser.UpdateUser(new Student(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())));
                        statusMsg.setText("User edited.");
                    }
                    else {
                        daoUser.UpdateUser(new Admin(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())));
                        statusMsg.setText("User edited.");
                    }}
            }  catch (ObjectAlredyExist objectAlredyExist) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit failed");
                alert.setHeaderText(null);
                alert.setContentText("Sorry, edit failed, this username or JMBG alredy exist");
                alert.showAndWait();
            }

        } else if (isValidAll() && !btnUserAdd.isDisable()) {
            try {
                int a=daoUser.AddUserExist(fldUsername.getText(),fldJmbg.getText());
                if (radioProfesor.isSelected() && checkEmail(fldEmail.getText())) {
                    pw=generisiPassword();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password");
                    alert.setHeaderText(null);
                    alert.setContentText("Password of user is, "+pw+"," + " this message will no longer be displayed once closed.");

                    alert.showAndWait();

                    daoUser.addUser(new Profesor(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), pw);
                    statusMsg.setText("User added.");
                } else if (radioStudent.isSelected() && checkEmail(fldEmail.getText())) {
                    pw=generisiPassword();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password");
                    alert.setHeaderText(null);
                    alert.setContentText("Password of user is, "+pw+"," + " this message will no longer be displayed once closed.");
                    alert.showAndWait();
                    statusMsg.setText("User added.");
                    daoUser.addUser(new Student(fldName.getText(), fldSurname.getText(), fldEmail.getText(), fldJmbg.getText(), fldUsername.getText(), Date.valueOf(dpBirthday.getValue())), pw);
                }
            } catch (ObjectAlredyExist objectAlredyExist) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit failed");
                alert.setHeaderText(null);
                alert.setContentText("Sorry, add failed, this username or JMBG alredy exist");
                alert.showAndWait();
            }



        }

        if (!isValidAll() || !checkEmail(fldEmail.getText()) && (!btnUserAdd.isDisable() || !btnUserEdit.isDisable())){
            try {
                throw new EmptyField("There is a filed that is invalid or empty");
            } catch (EmptyField emptyField) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Invalid form");
                alert.setHeaderText(null);
                alert.setContentText("There is a field that is invalid or empty.");
                alert.showAndWait();
            }}
            else{
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
        btnUserDelete.setDisable(true);
        btnUserEdit.setDisable(true);
        setTrenutniKorisnik(null);
        btnCancelUser.setDisable(false);
        btnConfirmUser.setDisable(false);
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
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/classroom.fxml"));
            loader3.setController(new addClassroomController(daoClassroom,getTrenutniClassroom()));
            root = loader3.load();
            addClassroomController = loader3.getController();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();
            ObservableList<Classroom> s = daoClassroom.getAllClassrooms();
            myStage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    listViewClassroom.setItems(daoClassroom.getAllClassrooms());
                    if (s.equals(daoClassroom.getAllClassrooms())) {
                        statusMsg.setText("Classroom not edited.");
                    } else {
                        statusMsg.setText("Classroom edited.");
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


    public boolean isLeap(int year) {
        boolean isLeap = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0)
                    isLeap = true;
                else
                    isLeap = false;
            } else
                isLeap = true;
        } else {
            isLeap = false;
        }
        return isLeap;
    }

    public void btnLeft(ActionEvent actionEvent) {
        if (year > 2020) {
            year -= 1;
            lbYear.setText(String.valueOf(year));
            boolean sa = isLeap(year);
            setLvCalendar();
        }
    }

    public void btnRight(ActionEvent actionEvent) {
        year += 1;
        lbYear.setText(String.valueOf(year));
        setLvCalendar();

    }

    void setLvCalendar() {
        listViewCalendar.getItems().clear();
        if (year != 2020) {
            day = 1;
        } else {
            day = LocalDate.now().getDayOfYear();
        }
        int days = 0;
        if (isLeap(year)) {
            days = 366;
        } else {
            days = 365;
        }
        for (int i = day; i <= days; i++) {
            listViewCalendar.getItems().add(LocalDate.ofYearDay(year, i));
        }
    }

    public void Sreach(ActionEvent actionEvent) {
        if (cbClassroom.getValue() != null && cbSubject.getValue() != null && getTrenutniDate()!= null) {
            try {
                Parent root = null;
                Stage myStage = new Stage();
                FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/timetable.fxml"));
                loader3.setController(new ClassController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, (Classroom) cbClassroom.getValue(), (Subject) cbSubject.getValue(), Date.valueOf((LocalDate) listViewCalendar.getSelectionModel().getSelectedItem())));
                root = loader3.load();
                ClassController = loader3.getController();
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Not all fields are filled");
            alert.setContentText("Check again, please!");
            alert.showAndWait();

        }

    }

    private boolean checkEmail(String email){

        try {

            String urlString = "http://apilayer.net/api/check?access_key=9bacc9cdc053432c2be20bb07dbc07bd&email=" + email;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            String inline = "";
            Scanner sc = new Scanner(url.openStream());

            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            sc.close();
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(inline);
            return true;
         //   return Boolean.valueOf(json.get("format_valid").toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;

    }
    public void printClassrooms(ActionEvent actionEvent) {

        XMLFormat xml = new XMLFormat();
        xml.zapisiXmlClassroom(daoClassroom.getAllClassroomsXML());
    }
    public void printSubjects(ActionEvent actionEvent) {

        XMLFormat xml = new XMLFormat();
        xml.zapisiXmlSuject(daoSubject.getAllSubjectsXML());
    }
    public void about(ActionEvent actionEvent) {
        System.out.println("About");
    }
    public void logOut(ActionEvent actionEvent) {

        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        Parent rootp = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/fxml/startScreen.fxml"));
            loader4.setController(new StartScreenController());
            rootp = loader4.load();
            StartScreenController = loader4.getController();
            myStage.setScene(new Scene(rootp, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.close();

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

    public Date getTrenutniDate() {
        return trenutniDate.get();
    }

    public SimpleObjectProperty<Date> trenutniDateProperty() {
        return trenutniDate;
    }

    public void setTrenutniDate(Date trenutniDate) {
        this.trenutniDate.set(trenutniDate);
    }


}
