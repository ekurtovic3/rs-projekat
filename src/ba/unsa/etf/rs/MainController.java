package ba.unsa.etf.rs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class MainController {


    public ListView listViewSubjects;
    public ListView listViewUsers;
    public ListView listViewClassroom;
    public TextField fldName;
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
     //   dao.defaultClass();
        listViewSubjects.setItems(dao.getAllSubjects());
        listViewUsers.setItems(dao.getAllUsers());
        listViewClassroom.setItems(dao.getAllClassrooms());
        listViewUsers.getSelectionModel().selectedItemProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
            dao.setTrenutniKorisnik((User) newKorisnik);

        });
        dao.trenutniKorisnikProperty().addListener((obs, oldKorisnik, newKorisnik) -> {
                fldName.textProperty().bindBidirectional( newKorisnik.nameProperty() );


        });
    }
    public void btnSubjectAdd(ActionEvent actionEvent) {
    }

    public void btnSubjectChange(ActionEvent actionEvent) {
    }

    public void btnSubjectDelete(ActionEvent actionEvent) {
    }
}
