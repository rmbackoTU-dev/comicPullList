package dao;

import model.ComicIssue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import db.DatabaseConnector;

public class ComicCRUDActions {

    //Static global there should not be any other connections created by this class.
    public static Connection conn=null;

    /**
    *constructor if  noconnection exist
    */
    public ComicCRUDActions()
    {
        try{
            if(conn == null)
            {
                conn=DatabaseConnector.getConnection();
            }
        }
        catch(SQLException sqlException)
        {
            //Later we need to figure out how to get this logged or printed for long term viewing
            sqlException.printStackTrace();
            System.out.println(sqlException.getMessage());
        }
    }

    /*
    *constructor if connection exist
    */
    public ComicCRUDActions(Connection connect) throws IllegalArgumentException
    {
        if(conn == null)
        {
            conn=connect;
        }
        else
        {
            throw new IllegalArgumentException("A connection already exist a new one can not be created.");
        }
    }

    /**
     * Inserts a single Comic Issue into the database
     * @param comic
     */
    public void insertComic(ComicIssue comic)
    {
        
        //Step 1. we need to gather all our comic fields for our prepared sql
        String comic_Name=comic.getSeriesName();
        String comic_Year=comic.getIssuePublishYear();
        String comic_issue=comic.getIssueNumber();
        String comic_collection_num= Integer.valueOf(comic.getCollectionID()).toString();

        //Note: Collection ID May not be filled we should check for it before making our statement.
        //Step 2. Determine if the collection id has been inserted since this is a future feature
        String insert_sql;
        if(comic_collection_num == "0")
        {
            insert_sql="INSERT INTO comic_Issue(Series_Name,Series_Year,issue_ID) VALUES(?,?,?);";
            
        }
        else
        {
            insert_sql="INSERT INTO comic_Issue(Series_Name,Series_Year,issue_ID,collect_ID) VALUES(?,?,?,?);";
        }

        
        //Step 3. make the prepared statement and insert the appropriate variables for the statement
        try{
            PreparedStatement statement=conn.prepareStatement(insert_sql);
            statement.setString(1, comic_Name);
            statement.setString(2, comic_Year);
            statement.setString(3, comic_issue);
            if(comic_collection_num != "0")
            {
                statement.setString(4, comic_collection_num);
            }
            //Step 4. Execute and close the connection
            statement.execute();
            
        }
        catch(SQLException e)
        {
            //Later we need to figure out how to get this logged or printed for long term viewing
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**
     * Selects all of the comics from the comic issue table
     * @return An ArrayList of ComicIssue objects. If an exception is thrown return an empty ArrayList
     */
    public ArrayList<ComicIssue> readComics()
    {
        //Step1. Create a SQL Select string
        String select_string="SELECT Series_Name, Series_Year, issue_ID, collect_ID FROM comic_Issue;";
        ArrayList<ComicIssue> comicsList=new ArrayList<ComicIssue>(); //Prep the empty list just in case we fail
        //Step 2. Try catch a prepared statement
        try{
            PreparedStatement statement=conn.prepareStatement(select_string);
            ResultSet results=statement.executeQuery();
            //Step 3. Now we have the result set we need to parse it to make our list of objects
            
            while(results.next())
            {
                ComicIssue currentComic;
                String comicSeriesName=results.getString(1);
                //Need to conver from a character Stream to a String.
                //Maybe we can see if there is a difference.
                String comicYear=results.getString(2);
                String comicIssue=results.getString(3);
                int current_collection_ID=results.getInt(4);
                currentComic=new ComicIssue(comicSeriesName, comicYear, comicIssue);
                if(!(results.wasNull()))
                {
                    currentComic.setCollection(current_collection_ID);
                }
                comicsList.add(currentComic);
            }
            return comicsList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return comicsList;
        }
    }

}
