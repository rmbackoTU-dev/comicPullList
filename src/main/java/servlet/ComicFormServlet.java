package servlet;
//Created code
import model.ComicIssue;
import dao.ComicCRUDActions;

//JSP-Servlet Libraries
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

//Java libraries
import java.io.IOException;

public class ComicFormServlet extends HttpServlet{

    private ComicCRUDActions actor;

    @Override
    public void init()
        throws ServletException
    {
        super.init();
        //set up a actor to save our data
        actor=new ComicCRUDActions(); 
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse responsemHttpServletResponse)
        throws IOException, ServletException
        {
            String issueName=request.getParameter("comic_Name");
            String issueNum=request.getParameter("comic_issue");
            String issueYear=request.getParameter("comic__Pub_Year");

            //create a new comic item
            ComicIssue newIssue=new ComicIssue(issueName, issueYear, issueNum);

            //Store issue
            actor.insertComic(newIssue);

            //Get info for the response
            String comicName=newIssue.getIssueName();
            String comicYear=newIssue.getIssuePublishYear();
            String comicNum=newIssue.getIssueNumber();

            //Tell the user with a response we have stored the comic
            request.setAttribute("comic_Name",comicName);
            request.setAttribute("comic_Pub_Year", comicYear);
            request.setAttribute("comic_issue", comicNum);
            RequestDispatcher view=getServletContext().getRequestDispatcher("/templates/confirm-entry.jsp");

            view.forward(request, responsemHttpServletResponse);
            
        }
}
