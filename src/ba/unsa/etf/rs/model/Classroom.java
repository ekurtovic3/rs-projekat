package ba.unsa.etf.rs.model;

import java.io.Serializable;
import java.util.Objects;

public class Classroom  implements Serializable {
     int id;
     String name;
    int capacity;

    public Classroom() {
    }

    public Classroom(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public Classroom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return
                "Name: " + name + ", Capacity: " +capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return id == classroom.id &&
                capacity == classroom.capacity &&
                Objects.equals(name, classroom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity);
    }
}
