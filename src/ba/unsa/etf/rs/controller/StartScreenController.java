package ba.unsa.etf.rs.controller;

import ba.unsa.etf.rs.database.*;
import ba.unsa.etf.rs.model.Country;

import ba.unsa.etf.rs.model.Student;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class StartScreenController implements Initializable {
    public TextField tfUsernameSignIn;
    public TextField tfNameSignIn;
    public TextField tfJMBGSignIn;
    public TextField tfSurnameSignIn;
    public TextField tfEmailSignIn;
    public DatePicker dpDateOfBirthSignIn;
    public TextField tfUsernameLogIn;
    public PasswordField tfPasswordLogIn;
    public PasswordField tfPasswordSignUp;


    // private TimetableDAO dao;
    private CountryDAO countryDao;
    private ClassDAO daoClass;
    private ClassroomDAO daoClassroom;
    private ProfessorToSubjectDAO daoProfessorToSubjectDAO;
    private SubjectDAO daoSubject;
    private UserDAO daoUser;

    public ImageView imvIkonaDrzave;
    public ChoiceBox<Country> cbCountrySignIn;
    private Object MainController;


    public StartScreenController(CountryDAO countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        daoClass = ClassDAO.getInstance();
        daoClassroom = ClassroomDAO.getInstance();
        daoProfessorToSubjectDAO = ProfessorToSubjectDAO.getInstance();
        daoSubject = SubjectDAO.getInstance();
        daoUser = UserDAO.getInstance();


        cbCountrySignIn.setItems(countryDao.getAllCountries());
        cbCountrySignIn.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue ov, Number value, Number new_value) {
                System.out.println(cbCountrySignIn.getItems().get(new Integer(new_value.toString())));
                Country currentlySelectedCountry = cbCountrySignIn.getItems().get(new Integer(new_value.toString()));
                setCountriesIcon(currentlySelectedCountry);

            }

        });

/*
        tfPasswordLogIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && !(newIme.length() < 3)) {
                tfPasswordLogIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfPasswordLogIn.getStyleClass().add("poljeIspravno");
            } else {
                tfPasswordLogIn.getStyleClass().removeAll("poljeIspravno");
                tfPasswordLogIn.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfUsernameLogIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfUsernameLogIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfUsernameLogIn.getStyleClass().add("poljeIspravno");
            } else {
                tfUsernameLogIn.getStyleClass().removeAll("poljeIspravno");
                tfUsernameLogIn.getStyleClass().add("poljeNijeIspravno");
            }
        });*/

        tfNameSignIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfNameSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfNameSignIn.getStyleClass().add("poljeIspravno");
            } else {
                tfNameSignIn.getStyleClass().removeAll("poljeIspravno");
                tfNameSignIn.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfSurnameSignIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfSurnameSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfSurnameSignIn.getStyleClass().add("poljeIspravno");
            } else {
                tfSurnameSignIn.getStyleClass().removeAll("poljeIspravno");
                tfSurnameSignIn.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfEmailSignIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                if (newIme.contains(".com") && newIme.contains("@") && newIme.length() >= 3) {
                    if (Character.isLetter(newIme.charAt(0)) && Character.isLetter(newIme.charAt(newIme.length() - 1))) {
                        tfEmailSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                        tfEmailSignIn.getStyleClass().add("poljeIspravno");
                    } else {
                        tfEmailSignIn.getStyleClass().removeAll("poljeIspravno");
                        tfEmailSignIn.getStyleClass().add("poljeNijeIspravno");
                    }
                } else {
                    tfEmailSignIn.getStyleClass().removeAll("poljeIspravno");
                    tfEmailSignIn.getStyleClass().add("poljeNijeIspravno");
                }
            } else {
                tfEmailSignIn.getStyleClass().removeAll("poljeIspravno");
                tfEmailSignIn.getStyleClass().add("poljeNijeIspravno");
            }
        });
        tfPasswordSignUp.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty() && !(newIme.length() < 3)) {
                tfPasswordSignUp.getStyleClass().removeAll("poljeNijeIspravno");
                tfPasswordSignUp.getStyleClass().add("poljeIspravno");
            } else {
                tfPasswordSignUp.getStyleClass().removeAll("poljeIspravno");
                tfPasswordSignUp.getStyleClass().add("poljeNijeIspravno");
            }
        });


        tfUsernameSignIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfUsernameSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfUsernameSignIn.getStyleClass().add("poljeIspravno");
            } else {
                tfUsernameSignIn.getStyleClass().removeAll("poljeIspravno");
                tfUsernameSignIn.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfUsernameSignIn.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                tfUsernameSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                tfUsernameSignIn.getStyleClass().add("poljeIspravno");
            } else {
                tfUsernameSignIn.getStyleClass().removeAll("poljeIspravno");
                tfUsernameSignIn.getStyleClass().add("poljeNijeIspravno");
            }
        });

        tfJMBGSignIn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                if (isJmbgValid(n)) {
                    tfJMBGSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                    tfJMBGSignIn.getStyleClass().add("poljeIspravno");
                } else {
                    tfJMBGSignIn.getStyleClass().removeAll("poljeIspravno");
                    tfJMBGSignIn.getStyleClass().add("poljeNijeIspravno");
                }
            }
        });
        dpDateOfBirthSignIn.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observableValue, LocalDate o, LocalDate n) {
                if (isDateValid(n)) {
                    dpDateOfBirthSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                    dpDateOfBirthSignIn.getStyleClass().add("poljeIspravno");
                } else {
                    dpDateOfBirthSignIn.getStyleClass().removeAll("poljeIspravno");
                    dpDateOfBirthSignIn.getStyleClass().add("poljeNijeIspravno");
                }
                if (isJmbgValid(tfJMBGSignIn.getText())) {
                    tfJMBGSignIn.getStyleClass().removeAll("poljeNijeIspravno");
                    tfJMBGSignIn.getStyleClass().add("poljeIspravno");
                } else {
                    tfJMBGSignIn.getStyleClass().removeAll("poljeIspravno");
                    tfJMBGSignIn.getStyleClass().add("poljeNijeIspravno");
                }
            }


        });

    }

    private void setCountriesIcon(Country currentlySelectedCountry) {
        String countryNameInLowerCase = currentlySelectedCountry.toString().toLowerCase();

        String partsOfCountyName[] = countryNameInLowerCase.split(" ");

        String countryNameWithDashes = new String("");
        for (int i = 0; i < partsOfCountyName.length; i++) {
            countryNameWithDashes += partsOfCountyName[i];

            if (i + 1 != partsOfCountyName.length)
                countryNameWithDashes += "-";
        }

        imvIkonaDrzave.setImage(new Image("/img/countryFlags/png/" + countryNameWithDashes + ".png"));
        imvIkonaDrzave.setPreserveRatio(true);
    }

    private boolean isJmbgValid(String s) {
        if (s.length() != 13) return false;
        try {
            int dd = Integer.parseInt(tfJMBGSignIn.getText(0, 2));
            int mm = Integer.parseInt(tfJMBGSignIn.getText(2, 4));
            int ggg = Integer.parseInt(tfJMBGSignIn.getText(4, 7));
            if (mm < 1 || mm > 12) return false;
            if (dd < 1 || dd > YearMonth.of(ggg, mm).lengthOfMonth()) return false;
            int rr = Integer.parseInt(tfJMBGSignIn.getText(7, 9));
            int bbb = Integer.parseInt(tfJMBGSignIn.getText(9, 12));
            int k = Integer.parseInt(tfJMBGSignIn.getText(12, 13));
            int l = 11 - ((7 * (Integer.parseInt(tfJMBGSignIn.getText(0, 1)) + Integer.parseInt(tfJMBGSignIn.getText(6, 7))) +
                    6 * (Integer.parseInt(tfJMBGSignIn.getText(1, 2)) + Integer.parseInt(tfJMBGSignIn.getText(7, 8))) +
                    5 * (Integer.parseInt(tfJMBGSignIn.getText(2, 3)) + Integer.parseInt(tfJMBGSignIn.getText(8, 9))) +
                    4 * (Integer.parseInt(tfJMBGSignIn.getText(3, 4)) + Integer.parseInt(tfJMBGSignIn.getText(9, 10))) +
                    3 * (Integer.parseInt(tfJMBGSignIn.getText(4, 5)) + Integer.parseInt(tfJMBGSignIn.getText(10, 11))) +
                    2 * (Integer.parseInt(tfJMBGSignIn.getText(5, 6)) + Integer.parseInt(tfJMBGSignIn.getText(11, 12)))) % 11);
            if (l >= 1 && l <= 9 && l != k) return false;
            if (l > 9 && k != 0) return false;
            //unakrsno sa datumom
            if (ggg != dpDateOfBirthSignIn.getValue().getYear() % 1000) return false;
            if (mm != dpDateOfBirthSignIn.getValue().getMonthValue()) return false;
            if (dd != dpDateOfBirthSignIn.getValue().getDayOfMonth()) return false;
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

    private boolean isValidAllSignIn() {
        if (style(tfNameSignIn) && style(tfSurnameSignIn) && style(tfJMBGSignIn) && style(tfEmailSignIn) && style(tfUsernameSignIn) && style(dpDateOfBirthSignIn) && style(tfPasswordSignUp))
            return true;
        return false;
    }

    private boolean isValidAllLogIn() {
        if (style(tfUsernameLogIn) && style(tfPasswordLogIn))
            return true;
        return false;
    }

    public void LogIn(ActionEvent actionEvent) {
        if ( (UserDAO.findUserLogIn(tfUsernameLogIn.getText(), tfPasswordLogIn.getText())) != -1) {
            int id = daoUser.findUserLogIn(tfUsernameLogIn.getText(), tfPasswordLogIn.getText());
System.out.println(id);
            Parent root = null;
            try {
                Stage myStage = new Stage();
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/mainform.fxml"));
                loader2.setController(new MainController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, daoUser.findUserByID(id)));
                root = loader2.load();
                MainController = loader2.getController();
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();
                Node n = (Node) actionEvent.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login failed");
            alert.setHeaderText(null);
            alert.setContentText("Sorry, login failed, plese check your username or password");
            tfUsernameLogIn.setText("");
            tfUsernameLogIn.setText("");
            alert.showAndWait();


        }
    }

    public void SingIn(ActionEvent actionEvent) {
        if (isValidAllSignIn()) {
            Student student = new Student(tfNameSignIn.getText(), tfSurnameSignIn.getText(), tfEmailSignIn.getText(), tfJMBGSignIn.getText(), tfUsernameSignIn.getText(), Date.valueOf(dpDateOfBirthSignIn.getValue()));
            daoUser.addUser(new Student(tfNameSignIn.getText(), tfSurnameSignIn.getText(), tfEmailSignIn.getText(), tfJMBGSignIn.getText(), tfUsernameSignIn.getText(), Date.valueOf(dpDateOfBirthSignIn.getValue())), tfPasswordSignUp.getText());

            Parent root = null;
            try {
                Stage myStage = new Stage();
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/mainform.fxml"));
                loader2.setController(new MainController(daoClass, daoClassroom, daoProfessorToSubjectDAO, daoSubject, daoUser, student));
                root = loader2.load();
                MainController = loader2.getController();
                myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                myStage.setResizable(false);
                myStage.show();
                Node n = (Node) actionEvent.getSource();
                Stage stage = (Stage) n.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}