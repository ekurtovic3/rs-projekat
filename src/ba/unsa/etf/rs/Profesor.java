package ba.unsa.etf.rs;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Profesor extends User {
    private String titula;
    //private ArrayList<Subject> subjects;

    public Profesor(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        super(name, surname, email, jmbg, username, dateOfBirth);
    }
}
