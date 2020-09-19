package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectDAO
{
    private static int ID;

    private static SubjectDAO instance = null;
    private DatabaseConnection datConn;
    private static void initialize()
    {
        instance = new SubjectDAO();
    }

    private static PreparedStatement selectSubjects, addSubjectQuery, findSubjectQuery, findMaxIDSubject, deleteSubject,findSubjectByIDQuery;

    private SubjectDAO()
    {
        try
        {   findSubjectByIDQuery= datConn.getConnection().prepareStatement("SELECT name FROM Subject WHERE id = ?");
            selectSubjects = datConn.getConnection().prepareStatement("SELECT * FROM Subject");
            addSubjectQuery = datConn.getConnection().prepareStatement("INSERT INTO Subject values (?,?)");
            findSubjectQuery = datConn.getConnection().prepareStatement("SELECT * FROM Subject WHERE name = ?");
            findMaxIDSubject = datConn.getConnection().prepareStatement("SELECT max(id) FROM Subject");
            deleteSubject = datConn.getConnection().prepareStatement("DELETE FROM Subject WHERE name = ?");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static SubjectDAO getInstance()
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

    public Subject findSubject(String subjectName) {
    Subject result = null;
    try {
        findSubjectQuery.setString(1,subjectName);
        ResultSet resultSet = findSubjectQuery.executeQuery();
        while (resultSet.next())
            result = new Subject(resultSet.getString(2));
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
}

    public  int findSubjectID(String subjectName) {
        int result=0;
        try {
            findSubjectQuery.setString(1,subjectName);
            ResultSet resultSet = findSubjectQuery.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int findMaxIDSubject() {
        int result = 0;
        try {
            ResultSet resultSet = findMaxIDSubject.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean addSubject(Subject sub) {
        try  {
            ID = findMaxIDSubject() + 1;
            int subjectID = ID;
            addSubjectQuery.setInt(1,subjectID);
            addSubjectQuery.setString(2, sub.getName());
            addSubjectQuery.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Subject> getAllSubjects() {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectSubjects.executeQuery();
            while (resultSet.next())
                result.add(new Subject(resultSet.getString(2)));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }


    public ArrayList<Subject> getAllSubjectsXML() {
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectSubjects.executeQuery();
            while (resultSet.next())
                result.add(new Subject(resultSet.getString(2)));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteSubject(String s) {
        try {
            Subject ps= findSubject(s);
            deleteSubject.setString(1,ps.getName());
            deleteSubject.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Subject findSubjectByID(int id) {
        Subject result = null;
        try {
            findSubjectQuery.setInt(1,id);
            ResultSet resultSet = findSubjectQuery.executeQuery();
            while (resultSet.next())
                result = new Subject(resultSet.getString(2));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public  Subject findSubjectByID2(int id) {
        Subject result = null;
        try {
            findSubjectByIDQuery.setInt(1,id);
            ResultSet resultSet = findSubjectByIDQuery.executeQuery();
            while (resultSet.next())
                result = new Subject(resultSet.getString(1));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void clearAll() {
        try {
            PreparedStatement DeleteAll = datConn.getConnection().prepareStatement("Delete FROM Subject ");
            DeleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void defaultData() {
        addSubject(new Subject("UUP"));
        addSubject(new Subject("RPR"));
        addSubject(new Subject("RS"));

    }
}
