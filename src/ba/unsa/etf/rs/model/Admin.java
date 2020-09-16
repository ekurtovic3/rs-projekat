package ba.unsa.etf.rs.model;

import java.sql.Date;

public class Admin extends User {


    public Admin(String name, String surname, String email, String jmbg, String username, Date dateOfBirth) {
        super(name, surname, email, jmbg, username, dateOfBirth);
    }

    public Admin(User newKorisnik) {
        super(newKorisnik);
    }
}
