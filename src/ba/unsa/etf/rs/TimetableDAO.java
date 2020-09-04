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
    private PreparedStatement findClassroomByIDQuery,findClassByIDQuery,findUserByIDQuery;
    private PreparedStatement updateUser,findUserID;
    private PreparedStatement addProfesorToSubject,deleteProfesorToSubject,selectProfesorsForAdd,selectProfesorsOfSubject;


    public TimetableDAO(TimetableDAO dao) {

    }

    public static TimetableDAO getInstance() throws SQLException {
        if (instance == null) instance = new TimetableDAO();
        return instance;
    }

    private TimetableDAO() throws SQLException {
        try {

            conn = DriverManager.getConnection("jdbc:sqlite:base.db");
            selectClassrooms = conn.prepareStatement("SELECT * FROM Classroom");
            selectSubjects = conn.prepareStatement("SELECT * FROM Subject");
            selectUsers = conn.prepareStatement("SELECT * FROM User");
            selectClass = conn.prepareStatement("SELECT * FROM Class");
            addClassroomQuery = conn.prepareStatement("INSERT INTO Classroom values (?,?,?)");
            addSubjectQuery = conn.prepareStatement("INSERT INTO Subject values (?,?)");
            addUserQuery = conn.prepareStatement("Insert INTO User values(?,?,?,?,?,?,?,?)");
            addClassQuery = conn.prepareStatement("Insert INTO Class values(?,?,?,?,?,?,?)");
            findClassroomQuery = conn.prepareStatement("SELECT * FROM Classroom WHERE name =?");
            findSubjectQuery = conn.prepareStatement("SELECT * FROM Subject WHERE name = ?");
            findUserQuery= conn.prepareStatement("SELECT * FROM User WHERE jmbg = ?");
            findClassQuery= conn.prepareStatement("SELECT * FROM Class WHERE id = ?");
            findMaxIDClassroom = conn.prepareStatement("SELECT max(id) FROM Classroom");
            findMaxIDSubject = conn.prepareStatement("SELECT max(id) FROM Subject");
            findMaxIDUser= conn.prepareStatement("SELECT max(id) FROM User");
            findMaxIDClass= conn.prepareStatement("SELECT max(id) FROM Class");
            findUserID =conn.prepareStatement("SELECT id from User where jmbg= ?");
            deleteClassroom = conn.prepareStatement("DELETE FROM Classroom WHERE id = ?");
            deleteSubject = conn.prepareStatement("DELETE FROM Subject WHERE name = ?");
            deleteUser = conn.prepareStatement("DELETE FROM User WHERE jmbg = ?");
            deleteClass = conn.prepareStatement("DELETE FROM User WHERE id = ?");
            updateUser = conn.prepareStatement("update USER set name=?, surname= ?, email=?, jmbg=?,username =?,dateOfBirth=?,status=? where id = ?");

            selectProfesorsOfSubject = conn.prepareStatement("SELECT idp FROM ProfesorSubject Where ids=?");
            selectProfesorsForAdd = conn.prepareStatement("SELECT idp FROM ProfesorSubject where  ids<>?");
            addProfesorToSubject = conn.prepareStatement("INSERT INTO ProfesorSubject VALUES(?,?)");
            deleteProfesorToSubject =conn.prepareStatement("DELETE FROM ProfesorSubject WHERE idp = ? and ids=?");

            findClassroomByIDQuery = conn.prepareStatement("SELECT * FROM Classroom WHERE id = ?");
            findClassByIDQuery = conn.prepareStatement("SELECT * FROM Class WHERE id = ?");
            findUserByIDQuery = conn.prepareStatement("SELECT * FROM User WHERE id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//ClassDAO
    public boolean addClass(Class c) {
        try  {
            Class c1 = findClass(c.getId());
            if (c1 == null) {
                ID = findMaxIDClass() + 1;
                int classID = ID;
                //   room.setId(classroomID);
                addClassQuery.setInt(1,classID );
                addClassQuery.setTime(2, c1.getStart());
                addClassQuery.setTime(3, c1.getEnd());
                addClassQuery.setInt(4, c1.getPeriod());
                addClassQuery.setInt(5, c1.getClassroom().getId());
                addClassQuery.setInt(6, findSubjectID(c1.getSubject().getName()));
                addClassQuery.setInt(7, c1.getType().ordinal());

                addUserQuery.executeUpdate();
                //   System.out.println(s.getName() + " "+ s.getSurname()+" "+s.getEmail());
                return true;
            }

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
                result = new Class(resultSet.getInt(1),resultSet.getTime(2), resultSet.getTime(3), resultSet.getInt(4), findClassroomByID(resultSet.getInt(5)), findSubjectByID(resultSet.getInt(6)),vrsta);
            }}
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private int findMaxIDClass() {
        int result = 0;
        try {
            ResultSet resultSet = findMaxIDUser.executeQuery();
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

//UserDAO

    //Za test
    public void defaultUser() {
        addUser(new Student("Emir", "Kurtovic","kurtovic@gmail.com","0404888170254","asd",Date.valueOf(LocalDate.now())));
        addUser(new Profesor("Azra", "Balic","awdawd@wadad.com","18115615651","wasd",Date.valueOf(LocalDate.now())));
        addUser(new Student("A", "B","C","15465464","n15a",Date.valueOf(LocalDate.now())));
    }
    public void TESTdeleteUser() {
        //  deleteUser("0404888170254");
        // deleteUser("18115615651");
        deleteUser("15465464");
    }
    public void UpdateUser(User user){
       // User trenutni = this.getTrenutniKorisnik();
        try {
            updateUser.setString(1, user.getName());
            updateUser.setString(2, user.getSurname());
            updateUser.setString(3, user.getEmail());
            updateUser.setString(4, user.getJmbg());
            updateUser.setString(5, user.getUsername());
            updateUser.setDate(6, user.getDateOfBirth());
            if(user instanceof Student) updateUser.setInt(7,2 );
            if(user instanceof Profesor) updateUser.setInt(7,1 );
            updateUser.setInt(8, findUserID(user.getJmbg()));
            updateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User s) {
        try  {
            User s1 = findUser(s.getName());
            if (s1 == null) {
                ID = findMaxIDUser() + 1;
                int userID = ID;
                //   room.setId(classroomID);
                addUserQuery.setInt(1,userID );
                addUserQuery.setString(2, s.getName());
                addUserQuery.setString(3, s.getSurname());
                addUserQuery.setString(4, s.getEmail());
                addUserQuery.setString(5, s.getJmbg());
                addUserQuery.setString(6, s.getUsername());
                addUserQuery.setDate(7, s.getDateOfBirth());
                if(s instanceof Profesor) {addUserQuery.setInt(8, 2);}
                else if(s instanceof Student) {addUserQuery.setInt(8, 1);}
                else {addUserQuery.setInt(8, 0);}
                /*  UBACIT JOS ZA ADMINA*/
                addUserQuery.executeUpdate();
                System.out.println(s.getName() + " "+ s.getSurname()+" "+s.getEmail());
                return true;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int findMaxIDUser() {
        int result = 0;
        try {
            ResultSet resultSet = findMaxIDUser.executeQuery();
            while (resultSet.next())
                result = resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User findUser(String userJmbg) {
        User result = null;                 /* VRACA PROFESORA ILI STUDENTA A NE USER??????????*/
        try {
            findUserQuery.setString(1,userJmbg);
            ResultSet resultSet = findUserQuery.executeQuery();
            while (resultSet.next())
                result = new User(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getDate(7));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public int findUserID(String userJmbg) {
        int id=0;
        try {
            findUserID.setString(1, userJmbg);
            ResultSet resultSet = findUserID.executeQuery();
             id=resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }



        return id;
    }

    public void deleteUser(String s) {
        try {
            User pu = findUser(s);
            deleteUser.setString(1,pu.getJmbg());
            deleteUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public ObservableList<User> getAllUsers() { // DODAT ADMINA i indeks
        ArrayList<User> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectUsers.executeQuery();
            while (resultSet.next())
                if(resultSet.getInt(8) ==2)
                { result.add(new Profesor(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==1)
                { result.add(new Student(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}

            //      else {addUserQuery.setInt(8, 0);}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }
    public ObservableList<User> getAllSpecificUsers(int i) { // DODAT ADMINA i indeks
        ArrayList<User> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectUsers.executeQuery();
            while (resultSet.next())
                if(resultSet.getInt(8) ==2 &&  i==2)
                { result.add(new Profesor(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==1 && i==1)
                { result.add(new Student(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}

            //      else {addUserQuery.setInt(8, 0);}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }


    public Profesor findUserByID(int id) {
        Profesor result = null;
        try {
            findUserByIDQuery.setInt(1,id);
            ResultSet resultSet = findUserByIDQuery.executeQuery();
            while (resultSet.next())
                result = new Profesor(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),Date.valueOf(LocalDate.now()));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    //SalaDAO
    public void defaultClass() {
        addClassroom(new Classroom("2-0", 50));
        addClassroom(new Classroom("2-a", 60));
        addClassroom(new Classroom("asd", 500));
    }
    public void TESTdeleteClassroom() {
        deleteClassroom("2-0");
        deleteClassroom("2-a");
        deleteClassroom("asd");
    }

    public boolean addClassroom(Classroom room) {
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
                System.out.println(room.getName() + " "+ room.getCapacity());
                return true;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int findMaxIDClassroom() {
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

    public Classroom findClassroom(String classroomName) {
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

    public int findClassroomID(String classroomName) {
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
    public Classroom findClassroomByID(int id) {
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
    public ObservableList<Classroom> getAllClassrooms() {
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

//ProfesorToSubjectDAO
public ObservableList<Profesor> getProfesorsOfSubject(Subject subject) throws SQLException {
    selectProfesorsOfSubject.setInt(1,findSubjectID(subject.getName()));
    ArrayList<Profesor> result = new ArrayList<>();
    try {

        ResultSet resultSet = selectProfesorsOfSubject.executeQuery();
        while (resultSet.next()){
        System.out.println(findUserByID(resultSet.getInt(1)).toString()            );
            if (findUserByID(resultSet.getInt(1)) instanceof Profesor) result.add(findUserByID(resultSet.getInt(1)));}
        //  result.add(new User( findUserByID(resultSet.getInt(1))));
    }
    catch (SQLException e) {
        e.printStackTrace();
    }
    return FXCollections.observableArrayList(result);
}
    public ObservableList<Profesor> getProfesorsForAdd(Subject subject) throws SQLException {
        selectProfesorsForAdd.setInt(1,findSubjectID(subject.getName()));
        ArrayList<Profesor> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectProfesorsForAdd.executeQuery();
            while (resultSet.next()){

                result.add(findUserByID(resultSet.getInt(1)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }

public  void addProfesorToSubject(int id1,int id2){
        try {
            addProfesorToSubject.setInt(1,id1);
            addProfesorToSubject.setInt(2,id2);
            addProfesorToSubject.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void deleteProfesorToSubject(int id1,int id2){
        try {
            deleteProfesorToSubject.setInt(1,id1);
            deleteProfesorToSubject.setInt(2,id2);
            deleteProfesorToSubject.executeUpdate();
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void setTrenutniSubject(Subject newKorisnik) {    }
    public User getTrenutniKorisnik() {
        return trenutniKorisnik.get();
    }

    public SimpleObjectProperty<User> trenutniKorisnikProperty() {
        return trenutniKorisnik;
    }

    public void setTrenutniKorisnik(User trenutniKorisnik) {
        this.trenutniKorisnik.set(trenutniKorisnik);
    }

    private SimpleObjectProperty<User> trenutniKorisnik = new SimpleObjectProperty<>();

    public TimetableDAO(SimpleObjectProperty<User> trenutniSubject) {
        this.trenutniSubject = trenutniSubject;
    }

    public User getTrenutniSubject() {
        return trenutniSubject.get();
    }

    public SimpleObjectProperty<User> trenutniSubjectProperty() {
        return trenutniSubject;
    }

    public void setTrenutniSubject(User trenutniSubject) {
        this.trenutniSubject.set(trenutniSubject);
    }
    private SimpleObjectProperty<User> trenutniSubject = new SimpleObjectProperty<>();


}

