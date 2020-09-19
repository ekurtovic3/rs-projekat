package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Subject;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ProfessorToSubjectDAOTest {




    @Test
    void getSubjectsOfStudent() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();
        SubjectDAO s = SubjectDAO.getInstance();

        m.clearAll();
        m.defaultData();
        assertEquals(2,m.getSubjectsOfStudent(3).size());
    }
    @Test
    void getSubjectsOfStudent2() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();
        SubjectDAO s = SubjectDAO.getInstance();

        m.clearAll();
        m.defaultData();
        assertEquals("UUP",m.getSubjectsOfStudent(3).get(0).getName());
        assertEquals("RPR",m.getSubjectsOfStudent(3).get(1).getName());
    }
    @Test
    void getSubjectOfProfesor() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();
        SubjectDAO s = SubjectDAO.getInstance();

        m.clearAll();
        m.defaultData();
        assertEquals(2,m.getSubjectOfProfesor(2).size());
    }

    @Test
    void getSubjectOfProfesor2() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();
        SubjectDAO s = SubjectDAO.getInstance();

        m.clearAll();
        m.defaultData();
        assertEquals("UUP",m.getSubjectOfProfesor(3).get(0).getName());
        assertEquals("RPR",m.getSubjectOfProfesor(3).get(1).getName());
    }

    @Test
    void addProfesorToSubject() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();

        m.clearAll();
        m.defaultData();
        assertEquals(2,m.getSubjectOfProfesor(2).size());
        m.addProfesorToSubject(3,3);
        assertEquals(3,m.getSubjectOfProfesor(3).size());
        assertEquals("RS",m.getSubjectOfProfesor(3).get(2).getName());

    }

    @Test
    void deleteProfesorToSubject() throws SQLException {
        ProfessorToSubjectDAO m = ProfessorToSubjectDAO.getInstance();
        m.clearAll();
        m.defaultData();
        assertEquals(2,m.getSubjectOfProfesor(2).size());
        m.addProfesorToSubject(3,3);
        assertEquals(3,m.getSubjectOfProfesor(3).size());
        assertEquals("RS",m.getSubjectOfProfesor(3).get(2).getName());
        m.deleteProfesorToSubject(3,3);
        assertEquals(2,m.getSubjectOfProfesor(2).size());
        assertNotEquals("RS",m.getSubjectOfProfesor(3).get(1).getName());



    }
}