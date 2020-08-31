package ba.unsa.etf.rs;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class Student extends User {
    int index;
    //ArrayList<Subject> predmeti;

    public Student(String name, String surname, String email, String jmbg, String username, Date dateOfBirth, int index) {
        super(name, surname, email, jmbg, username, dateOfBirth);
        this.index = index;
    }

    public Student(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {

        super(name, surname, email, jmbg, username, dateOfBirth);
    }
}
