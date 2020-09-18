package ba.unsa.etf.rs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassroomTest {

    @Test
    void getName() {
        Classroom classroom=new Classroom("0-2",50);
        assertEquals("0-2", classroom.getName());

    }

    @Test
    void setName() {
        Classroom classroom=new Classroom();
        classroom.setName("0-2");
        assertEquals("0-2", classroom.getName());
    }

    @Test
    void getCapacity() {
        Classroom classroom=new Classroom("0-2",50);
        assertEquals(50, classroom.getCapacity());
    }

    @Test
    void setCapacity() {
        Classroom classroom=new Classroom();
        classroom.setName("0-2");
        classroom.setCapacity(100);
        assertEquals("0-2", classroom.getName());
        assertEquals(100, classroom.getCapacity());

    }
}