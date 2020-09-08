package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Classroom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassroomDAO
{
    private static ClassroomDAO instance = null;
    private DatabaseConnection datConn;
    private static void initialize()
    {
        instance = new ClassroomDAO();
    }

    private static PreparedStatement selectClassrooms, findClassroomQuery, addClassroomQuery, findMaxIDClassroom, deleteClassroom,
            findClassroomByIDQuery;

    private static int ID;

    private ClassroomDAO()
    {
        try
        {
            //nadjiDrzavuPoIdu = datConn.getConnection().prepareStatement("SELECT * FROM drzava WHERE id = ?");
            selectClassrooms = datConn.getConnection().prepareStatement("SELECT * FROM Classroom");
            findClassroomQuery = datConn.getConnection().prepareStatement("SELECT * FROM Classroom WHERE name =?");
            addClassroomQuery = datConn.getConnection().prepareStatement("INSERT INTO Classroom values (?,?,?)");
            findMaxIDClassroom = datConn.getConnection().prepareStatement("SELECT max(id) FROM Classroom");
            deleteClassroom = datConn.getConnection().prepareStatement("DELETE FROM Classroom WHERE id = ?");
            findClassroomByIDQuery = datConn.getConnection().prepareStatement("SELECT * FROM Classroom WHERE id = ?");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static ClassroomDAO getInstance()
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
    public void TESTdeleteClassroom() {
        deleteClassroom("2-0");
        deleteClassroom("2-a");
        deleteClassroom("asd");
    }

    public static boolean addClassroom(Classroom room) {
        try  {
            Classroom cl = findClassroom(room.getName());
            if (cl == null) {
                ID = findMaxIDClassroom() + 1;
                int classroomID = ID;
                //  room.setId(classroomID);
                addClassroomQuery.setInt(1, classroomID);
                addClassroomQuery.setString(2, room.getName());
                addClassroomQuery.setInt(3, room.getCapacity());
                addClassroomQuery.executeUpdate();
                //        System.out.println(room.getName() + " "+ room.getCapacity());
                return true;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int findMaxIDClassroom() {
        int result = 0;
        try {
            ResultSet resultSet = findMaxIDClassroom.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Classroom findClassroom(String classroomName) {
        Classroom result = null;
        try {
            findClassroomQuery.setString(1,classroomName);
            ResultSet resultSet = findClassroomQuery.executeQuery();
            while (resultSet.next())
                result = new Classroom(resultSet.getString(1),resultSet.getInt(2));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int findClassroomID(String classroomName) {
        int result = 0;
        try {
            findClassroomQuery.setString(1,classroomName);
            ResultSet resultSet = findClassroomQuery.executeQuery();
            result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    static public Classroom findClassroomByID(int id) {
        Classroom result = null;
        try {
            findClassroomByIDQuery.setInt(1,id);
            ResultSet resultSet = findClassroomByIDQuery.executeQuery();
            while (resultSet.next())
                result = new Classroom(resultSet.getString(1),resultSet.getInt(2));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteClassroom(String c) {
        try {
            // Classroom cl = findClassroom(c.getName());
            deleteClassroom.setInt(1, findClassroomID(c));
            deleteClassroom.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ObservableList<Classroom> getAllClassrooms() {
        ArrayList<Classroom> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectClassrooms.executeQuery();
            while (resultSet.next())
                result.add(new Classroom(resultSet.getString(2), resultSet.getInt(3)));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
}
