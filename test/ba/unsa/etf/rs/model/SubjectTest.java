package ba.unsa.etf.rs.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    @Test
    void getName() {
        Subject subject=new Subject("UUP");
        assertEquals("UUP",subject.getName());
    }

    @Test
    void setName() {
        Subject subject=new Subject();
        assertEquals(null,subject.getName());
       subject.setName("UUP");
        assertEquals("UUP",subject.getName());
    }
}