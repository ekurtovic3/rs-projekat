package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.*;
import ba.unsa.etf.rs.model.Class;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ClassDAO
{
    private static  int ID =0 ;
    private static ClassDAO instance = null;
    private DatabaseConnection datConn;
    private static SubjectDAO daoSubject=SubjectDAO.getInstance();
    private ClassroomDAO daoClassroom=ClassroomDAO.getInstance();


    private static void initialize()
    {
        instance = new ClassDAO();
    }

    private  static PreparedStatement selectClass,initializeClassQuery, findMaxIDClass, addClassQuery, findClassQuery, deleteClass,updateClass,isClassFree;

    private ClassDAO()
    {
        try
        {
            initializeClassQuery=datConn.getConnection().prepareStatement("SELECT * FROM Class WHERE date=? AND Classroom=? AND Subject=?");
            selectClass = datConn.getConnection().prepareStatement("SELECT * FROM Class");
            findMaxIDClass= datConn.getConnection().prepareStatement("SELECT max(id) FROM Class");
            addClassQuery = datConn.getConnection().prepareStatement("Insert INTO Class values(?,?,?,?,?,?,?,?)");
            findClassQuery= datConn.getConnection().prepareStatement("SELECT * FROM Class WHERE id = ?");
            deleteClass = datConn.getConnection().prepareStatement("DELETE FROM Class WHERE id = ?");
            updateClass = datConn.getConnection().prepareStatement("update Class set start=?, end= ?, period=?,Classroom =?,Subject=?,Type=?,Date =? where id = ?");
            isClassFree=datConn.getConnection().prepareStatement("SELECT * FROM Class WHERE date=? AND Classroom=? AND Subject=? AND start=?");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static ClassDAO getInstance()
    {
        if(instance == null)
            initialize();

        return instance;
    }

    public static void removeInstance()
    {
        instance = null;
    }

    //METHODS

    public boolean addClass(Class aclass) {
        try  {
            ID = findMaxIDClass() + 1;
            int subjectID = ID;
            addClassQuery.setInt(1,subjectID);
            addClassQuery.setInt(2,aclass.getStart());
            addClassQuery.setInt(3,aclass.getEnd());
            addClassQuery.setInt(4,aclass.getPeriod());
            addClassQuery.setInt(5,aclass.getClassroom().getId());
            addClassQuery.setInt(6,daoSubject.findSubjectID(aclass.getSubject().getName()));
            addClassQuery.setInt(7,aclass.getType().ordinal());
            addClassQuery.setDate(8,aclass.getDate());
            addClassQuery.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void UpdateClass(Class clas, int id){
        try {
            updateClass.setInt(1, clas.getStart());
            updateClass.setInt(2, clas.getEnd());
            updateClass.setInt(3, clas.getPeriod());
            updateClass.setInt(4, clas.getClassroom().getId());
            updateClass.setInt(5, daoSubject.findSubjectID(clas.getSubject().getName()));
            updateClass.setInt(6, clas.getType().ordinal());
            updateClass.setDate(7, clas.getDate());
            updateClass.setInt(8,id);
            updateClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Class findClass(int id) {
        Class result = null;
        try {
            findClassQuery.setInt(1,id);
            ResultSet resultSet = findClassQuery.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result = new Class(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), daoClassroom.findClassroomByID(resultSet.getInt(5)),
                        daoSubject.findSubjectByID(resultSet.getInt(6)), vrsta, resultSet.getDate(8));
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ObservableList<Class> initializeClass(Date date, Classroom classroom, Subject subject) {
        ArrayList<Class> result = new ArrayList<>();
        try {
            initializeClassQuery.setDate(1,date);
            initializeClassQuery.setInt(2,classroom.getId());
            initializeClassQuery.setInt(3,daoSubject.findSubjectID(subject.getName()));
            ResultSet resultSet = initializeClassQuery.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result.add( new Class(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), classroom,
                        subject, vrsta, resultSet.getDate(8))) ;
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    public ObservableList<Class> getAllClass() {
        ArrayList<Class> result = new ArrayList<>();
        try {

            ResultSet resultSet = selectClass.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result.add( new Class(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), daoClassroom.findClassroomByID(resultSet.getInt(5)),
                        daoSubject.findSubjectByID2(resultSet.getInt(6)), vrsta, resultSet.getDate(8))) ;
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    public boolean isClassFree(Date date, Classroom classroom, Subject subject,int start) {
        ArrayList<Class> result = new ArrayList<>();
        try {
            isClassFree.setDate(1,date);
            isClassFree.setInt(2,classroom.getId());
            isClassFree.setInt(3,daoSubject.findSubjectID(subject.getName()));
            isClassFree.setInt(4,start);
            ResultSet resultSet = isClassFree.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result.add( new Class(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), classroom,
                        subject, vrsta, resultSet.getDate(8))) ;
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        if (result.isEmpty()) return true;
        return false;
    }

    private int findMaxIDClass() {
        int result = 0;
        try {
            ResultSet resultSet = findMaxIDClass.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteClass(int id) {
        try {
          //  Class pc = findClass(id);
            deleteClass.setInt(1,id);
            deleteClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void clearAll() {
        try {
            PreparedStatement DeleteAll = datConn.getConnection().prepareStatement("Delete FROM Class ");
            DeleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void defaultData() {
        daoSubject.clearAll();
        daoClassroom.clearAll();
        daoSubject.addSubject(new Subject("UUP"));
        daoSubject.addSubject(new Subject("RPR"));
        daoSubject.addSubject(new Subject("RS"));

       daoClassroom.addClassroom(new Classroom("0-1",10));
        daoClassroom.addClassroom(new Classroom("0-2",10));
        daoClassroom.addClassroom(new Classroom("VA",50));
        Class aclass1=new Class(12,13,0,new Classroom("0-1",10),new Subject("UUP"), Class.Type.Exercises, Date.valueOf(LocalDate.now()));
        Class aclass2=new Class(13,14,0,new Classroom("0-2",10),new Subject("UUP"), Class.Type.Lectures, Date.valueOf(LocalDate.now()));
        Class aclass3=new Class(17,18,0,new Classroom("VA",10),new Subject("RS"), Class.Type.Tutorial, Date.valueOf(LocalDate.now()));

        addClass(aclass1);
        addClass(aclass2);
        addClass(aclass3);
    }
}
