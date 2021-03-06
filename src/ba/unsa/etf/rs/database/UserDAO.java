package ba.unsa.etf.rs.database;

import ba.unsa.etf.rs.exceptions.InvalidParam;
import ba.unsa.etf.rs.exceptions.ObjectAlredyExist;
import ba.unsa.etf.rs.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UserDAO
{
    private static UserDAO instance = null;
    private DatabaseConnection datConn;
    private static void initialize()
    {
        instance = new UserDAO();
    }
    private static int ID;

    private static PreparedStatement selectUsers, findUserQuery, findMaxIDUser, deleteUser, findUserByIDQuery, findUserLogIn,
            updateUser, findUserID, addUserQuery,EditUserExistQuery,AddUserExistQuery;

    private UserDAO()
    {
        try
        {
            selectUsers = datConn.getConnection().prepareStatement("SELECT * FROM User");
            findUserQuery= datConn.getConnection().prepareStatement("SELECT * FROM User WHERE jmbg = ?");
            findMaxIDUser= datConn.getConnection().prepareStatement("SELECT max(id) FROM User");
            deleteUser = datConn.getConnection().prepareStatement("DELETE FROM User WHERE jmbg = ?");
            findUserByIDQuery = datConn.getConnection().prepareStatement("SELECT * FROM User WHERE id = ?");
           findUserLogIn= datConn.getConnection().prepareStatement("SELECT id FROM User WHERE username = ? and password=?");
            updateUser = datConn.getConnection().prepareStatement("update USER set name=?, surname= ?, email=?, jmbg=?,username =?,dateOfBirth=?,status=? where id = ?");
            findUserID =datConn.getConnection().prepareStatement("SELECT id from User where jmbg= ?");
            addUserQuery = datConn.getConnection().prepareStatement("Insert INTO User values(?,?,?,?,?,?,?,?,?)");
            EditUserExistQuery= datConn.getConnection().prepareStatement("SELECT id FROM User WHERE (username = ? OR jmbg=?) and id<>?");
            AddUserExistQuery= datConn.getConnection().prepareStatement("SELECT id FROM User WHERE (username = ? OR jmbg=?)");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static UserDAO getInstance()
    {
        if(instance == null)
            initialize();

        return instance;
    }

    public static void removeInstance()
    {
        instance = null;
    }


    public static ObservableList<Profesor> getAllProfesors() {
        ArrayList<Profesor> result = new ArrayList<>();
        try {
            ResultSet resultSet = selectUsers.executeQuery();
            while (resultSet.next())
                if(resultSet.getInt(8) ==2)
                { result.add(new Profesor(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(result);
    }

    public  void UpdateUser(@NotNull User user){
        try {
            updateUser.setString(1, user.getName());
            updateUser.setString(2, user.getSurname());
            updateUser.setString(3, user.getEmail());
            updateUser.setString(4, user.getJmbg());
            updateUser.setString(5, user.getUsername());
            updateUser.setDate(6, user.getDateOfBirth());
            if(user instanceof Student) updateUser.setInt(7, 1);
            if(user instanceof Profesor) updateUser.setInt(7,2 );
            if(user instanceof Admin) updateUser.setInt(7,0 );
            //updateUser.setString(8, pass);
            updateUser.setInt(8, findUserID(user.getJmbg()));
            updateUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean addUser(User s,String pass) {
        try  {
            User s1 = findUser(s.getName());
            if (s1 == null) {
                ID = findMaxIDUser() + 1;
                int userID = ID;
                addUserQuery.setInt(1,userID );
                addUserQuery.setString(2, s.getName());
                addUserQuery.setString(3, s.getSurname());
                addUserQuery.setString(4, s.getEmail());
                addUserQuery.setString(5, s.getJmbg());
                addUserQuery.setString(6, s.getUsername());
                addUserQuery.setDate(7, s.getDateOfBirth());
                addUserQuery.setString(9, pass);
                if(s instanceof Profesor) {addUserQuery.setInt(8, 2);}
                else if(s instanceof Student) {addUserQuery.setInt(8, 1);}
                else {addUserQuery.setInt(8, 0);}

                addUserQuery.executeUpdate();
                return true;
            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int findMaxIDUser() {
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

    public static User findUser(String userJmbg) {
        User result = null;
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

    public static int findUserLogIn(String username,String pass) throws InvalidParam {
        int result = -1;
        try {
            findUserLogIn.setString(1,username);
            findUserLogIn.setString(2,pass);
            ResultSet resultSet = findUserLogIn.executeQuery();
            while (resultSet.next())
             result=resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(result == -1){
            throw new InvalidParam("Username and password did not match");
        }
        return result;
    }

    public  int EditUserExist(String username,String jmbg,int id) throws ObjectAlredyExist {
        int result = -1;
        try {
            EditUserExistQuery.setString(1,username);
            EditUserExistQuery.setString(2,jmbg);
            EditUserExistQuery.setInt(3,id);
            ResultSet resultSet = EditUserExistQuery.executeQuery();
            while (resultSet.next())
                result=resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(result != -1){
            throw new ObjectAlredyExist("Username or password alredy exist.");
        }
        return result;
    }
    public  int AddUserExist(String username,String jmbg) throws  ObjectAlredyExist {
        int result = -1;
        try {
            AddUserExistQuery.setString(1,username);
            AddUserExistQuery.setString(2,jmbg);
            ResultSet resultSet = AddUserExistQuery.executeQuery();
            while (resultSet.next())
                result=resultSet.getInt(1);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(result != -1){
            throw new ObjectAlredyExist("Username or password alredy exist.");
        }
        return result;
    }
    public static int findUserID(String userJmbg) {
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
    public  int findUserID2(String userJmbg) {
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
else  if(resultSet.getInt(8) ==0) { result.add(new Admin(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
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

    public static  User findUserByID(int id) {
        User result = null;
        try {
            findUserByIDQuery.setInt(1,id);
            ResultSet resultSet = findUserByIDQuery.executeQuery();
            while (resultSet.next())
                if(resultSet.getInt(8) ==2)
                { result=(new Profesor(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==1)
                { result=(new Student(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==0)
                { result=(new Admin(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public  User findUserByID2(int id) {
        User result = null;
        try {
            findUserByIDQuery.setInt(1,id);
            ResultSet resultSet = findUserByIDQuery.executeQuery();
            while (resultSet.next())
                if(resultSet.getInt(8) ==2)
                { result=(new Profesor(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==1)
                { result=(new Student(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}
                else  if(resultSet.getInt(8) ==2)
                { result=(new Admin(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getDate(7)));}

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void clearAll() {
        try {
            PreparedStatement DeleteAll = datConn.getConnection().prepareStatement("Delete FROM User ");
            DeleteAll.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
public void defaultData() {
        addUser(new Admin("Vedran","Ljubovic","evedram@gmail.com","0202995170029","admin", Date.valueOf(LocalDate.of(1995,2,2))),"admin");
    addUser(new Profesor("Dzan","Tabakovic","tabak@gmail.com","0505995170021","profesor", Date.valueOf(LocalDate.of(1995,5,5))),"profesor");
    addUser(new Student("Emir","Kurtovic","ekurtovic3@etf.unsa.ba","0404998170021","student", Date.valueOf(LocalDate.of(1998,4,4))),"student");

    }
}
