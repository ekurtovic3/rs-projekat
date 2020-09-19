package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomDAOTest {

    @Test
    void addClassroom() {
        ClassroomDAO m = ClassroomDAO.getInstance();
        m.clearAll();
        assertEquals(0,m.getAllClassrooms().size());
    }

    @Test
    void updateClassrom() {
        ClassroomDAO m = ClassroomDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("0-2",m.getAllClassrooms().get(1).getName());
        assertEquals("VA",m.getAllClassrooms().get(2).getName());
        m.addClassroom(new Classroom("MA",50));
        assertEquals("MA",m.getAllClassrooms().get(3).getName());
        assertEquals(50,m.getAllClassrooms().get(3).getCapacity());
        m.updateClassrom(new Classroom("Edit",20),4);

        assertEquals("Edit",m.getAllClassrooms().get(3).getName());
        assertEquals(20,m.getAllClassrooms().get(3).getCapacity());
    }

    @Test
    void deleteClassroom() {
        ClassroomDAO m = ClassroomDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("0-2",m.getAllClassrooms().get(1).getName());
        assertEquals("VA",m.getAllClassrooms().get(2).getName());
        m.deleteClassroom(m.getAllClassrooms().get(0).getName());
        assertEquals("0-2",m.getAllClassrooms().get(0).getName());

    }

    @Test
    void getAllClassrooms() {
        ClassroomDAO m = ClassroomDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("0-1",m.getAllClassrooms().get(0).getName());
        assertEquals("0-2",m.getAllClassrooms().get(1).getName());
        assertEquals("VA",m.getAllClassrooms().get(2).getName());
        assertNotEquals("MA",m.getAllClassrooms().get(2).getName());
        m.addClassroom(new Classroom("MA",50));
        assertEquals("MA",m.getAllClassrooms().get(3).getName());
        assertEquals(50,m.getAllClassrooms().get(3).getCapacity());
    }
}