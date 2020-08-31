package ba.unsa.etf.rs;

import java.sql.Time;

public class Class {
    int id;
    Time start,end;
    int period;
    Classroom classroom;
    Subject subject;
    Type type;

    public enum Type{
        Tutorijal("Tutorijal"), Exercises("Exercises"), Lectures("Lectures");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }


    public Class(int id, Time start, Time end, int period, Classroom classroom, Subject subject, Type type) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.period = period;
        this.classroom = classroom;
        this.subject = subject;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
