package ba.unsa.etf.rs;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Profesor extends User {
    private String titula;
    //private ArrayList<Subject> subjects;

    public Profesor(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        super(name, surname, email, jmbg, username, dateOfBirth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return Objects.equals(titula, profesor.titula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titula);
    }
}
