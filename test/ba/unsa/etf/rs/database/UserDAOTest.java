package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Profesor;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void updateUser() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("admin",m.getAllUsers().get(0).getUsername());
        m.UpdateUser(new Profesor("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170021","student", Date.valueOf(LocalDate.of(1998,4,4))));
        assertEquals("ekurtovic3@gmail.com",m.getAllUsers().get(2).getEmail());
    }

    @Test
    void addUser() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("admin",m.getAllUsers().get(0).getUsername());
        assertEquals("profesor",m.getAllUsers().get(1).getUsername());
        assertEquals("student",m.getAllUsers().get(2).getUsername());
        m.addUser(new User("Meho","Mehic","meho@etf.unsa.ba","0404998170025","dodavanje", Date.valueOf(LocalDate.of(1998,4,4))),"dodavanje");
        assertEquals("dodavanje",m.getAllUsers().get(3).getUsername());
    }

    @Test
    void deleteUser() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(3,m.getAllUsers().size());
        assertEquals("Emir",m.getAllUsers().get(2).getName());
        m.deleteUser(m.getAllUsers().get(2).getJmbg());
        assertNotEquals(3,m.getAllUsers().size());
    }

    @Test
    void getAllUsers() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        assertEquals(0,m.getAllUsers().size());
    }

    @Test
    void findUserID() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("admin",m.getAllUsers().get(0).getUsername());
        m.UpdateUser(new Profesor("Emir","Kurtovic","ekurtovic3@gmail.com","0404998170021","student", Date.valueOf(LocalDate.of(1998,4,4))));
        assertEquals("ekurtovic3@gmail.com",m.getAllUsers().get(2).getEmail());
        assertEquals(3,m.findUserID2("0404998170021"));
    }
     @Test
    void findUserByID() {
        UserDAO m = UserDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("admin",m.getAllUsers().get(0).getUsername());
        assertEquals(null,m.findUserByID2(5));
    }
}