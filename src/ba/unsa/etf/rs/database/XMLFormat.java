package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class XMLFormat {

    public void zapisiXmlSuject(ArrayList<Subject> subjects) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("subjects.xml"));
            izlaz.writeObject(subjects);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }
    }
    public void zapisiXmlClassroom(ArrayList<Classroom> classrooms) {
        try {
            XMLEncoder izlaz = new XMLEncoder(new FileOutputStream("classrooms.xml"));
            izlaz.writeObject(classrooms);
            izlaz.close();
        } catch(Exception e) {
            System.out.println("Greška: "+e);
        }
    }
}
