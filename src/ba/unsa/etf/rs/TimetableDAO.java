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

