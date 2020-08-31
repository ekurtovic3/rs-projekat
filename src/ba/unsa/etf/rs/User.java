package ba.unsa.etf.rs;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    private SimpleStringProperty  name;
    private String surname;
    private String email;
    private String jmbg;
    private String username;
    private Date dateOfBirth;

    @Override
    public String toString() {
        return  name +" " +" "+surname +" " +email ;
    }

    public User(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        this.name = new SimpleStringProperty(name);;
        this.surname = surname;
        this.email = email;
        this.jmbg = jmbg;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
