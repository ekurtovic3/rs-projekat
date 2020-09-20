package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProfessorToSubjectDAO
{
    private static ProfessorToSubjectDAO instance = null;
    private DatabaseConnection datConn;
    private SubjectDAO daoSubject=SubjectDAO.getInstance();
    private ClassroomDAO daoClassroom=ClassroomDAO.getInstance();
    private UserDAO daoUser=UserDAO.getInstance();

    private static void initialize()
    {
        instance = new ProfessorToSubjectDAO();
    }

    private PreparedStatement addProfesorToSubject,deleteProfesorToSubject,selectProfesorsForAdd,selectProfesorsOfSubject, selectUsersOfSubject,
        professorsNotOnSubject, studentssNotOnSubject, getAllFromProfessorToSubjectTable, isUserOnSubject;

    private ProfessorToSubjectDAO()
    {
        try
        {
            selectUsersOfSubject = datConn.getConnection().prepareStatement("SELECT ids FROM ProfesorSubject Where idp=?");
            selectProfesorsOfSubject = datConn.getConnection().prepareStatement("SELECT idp FROM ProfesorSubject Where ids=?");
            selectProfesorsForAdd = datConn.getConnection().prepareStatement("SELECT idp FROM ProfesorSubject where  ids!=?");
            addProfesorToSubject = datConn.getConnection().prepareStatement("INSERT INTO ProfesorSubject VALUES(?,?)");
            deleteProfesorToSubject =datConn.getConnection().prepareStatement("DELETE FROM ProfesorSubject WHERE idp = ? and ids=?");
            professorsNotOnSubject = datConn.getConnection().prepareStatement(
                    "SELECT u.id FROM ProfesorSubject ps, User u, Subject s WHERE u.status= 2 AND s.id!=?");
            studentssNotOnSubject = datConn.getConnection().prepareStatement(
                    "SELECT u.id FROM ProfesorSubject ps, User u, Subject s WHERE u.status = 1 AND s.id!=?");
            getAllFromProfessorToSubjectTable = datConn.getConnection().prepareStatement("SELECT * FROM ProfesorSubject");
            isUserOnSubject = datConn.getConnection().prepareStatement("SELECT * FROM ProfesorSubject WHERE idp = ? AND ids=?");

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }



    }

    public static ProfessorToSubjectDAO getInstance()
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
    public boolean isUserOnSubject(User professor, Subject subject)
    {
        try
        {
            isUserOnSubject.setInt(1, daoUser.findUserID2(professor.getJmbg()));
            isUserOnSubject.setInt(2, daoSubject.findSubjectID(subject.getName()));

            ResultSet resultSet = isUserOnSubject.executeQuery();
            while (resultSet.next())
            {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ObservableList<User> getUsersOfSubject(Subject subject) throws SQLException {
        selectProfesorsOfSubject.setInt(1,daoSubject.findSubjectID(subject.getName()));
        ArrayList<User> result = new ArrayList<>();
        try {

            ResultSet resultSet = selectProfesorsOfSubject.executeQuery();
            while (resultSet.next()){
                    result.add(UserDAO.findUserByID(resultSet.getInt(1)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    public ObservableList<Subject> getSubjectsOfStudent(int ids) throws SQLException {
        selectUsersOfSubject.setInt(1,ids);
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectUsersOfSubject.executeQuery();
            while (resultSet.next()){
                result.add(daoSubject.findSubjectByID2(resultSet.getInt(1)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }

    public ObservableList<Subject> getSubjectOfProfesor(int idp) throws SQLException {
        selectUsersOfSubject.setInt(1,idp);
        ArrayList<Subject> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectUsersOfSubject.executeQuery();
            while (resultSet.next()){
                result.add(daoSubject.findSubjectByID2(resultSet.getInt(1)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    /*
    public ObservableList<Profesor> getProfesorsForAdd(Subject subject) throws SQLException {
        //  ArrayList<Profesor> result = new ArrayList<>();
        ObservableList<Profesor> allProfesors= UserDAO.getAllProfesors();
       // ObservableList<Profesor> profesorsOfSubject= getProfesorsOfSubject(subject);
        // profesorsOfSubject.removeAll(allProfesors);
        //List<Profesor> result=null;
        //List<Profesor> result = allProfesors.stream().filter(aObject -> {return  profesorsOfSubject.contains(aObject); }).collect(Collectors.toList());
        for (int i=0;i<result.size();i++) {
            System.out.println("Profesor za dodavanje:"+result.get(i));

        }
        return  FXCollections.observableArrayList(result);
    }
*/
    public  void addProfesorToSubject(int id1,int id2){
        try {
            addProfesorToSubject.setInt(1,id1);
            addProfesorToSubject.setInt(2,id2);
            addProfesorToSubject.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void deleteProfesorToSubject(int idP,int idS){
        try {
            deleteProfesorToSubject.setInt(1,idP);
            deleteProfesorToSubject.setInt(2,idS);
            deleteProfesorToSubject.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearAll()
    {
        try
        {
            PreparedStatement DeleteAll = datConn.getConnection().prepareStatement("Delete FROM ProfesorSubject ");
            DeleteAll.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public void defaultData()
    {
        daoSubject.clearAll();
        daoSubject.defaultData();
        daoClassroom.clearAll();
        daoClassroom.defaultData();
        addProfesorToSubject(2,1);
        addProfesorToSubject(2,2);
        addProfesorToSubject(3,1);
        addProfesorToSubject(3,2);

    }

    public  ObservableList<User> getAllProfessorsThatAreNoProfessorsOnASubject(Subject subject)
    {
        ArrayList<User> result = new ArrayList<>();
        ObservableList<User> allProfessors = daoUser.getAllSpecificUsers(2);

        try
        {
            for(int i=0; i<allProfessors.size(); i++)
            {
                if(isUserOnSubject(allProfessors.get(i), subject) == false)
                {
                    result.add(allProfessors.get(i));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(result);
    }

    public  ObservableList<User> getAllStudentsThatAreNotOnASubject(Subject subject)
    {
        ArrayList<User> result = new ArrayList<>();
        ObservableList<User> allStudents = daoUser.getAllSpecificUsers(1);

        try
        {
            for(int i=0; i<allStudents.size(); i++)
            {
                if(isUserOnSubject(allStudents.get(i), subject) == false)
                {
                    result.add(allStudents.get(i));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return FXCollections.observableArrayList(result);
    }
}
