package ba.unsa.etf.rs;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

//mozda abstract
public class User {
    private SimpleStringProperty  name;
    private SimpleStringProperty surname;
    private SimpleStringProperty email;
    private SimpleStringProperty jmbg;
    private SimpleStringProperty username;
    private SimpleObjectProperty<LocalDate> dateOfBirth;
    private static User loggedInUser;
    //lista predmeta

    public User(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.email =new SimpleStringProperty(email);
        this.jmbg = new SimpleStringProperty(jmbg);
        this.username = new SimpleStringProperty(username);
        this.dateOfBirth = new SimpleObjectProperty<LocalDate>(getLocalDateOfBirth(dateOfBirth));
    }

    public User(Object newKorisnik) {
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
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getJmbg() {
        return jmbg.get();
    }

    public SimpleStringProperty jmbgProperty() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg.set(jmbg);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public Date getDateOfBirth() {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //creating the instance of LocalDate using the day, month, year info
        LocalDate localDate = LocalDate.of(dateOfBirth.get().getYear(), dateOfBirth.get().getMonth(), dateOfBirth.get().getDayOfMonth());

        Date date = Date.valueOf(localDate);

        //Displaying LocalDate and Date
      /*  System.out.println("LocalDate is: " + localDate);
        System.out.println("Date is: " + date);*/
        return date;
    }

    public SimpleObjectProperty<LocalDate> dateOfBirthProperty() {
        return dateOfBirth;
    }


    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(getLocalDateOfBirth(dateOfBirth));
    }

    public LocalDate getLocalDateOfBirth(Date date){
        return date.toLocalDate();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    @Override
    public String toString() {
        return this.getName()+" "+this.getSurname()+" "+this.getEmail();
    }

    //equals
}
