package dao;


import model.ComicComponent;
import model.ComicIssue;
import dao.ComicCRUDActions;
import db.DatabaseConnector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.After;
import org.junit.Test;

public class TestComicCRUDActions {
    
   public static Connection conn;
   
    public static void runSQLScript(Connection conn, String resourceString)
    {
        try{
        System.out.println(resourceString);
        String sqlCmds=new String(Objects.requireNonNull(
            TestComicCRUDActions.class.getClassLoader().getResourceAsStream(resourceString).readAllBytes()
        ));

        //create a sql statement to execute that we can add sql to
        Statement stmt=conn.createStatement();
        //Now split the script into multiple commands for each end of line designator
        for(String cmd: sqlCmds.split(";"))
        {
            //Get rid of extraneous white space
            String trimmedCMD=cmd.trim();
            //if there is a command there execute it
            if(!(trimmedCMD.isEmpty()))
            {
                stmt.execute(trimmedCMD);
            }

        }

        }catch(IOException e)
        {
            String errorMessage=e.getMessage();
            System.err.println("Accessing your SQL Script located a "+resourceString+" caused the following error");
            System.err.println(errorMessage);
        }
        catch(SQLException se)
        {
            String errorMessage=se.getMessage();
            System.err.println("Issue accessing the sql database using the default connection.");
            System.err.println(errorMessage);
        }
    }

   @BeforeClass
   public static void setUpTesting() throws SQLException
   {
        conn=DatabaseConnector.getConnection();
        runSQLScript(conn, "sql-scripts/create_schema.sql");

   }

   @After
   public void tearDownTest() throws SQLException
   {
        //TODO: Use java properties to pull the exact path out of this.
        runSQLScript(conn, "sql-scripts/delete_data.sql");
   }


    @AfterClass
    public static void tearDownTesting() throws SQLException
    {
        /**TODO: Make a clean up action to clear the table after I am done. */
        runSQLScript(conn, "sql-scripts/destroy_schema.sql");
        conn.close();
        
    }

    @Test
    public void testCreateComicIssue()
    {
        ComicCRUDActions comicActions= new ComicCRUDActions(conn);
        ComicIssue testComic= new ComicIssue("TestIssue123", "2026", "1");
        comicActions.insertComic(testComic);

        ArrayList<ComicIssue> newComicList=comicActions.readComics();
        ComicIssue newComic=newComicList.get(0);
        assertNotNull(newComic);
    }

    // @Test
    // public void testCreateMultipleComicIssues()
    // {
    //     fail("TODO: Not yet implemented");
    // }

    // @Test
    // public void testReadComicIssueList()
    // {
    //     fail("TODO: Not yet implemented");
    // }

    // @Test
    // public void testReadSingleComic()
    // {
    //     fail("TODO: Not yet implemented");
    // }

}
