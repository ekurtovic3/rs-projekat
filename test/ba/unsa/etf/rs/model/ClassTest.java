package ba.unsa.etf.rs.model;

import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    @Test
    void getType() {
        Classroom classroom=new Classroom("0-2",50);
        Subject subject=new Subject("UUP");
        Class aclass=new Class(12,13,0,classroom,subject, Class.Type.Exercises, Date.valueOf(LocalDate.of(2020,9,15)));
        assertEquals("Exercises",aclass.getType().toString());
    }

    @Test
    void setType() {
    Class aclass=new Class();
    aclass.setType(Class.Type.Tutorial);
        assertEquals("Tutorial",aclass.getType().toString());
    }

    @Test
    void setStart() {
        Class aclass=new Class();
        aclass.setType(Class.Type.Exercises);
        assertEquals("Exercises",aclass.getType().toString());
        aclass.setStart(8);
        assertEquals(8,aclass.getStart());
    }

    @Test
    void getClassroom() {
        Classroom classroom=new Classroom("0-2",50);
        Subject subject=new Subject("UUP");
        Class aclass=new Class(12,13,0,classroom,subject, Class.Type.Exercises, Date.valueOf(LocalDate.of(2020,9,15)));
        assertEquals("Name: 0-2, Capacity: 50",aclass.getClassroom().toString());
    }

    @Test
    void setClassroom() {
        Classroom classroom=new Classroom("VA",100);
        Class a=new Class();
        a.setClassroom(classroom);
        assertEquals("Name: VA, Capacity: 100",a.getClassroom().toString());
    }
}