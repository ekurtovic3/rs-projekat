package ba.unsa.etf.rs;

import java.util.ArrayList;

public class Subject {
    String name;

    public Subject(String name) {
        this.name = name;
    }
          /*
    public Subject(String name, ArrayList<Profesor> profesors, ArrayList<Profesor> students) {
        this.name = name;
        this.profesors = profesors;
        this.students = students;
    }
        */
    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
