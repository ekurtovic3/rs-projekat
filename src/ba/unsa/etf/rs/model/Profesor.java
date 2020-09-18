package ba.unsa.etf.rs.model;

import java.sql.Date;
import java.util.Objects;

public class Profesor extends User {
    private String titula;


    public Profesor(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        super(name, surname, email, jmbg, username, dateOfBirth);
    }


    @Override
    public int hashCode() {
        return Objects.hash(titula);
    }


}
