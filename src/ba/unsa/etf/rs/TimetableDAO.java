package ba.unsa.etf.rs;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TimetableDAO{
    private static TimetableDAO instance = null;
    private  static Connection conn=null;
    private static int ID;
    private PreparedStatement selectSubjects, selectClassrooms,selectUsers,selectClass;
    private PreparedStatement addSubjectQuery, addClassroomQuery,addUserQuery,addClassQuery, findClassroomQuery, findSubjectQuery,findUserQuery,findClassQuery;
    private PreparedStatement findMaxIDClassroom,findMaxIDClass, findMaxIDSubject,findMaxIDUser;
    private PreparedStatement deleteClassroom, deleteSubject,deleteUser,deleteClass;
    private PreparedStatement findClassroomByIDQuery,findClassByIDQuery;



    //PredmetDAO

    public void defaultSubject() {
        addSubject(new Subject("Fizika"));
        addSubject(new Subject("Matematika"));
        addSubject(new Subject("asd"));

    }
    public void TESTdeleteSubject() {
        deleteSubject("Fizika");
        deleteSubject("Matematika");
        deleteSubject("asd");
    }
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
    public int findSubjectID(String subjectName) {
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
            //Subject s = findSubject(sub.getName());
            ID = findMaxIDSubject() + 1;
            int subjectID = ID;
            //   sub.setId(subjectID);
            addSubjectQuery.setInt(1,subjectID);
            addSubjectQuery.setString(2, sub.getName());
            addSubjectQuery.executeUpdate();
            System.out.println(sub.getName() + " " + ID);
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
    public Subject findSubjectByID(int id) {
        Subject result = null;
        try {
            findClassByIDQuery.setInt(1,id);
            ResultSet resultSet = findClassByIDQuery.executeQuery();
            while (resultSet.next())
                result = new Subject(resultSet.getString(2));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    ///// Ostalo
        public static void deleteInstance(){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            instance = null;
        }
        private void regenerationBase(){
            Scanner ulaz = null;
            try {
                ulaz = new Scanner(new FileInputStream("base.db.sql"));
                String sqlUpit = "";
                while (ulaz.hasNext()) {
                    sqlUpit += ulaz.nextLine();
                    if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
                        try {
                            Statement stmt = conn.createStatement();
                            stmt.execute(sqlUpit);
                            sqlUpit = "";
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                ulaz.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

