package ba.unsa.etf.rs.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseConnection
{
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            TestDatabase();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            InitializeDatabase();
        }
    }

    private void TestDatabase()
    {
        try
        {
            Statement s = connection.createStatement();
            s.execute("select * from drzava");
            System.out.println("Database exists...");
        }
        catch (SQLException e)
        {
            InitializeDatabase();
        }
    }

    private void InitializeDatabase()
    {
        try
        {
            Scanner sc = new Scanner(new FileReader("database_init.db.sql")).useDelimiter(";");

            while(sc.hasNext())
            {
                String nextStat = sc.next();

                if(!nextStat.trim().isEmpty())
                {
                    Statement s = connection.createStatement();
                    s.execute(nextStat);
                }
            }

            System.out.println("Database initialization complete...");
        }
        catch (FileNotFoundException | SQLException e)
        {
            System.out.println("Error initializing database!");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance()
    {
        if(instance == null)
            instance = new DatabaseConnection();
        return instance;
    }

    public static Connection getConnection()
    {
        return getInstance().connection;
    }

    public static void removeInstance()
    {
        if(instance == null)
            return;

        instance.close();
        instance = null;
    }

    public void close()
    {
        if(connection == null)
            return;

        try
        {
            if(!connection.isClosed())
                connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

