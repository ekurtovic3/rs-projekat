package ba.unsa.etf.rs.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Class {

    public enum Type{
        Tutorial("Tutorial"), Exercises("Exercises"), Lectures("Lectures");


        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getName() {
            return name;
        }
    }



    int id=0;
    int start,end;
    int period;
    Classroom classroom;
    Subject subject;
    Type type;
    Date Date;

    public Class() {
    }

    public Class(int id, int start, int end, int period, Classroom classroom, Subject subject, Type type, java.sql.Date date) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.period = period;
        this.classroom = classroom;
        this.subject = subject;
        this.type = type;
        Date = date;
    }

    public Class(int start, int end, int period, Classroom classroom, Subject subject, Type type, java.sql.Date date) {
        this.start = start;
        this.end = end;
        this.period = period;
        this.classroom = classroom;
        this.subject = subject;
        this.type = type;
        Date = date;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
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

    @Override
    public String toString() {
        return  subject.getName()+" "+start+":00-"+end+":00"+" "+classroom.getName()+" "+type.name;
    }
}
