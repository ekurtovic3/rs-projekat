package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Subject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SubjectDAOTest {

    @Test
    void clearAll() {
        SubjectDAO m = SubjectDAO.getInstance();
        m.clearAll();
        assertEquals(0,m.getAllSubjects().size());
    }

    @Test
    void getAllSubjects() {
        SubjectDAO m = SubjectDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("UUP",m.getAllSubjects().get(0).toString());
        assertEquals("RPR",m.getAllSubjects().get(1).toString());
        assertEquals("RS",m.getAllSubjects().get(2).toString());
        assertNotEquals("NRS",m.getAllSubjects().get(2).toString());
        m.addSubject(new Subject("NRS"));
        assertEquals("NRS",m.getAllSubjects().get(3).toString());
    }

    @Test
    void deleteSubject() {
        SubjectDAO m = SubjectDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals("UUP",m.getAllSubjects().get(0).toString());
        assertEquals("RPR",m.getAllSubjects().get(1).toString());
        m.deleteSubject(m.getAllSubjects().get(0).getName());
        assertNotEquals("UUP",m.getAllSubjects().get(0).toString());

    }

    @Test
    void findSubjectByID() {
        SubjectDAO m = SubjectDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(1,m.findSubjectID(m.getAllSubjects().get(0).getName()));
    }
}