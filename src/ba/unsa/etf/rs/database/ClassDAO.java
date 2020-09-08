package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassDAO
{
    private static ClassDAO instance = null;
    private DatabaseConnection datConn;
    private static void initialize()
    {
        instance = new ClassDAO();
    }

    private PreparedStatement selectClass, findMaxIDClass, addClassQuery, findClassQuery, deleteClass;

    private ClassDAO()
    {
        try
        {
            selectClass = datConn.getConnection().prepareStatement("SELECT * FROM Class");
            findMaxIDClass= datConn.getConnection().prepareStatement("SELECT max(id) FROM Class");
            addClassQuery = datConn.getConnection().prepareStatement("Insert INTO Class values(?,?,?,?,?,?,?)");
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
    public Class findClass(int id) {
        Class result = null;
        try {
            findClassQuery.setInt(1,id);
            ResultSet resultSet = findClassQuery.executeQuery();
            while (resultSet.next()) {
                Class.Type vrsta = Class.Type.values()[resultSet.getInt(7)];
                result = new Class(resultSet.getInt(1),resultSet.getTime(2), resultSet.getTime(3),
                        resultSet.getInt(4), ClassroomDAO.findClassroomByID(resultSet.getInt(5)),
                        SubjectDAO.findSubjectByID(resultSet.getInt(6)),vrsta);
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
