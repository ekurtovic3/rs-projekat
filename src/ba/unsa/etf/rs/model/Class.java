package ba.unsa.etf.rs.model;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class Class {

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
    int id;
    Time start,end;
    int period;
    Classroom classroom;
    Subject subject;
    Type type;
    Date Date;


    public Class(int id, Time start, Time end, int period, Classroom classroom, Subject subject, Type type, Date date) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.period = period;
        this.classroom = classroom;
        this.subject = subject;
        this.type = type;
        this.Date = date;
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

    @Override
    public String toString() {
        return "Class{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", period=" + period +
                ", classroom=" + classroom +
                ", subject=" + subject +
                ", type=" + type +
                ", Date=" + Date +
                '}';
    }
}
