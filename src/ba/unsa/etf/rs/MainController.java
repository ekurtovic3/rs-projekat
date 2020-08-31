package ba.unsa.etf.rs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

public class MainController {


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
    private TimetableDAO dao;
    private ObservableList<Subject> listSubjects;
    private ObservableList<User> listUsers;
    private ObservableList<Classroom> listClassrooms;

    public MainController() throws SQLException {
        dao = TimetableDAO.getInstance();
  //      listSubjects = FXCollections.observableArrayList(dao.getAllSubjects());
   //     listUsers = FXCollections.observableArrayList(dao.getAllUsers());
    //    listClassrooms = FXCollections.observableArrayList(dao.getAllClassrooms());
    }
    @FXML
    public void initialize() {
        listViewSubjects.setItems(dao.getAllSubjects());
        listViewUsers.setItems(dao.getAllUsers());
        listViewClassroom.setItems(dao.getAllClassrooms());

        listViewUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            dao.setTrenutniKorisnik((User) newKorisnik);

        });
        dao.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldName.textProperty().unbindBidirectional(oldKorisnik.nameProperty() );
                fldSurname.textProperty().unbindBidirectional(oldKorisnik.surnameProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldJmbg.textProperty().unbindBidirectional(oldKorisnik.jmbgProperty() );
             //   fldPassword.textProperty().unbindBidirectional(oldKorisnik.() );
              //  dpBirthday.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
            }
            if (newKorisnik == null) {
                fldName.setText("");
                fldSurname.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldJmbg.setText("");
                fldPassword.setText("");
                dpBirthday.setValue(LocalDate.now());
            }
            else {
                fldName.textProperty().bindBidirectional(newKorisnik.nameProperty());
                fldSurname.textProperty().bindBidirectional(newKorisnik.surnameProperty() );
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty() );
                fldJmbg.textProperty().bindBidirectional(newKorisnik.jmbgProperty() );
               // fldPassword.textProperty().bindBidirectional(newKorisnik.jmbgProperty() );
                //  dpBirthday.textProperty().unbindBidirectional(oldKorisnik.passwordProperty() );
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
                if(newIme.contains(".com") && newIme.contains("@") && newIme.length()>=3){
                    if(Character.isLetter(newIme.charAt(0)) && Character.isLetter(newIme.charAt(newIme.length()-1))) {
                        fldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                        fldEmail.getStyleClass().add("poljeIspravno");
                    }
                    else {
                        fldEmail.getStyleClass().removeAll("poljeIspravno");
                        fldEmail.getStyleClass().add("poljeNijeIspravno");
                    }
                }
                else {
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
                if (isDateValid(n))
                {
                    dpBirthday.getStyleClass().removeAll("poljeNijeIspravno");
                    dpBirthday.getStyleClass().add("poljeIspravno");
                }
                else {
                    dpBirthday.getStyleClass().removeAll("poljeIspravno");
                    dpBirthday.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });


        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() || newIme.length()<3) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    private boolean isDateValid(LocalDate s) {
        if (s == null) return false;
        LocalDate trenutni = LocalDate.now();
        if (s.isAfter(trenutni)) return false;
        return true;
    }

    public void btnSubjectAdd(ActionEvent actionEvent) {
    }

    public void btnSubjectChange(ActionEvent actionEvent) {
    }

    public void btnSubjectDelete(ActionEvent actionEvent) {
    }
}
