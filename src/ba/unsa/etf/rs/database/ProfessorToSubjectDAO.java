package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.model.Profesor;
import ba.unsa.etf.rs.model.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfessorToSubjectDAO
{
    private static ProfessorToSubjectDAO instance = null;
    private DatabaseConnection datConn;
    private SubjectDAO daoSubject=SubjectDAO.getInstance();
    private static void initialize()
    {
        instance = new ProfessorToSubjectDAO();
    }

    private PreparedStatement addProfesorToSubject,deleteProfesorToSubject,selectProfesorsForAdd,selectProfesorsOfSubject;

    private ProfessorToSubjectDAO()
    {
        try {
            selectProfesorsOfSubject = datConn.getConnection().prepareStatement("SELECT idp FROM ProfesorSubject Where ids=?");
            selectProfesorsForAdd = datConn.getConnection().prepareStatement("SELECT idp FROM ProfesorSubject where  ids!=?");
            addProfesorToSubject = datConn.getConnection().prepareStatement("INSERT INTO ProfesorSubject VALUES(?,?)");
            deleteProfesorToSubject =datConn.getConnection().prepareStatement("DELETE FROM ProfesorSubject WHERE idp = ? and ids=?");
        } catch (SQLException e) {
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
    public ObservableList<Profesor> getProfesorsOfSubject(Subject subject) throws SQLException {
        selectProfesorsOfSubject.setInt(1,daoSubject.findSubjectID(subject.getName()));
        ArrayList<Profesor> result = new ArrayList<>();
        try {

            ResultSet resultSet = selectProfesorsOfSubject.executeQuery();
            while (resultSet.next()){
                if (UserDAO.findUserByID(resultSet.getInt(1)) instanceof Profesor)
                    result.add((Profesor) UserDAO.findUserByID(resultSet.getInt(1)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    public ObservableList<Profesor> getProfesorsForAdd(Subject subject) throws SQLException {
        //  ArrayList<Profesor> result = new ArrayList<>();
        ObservableList<Profesor> allProfesors= UserDAO.getAllProfesors();
        ObservableList<Profesor> profesorsOfSubject= getProfesorsOfSubject(subject);
        // profesorsOfSubject.removeAll(allProfesors);
        //List<Profesor> result=null;
        List<Profesor> result = allProfesors.stream().filter(aObject -> {return  profesorsOfSubject.contains(aObject); }).collect(Collectors.toList());
        for (int i=0;i<result.size();i++) {
            System.out.println("Profesor za dodavanje:"+result.get(i));

        }
        return  FXCollections.observableArrayList(result);
    }

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
}
