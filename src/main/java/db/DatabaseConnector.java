package db;


import config.PropertiesLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {

    private static String dbURL;
    private static String dbUser;
    private static String dbPass;


    static
    {
        //our property file contains our db config
        PropertiesLoader loader=new PropertiesLoader("configs/properties.config");
        Properties prop=loader.getProps();
        dbURL=prop.getProperty("DB_URL");
        dbUser=prop.getProperty("DB_USER");
        dbPass=prop.getProperty("DB_PASS");
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbURL,
            dbUser,
            dbPass
        );
        
    }

    
}
