package ba.unsa.etf.rs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

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
    public Button btnAddUser;
    public RadioButton radioStudent;
    public RadioButton radioProfesor;
    public Button btnCancelUser;
    public Button btnConfirmUser;
    private User pom=null;
    private TimetableDAO dao;
    private ObservableList<Subject> listSubjects;
    private ObservableList<User> listUsers;
    private ObservableList<Classroom> listClassrooms;
    private Object SubjectController;

    public MainController() throws SQLException {
        dao = TimetableDAO.getInstance();
  //      listSubjects = FXCollections.observableArrayList(dao.getAllSubjects());
   //     listUsers = FXCollections.observableArrayList(dao.getAllUsers());
    //    listClassrooms = FXCollections.observableArrayList(dao.getAllClassrooms());
    }
    @FXML
    public void initialize() {
        //btnCancelUser.setDisable(true);
        listViewSubjects.setItems(dao.getAllSubjects());
        listViewUsers.setItems(dao.getAllUsers());
        listViewClassroom.setItems(dao.getAllClassrooms());

        listViewUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            dao.setTrenutniKorisnik((User) newKorisnik);
           // System.out.println(newKorisnik.toString());
            pom =new User(((User) newKorisnik).getName(),((User) newKorisnik).getSurname(),((User) newKorisnik).getEmail(),((User) newKorisnik).getJmbg(),((User) newKorisnik).getUsername(),((User) newKorisnik).getDateOfBirth());
        System.out.println(pom.toString());
        });
        listViewSubjects.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            dao.setTrenutniSubject((Subject) newKorisnik);
//            System.out.println(newKorisnik.toString());
        });
        dao.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            if (oldKorisnik != null) {
                fldName.textProperty().unbindBidirectional(oldKorisnik.nameProperty() );
                fldSurname.textProperty().unbindBidirectional(oldKorisnik.surnameProperty() );
                fldEmail.textProperty().unbindBidirectional(oldKorisnik.emailProperty() );
                fldUsername.textProperty().unbindBidirectional(oldKorisnik.usernameProperty() );
                fldJmbg.textProperty().unbindBidirectional(oldKorisnik.jmbgProperty() );
                dpBirthday.valueProperty().unbindBidirectional(oldKorisnik.dateOfBirthProperty());
                if(dao.getTrenutniKorisnik() instanceof Student) radioStudent.setSelected(true);
                if(dao.getTrenutniKorisnik() instanceof Profesor) radioProfesor.setSelected(true);

            }
            if (newKorisnik == null) {
                fldName.setText("");
                fldSurname.setText("");
                fldEmail.setText("");
                fldUsername.setText("");
                fldJmbg.setText("");
                dpBirthday.setValue(LocalDate.now());
            }
            else {
                fldName.textProperty().bindBidirectional(newKorisnik.nameProperty());
                fldSurname.textProperty().bindBidirectional(newKorisnik.surnameProperty() );
                fldEmail.textProperty().bindBidirectional(newKorisnik.emailProperty() );
                fldUsername.textProperty().bindBidirectional(newKorisnik.usernameProperty() );
                fldJmbg.textProperty().bindBidirectional(newKorisnik.jmbgProperty() );
                dpBirthday.valueProperty().bindBidirectional(newKorisnik.dateOfBirthProperty());
                if(dao.getTrenutniKorisnik() instanceof Student) radioStudent.setSelected(true);
                if(dao.getTrenutniKorisnik() instanceof Profesor) radioProfesor.setSelected(true);

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
                if (isJmbgValid(n))
                {
                    fldJmbg.getStyleClass().removeAll("poljeNijeIspravno");
                    fldJmbg.getStyleClass().add("poljeIspravno");
                }
                else {
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
                if (isJmbgValid(fldJmbg.getText()))
                {
                    fldJmbg.getStyleClass().removeAll("poljeNijeIspravno");
                    fldJmbg.getStyleClass().add("poljeIspravno");
                }
                else {
                    fldJmbg.getStyleClass().removeAll("poljeIspravno");
                    fldJmbg.getStyleClass().add("poljeNijeIspravno");
                }
            }


        });


/*        fldPassword.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() || newIme.length()<3) {
                fldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fldPassword.getStyleClass().removeAll("poljeIspravno");
                fldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });*/
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
    private boolean isValidAll( ){
        if(style(fldName) && style(fldSurname) && style(fldJmbg) && style(fldEmail) && style(fldUsername) && style(dpBirthday))  return true;
        return false;
    }
    public void btnSubjectAdd(ActionEvent actionEvent) {
    }

    public void btnSubjectChange(ActionEvent actionEvent) {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/subject.fxml"));
            loader2.setController(new SubjectController(dao,(Subject) listViewSubjects.getSelectionModel().getSelectedItem()));
            root = loader2.load();
            SubjectController = loader2.getController();
            myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();
            myStage.setOnHidden(event -> {
                listViewSubjects.setItems(dao.getAllSubjects());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSubjectDelete(ActionEvent actionEvent) {
    }

    public void AllUsers(ActionEvent actionEvent) {
        listViewUsers.setItems(dao.getAllUsers());
    }

    public void Students(ActionEvent actionEvent) {
        listViewUsers.setItems(dao.getAllSpecificUsers(1));

    }


    public void Profesors(ActionEvent actionEvent) {
        listViewUsers.setItems(dao.getAllSpecificUsers(2));
    }

    public void addUser(ActionEvent actionEvent) {
        User pom=new Student("E","E","E","0504998170021","e",Date.valueOf(LocalDate.now()));
        dao.addUser(pom);
        dao.setTrenutniKorisnik(pom);
    }

    public void cancelUser(ActionEvent actionEvent) {
if(pom != null) {
  //  System.out.println(dao.getTrenutniKorisnik().toString());
    System.out.println(pom.toString());
    fldName.setText(pom.getName());
    fldSurname.setText(pom.getSurname());
    fldEmail.setText(pom.getEmail());
    fldJmbg.setText(pom.getJmbg());
    fldUsername.setText(pom.getUsername());
    dpBirthday.setValue(pom.getDateOfBirth().toLocalDate());
}
    }

    public void confirmUser(ActionEvent actionEvent) {
        if (isValidAll())  {
            dao.UpdateUser(dao.getTrenutniKorisnik());
            listViewUsers.setItems(dao.getAllUsers());
        }

    }

    public void deleteUser(ActionEvent actionEvent) {
        if(dao.getTrenutniKorisnik()!=null){
            dao.deleteUser(dao.getTrenutniKorisnik().getJmbg());
            listViewUsers.setItems(dao.getAllUsers());
        }
    }
}
