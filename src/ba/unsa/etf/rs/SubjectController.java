package ba.unsa.etf.rs;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SubjectController {
    public MenuButton mbtnAddProfesor;
    public MenuButton mbtnDeleteProfesor;
    public TextField  fldSubjectName;
    private Subject subject;
    private TimetableDAO dao;

    @FXML
    public void initialize() {
        mbtnAddProfesor.getItems().clear();
        fldSubjectName.setText(subject.toString());
         ObservableList<User> allUsers = dao.getAllUsers();
              for(int i=0;i<allUsers.size();i++)
        { User p= allUsers.get(i);
            MenuItem i1=new MenuItem(allUsers.get(i).toString());
            i1.setOnAction(event -> {
                System.out.println("Dodavanje profesora" + p.toString()); // Dodavanje profesora na predmet
            });
            mbtnAddProfesor.getItems().add(i1);
        }

        for(int i=0;i<allUsers.size();i++)
        { User p= allUsers.get(i);
            MenuItem i1=new MenuItem(allUsers.get(i).toString());
            i1.setOnAction(event -> {
                //Brisanje profesora sa predmeta
            });
            mbtnAddProfesor.getItems().add(i1);
        }


    }


    public SubjectController(TimetableDAO dao, Subject s) {
        this.dao = dao;
        this.subject = s;
    }
    public SubjectController() {
    }


    public void deleteProfesor(ActionEvent actionEvent) {
    }
}
