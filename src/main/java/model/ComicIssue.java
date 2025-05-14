package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Creates a single comic book issue.
 * Can be used to compare to other issues to determine
 * duplicates or missing issues.
 * @author rmbackoTU-dev
 *
 *@TODO
 *Update so that the issue is just a String returned by the issueSetting.
 */
public class ComicIssue implements ComicComponent{
	
	private String seriesName;
	private String publishYear;
	private Integer issueNumber;
	private String subIssue;
	private IssueStatusTag status;
	//Only meant to keep the last 3 states (We could change this to a stack later)
	private IssueStatusTag[] prevStatuses=new IssueStatusTag[3];
	private String isbn;
	//implement book interface later for larger app. Highlight this does not look like a typical isbn
	private String isbnType="comicUPC";
	
	public ComicIssue(String name, String year, String issue)
	throws IllegalArgumentException
	{
		if(name=="" || name == null )
		{
			throw new IllegalArgumentException(
					"String must not be empty or null");
			
		}
		seriesName=name;
		if(!isYearFormat(year))
		{
			throw new IllegalArgumentException(
					"Year must be 4 consecutive decimals in the range 0-9");
		}
		else
		{
			publishYear=year;
		}
		
		issueNumber=parseIssueNumber(issue);
		if(hasSubIssue(issue))
		{
			subIssue=parseSubIssue(issue);
		}
		else
		{
			subIssue="";
		}
		//state of a comic always starts as pending
		this.status=IssueStatusTag.PENDING;
		initalizePrevious();
	}
	
	/**
	 * Initializes all the previous comic book states to null
	 */
	private void initalizePrevious()
	{
		for(int i=0; i<this.prevStatuses.length; i++)
		{
			this.prevStatuses[i]=null;
		}
	}
	
	private void pushPrevStatus(IssueStatusTag tag)
	{
		if(this.prevStatuses[0] == null)
		{
			this.prevStatuses[0]=tag;
		}
		else
		{
			IssueStatusTag prev=this.prevStatuses[0];
			IssueStatusTag current=null;
			//push everything to the right
			for(int i=1; i<this.prevStatuses.length; i++)
			{
				//keeps the current and pushes out the last previous
				current=this.prevStatuses[i];
				this.prevStatuses[i]=prev;
				prev=current;
				if(i==2 && !(current == null))
				{
					System.out.println("Dropping "+current.getStateName()+" from history.");
				}
			}
			//now add the new tag up front
			this.prevStatuses[0]=tag;
		}
	}
	
	private void pushRollBackStatus(IssueStatusTag rollBackTag)
	{
		for(int i=2; i>=0; i--)
		{
			//skip it if it is null it means nothing was lost
			IssueStatusTag current;
			IssueStatusTag replace=rollBackTag;
			if(!(this.prevStatuses[i] == null))
			{
				current=this.prevStatuses[i];
				this.prevStatuses[i]=rollBackTag;
				//the current tag goes into the previous slot
				replace=current;
				if(i==0)
				{
					System.out.println("Undoing push of "+replace.getStateName());
				}
			}
		}
	}
	
	private IssueStatusTag popPrevStatus()
	{
		IssueStatusTag current=null;
		if(!(this.prevStatuses[0] == (null)))
		{
			current=this.prevStatuses[0];
			//move everything else left
			int start=this.prevStatuses.length-1;
			IssueStatusTag last=null;
			IssueStatusTag replace=null;
			while(start>=0)
			{
				if(last == null)
				{
					last=this.prevStatuses[start];
					this.prevStatuses[start]=null;
				}
				else
				{
					replace=this.prevStatuses[start];
					this.prevStatuses[start]=last;
					last=replace;
				}
				start--;
			}
		}
		return current;
	}
	
	
	/**
	 * Sets the status to the correct state if the state is a valid next state
	 * @param status
	 */
	public void updateStatusNext(String status) throws IllegalStateException
	{	
		IssueStatusTag newTag=this.status;
		IssueStatusTag prevStatus=this.status;
		//save the last status in case we lose it by accident
		IssueStatusTag prevHistory=this.prevStatuses[2];
		if(status.equals("active"))
		{
			newTag=IssueStatusTag.ACTIVE;
		}
		else if(status.equals("pending"))
		{
			newTag=IssueStatusTag.PENDING;
		}
		else if(status.equals("skip"))
		{
			newTag=IssueStatusTag.SKIP;
		}
		else if(status.equals("backlog"))
		{
			newTag=IssueStatusTag.BACKLOG;
		}
		
		try
		{
			
			pushPrevStatus(prevStatus);
			this.status=this.status.nextState(newTag);
		}
		catch(Exception exception)
		{
			//reset to previous status
			this.status=popPrevStatus();
			this.pushRollBackStatus(prevHistory);;
			throw new IllegalStateException("An error occured reseting to last known status");
		}
		
		
	}
	
	public void rollbackStatusToPrevious() throws IllegalStateException
	{
		//In transaction terms this is dangerous because it is unprotected should be the smallest transaction unit.
		IssueStatusTag current=this.status;
		IssueStatusTag prev=popPrevStatus();
		try
		{
			this.status=status.previousState(prev);	
		}
		catch(Exception exception)
		{
			//Rolling back just resets and puts the previous back on the stack
			this.status=current;
			pushPrevStatus(prev);
			throw new IllegalStateException("An error occured reseting current status");
		}
			
		}
	
	public String getStatus()
	{
		return status.getStateName();
	}
	
	
	
	private static boolean isYearFormat(String year)
	{
		int inputSize=year.length();
		boolean result=false;
		if(inputSize != 4)
		{
			return result;
		}
		else 
		{
			Pattern yearPattern=Pattern.compile("(\\d{4})");
			Matcher yearMatcher=yearPattern.matcher(year);
			System.out.print(year+"\n");
			result=yearMatcher.find();
			System.out.println(result);
			return result;
		}
	}
	
	/**
	 * 
	 * @param issue
	 * @return The issue number as an int
	 * @throws IllegalArgumentException
	 */
	private static Integer parseIssueNumber(String issue)
	throws IllegalArgumentException
	{
		int inputLength=issue.length();
		Integer issueNumber=0;
		if(inputLength == 0 || issue == null)
		{
			throw new IllegalArgumentException("No valid issue String provided");
		}
		else if(inputLength >= 1)
		{
			//If the issue length is great than 1 separate the numeric part 
			//from the alphanumeric part at the start
			Pattern issueNumbPattern=Pattern.compile("^\\d+");
			//create a matcher to search
			Matcher patternSearch=issueNumbPattern.matcher(issue);
			System.out.println("Issue numb:"+issue);
			if(patternSearch.find())
			{
				int beginning=patternSearch.start();
				int endding=patternSearch.end();
				//get the sub string
				String numericPart=issue.substring(beginning, endding);
				
				issueNumber=Integer.parseInt(numericPart);
			}
			else
			{
				throw new IllegalArgumentException("Issue does not start with a number");
			}
		}
		return issueNumber;
	}
	/**
	 * 
	 * @param issue
	 * @return returns an alphabetic part of the issue, if there is no alphabetic part
	 *return err
	 * @throws IllegalArgumentException
	 */
	private static String parseSubIssue(String issue)
	throws IllegalArgumentException
	{
		String subIssueStr="err";
		if(!hasSubIssue(issue))
		{
			throw new IllegalArgumentException("This issue does not have a sub issue");
		}
		else
		{
			//Make a pattern to find the alphabetic portion of the issue
			Pattern subIssuePartPattern=Pattern.compile("[a-z]+");
			Matcher subIssuePartMatcher=subIssuePartPattern.matcher(issue);
			if(subIssuePartMatcher.find())
			{
				int beginning = subIssuePartMatcher.start();
				int ending= subIssuePartMatcher.end();
				
				subIssueStr=issue.substring(beginning, ending);
			}
		}
		
		return subIssueStr;
	}
	
	private static boolean hasSubIssue(String issue)
	{
		boolean hasSubIssue=false;
		int inputLength=issue.length();
		if(inputLength >= 2)
		{
			Pattern subIssuePattern=Pattern.compile("\\d{1,}[a-z]{1,}");
			
			Matcher subIssueMatcher=subIssuePattern.matcher(issue);
			
			hasSubIssue=subIssueMatcher.find();
		}
		
		return hasSubIssue;
	}
	
	@Override
	public ComicComponent getComicByIssue(String issueNum) {
		// TODO Auto-generated method stub
		if(getIssueNumber().equals(issueNum))
		{
			return this;
		}
		else
		{
			return null;
		}
		
	}
	
	public void setISBN(String isbn)
	{
		this.isbn=isbn;
	}
	

	public String getISBN()
	{
		return this.isbn;
	}
	
	public String getISBNType()
	{
		return this.isbnType;
	}
	
	public String getIssueName()
	{
		return this.seriesName;
	}
	
	@Override
	public String getIssuePublishYear()
	{
		return this.publishYear;
	}
	
	@Override
	public String getSeriesName()
	{
		return this.seriesName;
	}
	
	public String getIssueNumber()
	{
		String fullIssueNum=this.issueNumber.toString()+this.subIssue;
		return fullIssueNum;
	}
	
	public String getSubIssueNumber()
	{
		return this.subIssue;
	}

	@Override
	public String toString()
	{
		String issueString=this.seriesName+" - ("+this.publishYear+")\n"
				+ "issue: "+this.issueNumber.toString()+this.subIssue;
		return issueString;
	}

}
