package ba.unsa.etf.rs.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {




    @Test
    void getName() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        assertEquals("Emir", user.getName());
    }

    @Test
    void setName() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        user.setName("Promjena");
        assertEquals("Promjena", user.getName());
    }

    @Test
    void getSurname() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        assertEquals("Kurtovic", user.getSurname());
    }

    @Test
    void setSurname() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        user.setSurname("Promjena");
        assertEquals("Promjena", user.getSurname());
    }



    @Test
    void getJmbg() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        assertEquals("0404998170028", user.getJmbg());
    }

    @Test
    void setJmbg() {
        User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        user.setJmbg("Promjena");
        assertEquals("Promjena", user.getJmbg());

    }

    @Test
    void getUsername() {
            User user=new User("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170028","ekurtovic3", Date.valueOf(LocalDate.now()));
        user.setSurname("emir55");
            assertEquals("emir55", user.getSurname());

        }
    }
