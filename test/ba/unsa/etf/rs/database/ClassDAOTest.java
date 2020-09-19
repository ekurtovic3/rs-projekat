package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Class;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import ba.unsa.etf.rs.model.User;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClassDAOTest {

    @Test
    void getAllClass() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        assertEquals(0,m.getAllClass().size());
    }

    @Test
    void getAllClass2() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(3,m.getAllClass().size());
    }
    @Test
    void addClass() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(0,m.getAllClass().get(0).getPeriod());
        assertEquals(Date.valueOf(LocalDate.now()),m.getAllClass().get(0).getDate());
        assertEquals(0,m.getAllClass().get(1).getPeriod());
        assertEquals(Date.valueOf(LocalDate.now()),m.getAllClass().get(1).getDate());
        Class aclass1=new Class(16,17,1,new Classroom("0-2",10),new Subject("RS"), Class.Type.Exercises, Date.valueOf(LocalDate.now()));
        m.addClass(aclass1);
        assertEquals(1,m.getAllClass().get(3).getPeriod());
        assertNotNull(m.getAllClass().get(3).getSubject());
        assertEquals("Exercises",m.getAllClass().get(3).getType().toString());

    }

    @Test
    void updateClass() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(0,m.getAllClass().get(0).getPeriod());
        assertEquals(Date.valueOf(LocalDate.now()),m.getAllClass().get(0).getDate());
        assertEquals(0,m.getAllClass().get(1).getPeriod());
        assertEquals(Date.valueOf(LocalDate.now()),m.getAllClass().get(1).getDate());
        Class aclass1=new Class(16,17,1,new Classroom("0-2",10),new Subject("RS"), Class.Type.Exercises, Date.valueOf(LocalDate.now()));
        m.UpdateClass(aclass1,1);
        assertEquals(1,m.getAllClass().get(0).getPeriod());
        assertNotNull(m.getAllClass().get(0).getSubject());
        assertEquals("Exercises",m.getAllClass().get(0).getType().toString());

    }

    @Test
    void findClass() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        m.defaultData();
        Class a=m.findClass(1);
        assertEquals(0,a.getPeriod());
        assertNotNull(a.getDate());
        assertEquals("Exercises",a.getType().toString());

    }

    @Test
    void deleteClass() {
        ClassDAO m = ClassDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(3,m.getAllClass().size());
        m.deleteClass(1);
        m.deleteClass(2);
        m.deleteClass(3);
        assertEquals(0,m.getAllClass().size());



    }
}