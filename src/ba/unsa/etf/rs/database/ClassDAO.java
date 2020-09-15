package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Class;
import ba.unsa.etf.rs.model.Classroom;
import ba.unsa.etf.rs.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDAO
{
    private static  int ID =0 ;
    private static ClassDAO instance = null;
    private DatabaseConnection datConn;
    private SubjectDAO daoSubject=SubjectDAO.getInstance();
    private ClassroomDAO daoClassroom=ClassroomDAO.getInstance();


    private static void initialize()
    {
        instance = new ClassDAO();
    }

    private PreparedStatement initializeClassQuery,selectClass, findMaxIDClass, addClassQuery, findClassQuery, deleteClass;

    private ClassDAO()
    {
        try
        {

            initializeClassQuery=datConn.getConnection().prepareStatement("SELECT * FROM Class WHERE date=? AND Classroom=? AND Subject=?");
            selectClass = datConn.getConnection().prepareStatement("SELECT * FROM Class");
            findMaxIDClass= datConn.getConnection().prepareStatement("SELECT max(id) FROM Class");
            addClassQuery = datConn.getConnection().prepareStatement("Insert INTO Class values(?,?,?,?,?,?,?,?)");
            findClassQuery= datConn.getConnection().prepareStatement("SELECT * FROM Class WHERE id = ?");
            deleteClass = datConn.getConnection().prepareStatement("DELETE FROM User WHERE id = ?");
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



    public Class findClass(int id) {
        Class result = null;
        try {
            findClassQuery.setInt(1,id);
            ResultSet resultSet = findClassQuery.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result = new Class(resultSet.getInt(1),resultSet.getInt(2), resultSet.getInt(3),
                        resultSet.getInt(4), ClassroomDAO.findClassroomByID(resultSet.getInt(5)),
                        SubjectDAO.findSubjectByID(resultSet.getInt(6)), vrsta, resultSet.getDate(8));
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
            Class pc = findClass(id);
            deleteClass.setInt(1,pc.getId());
            deleteClass.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
