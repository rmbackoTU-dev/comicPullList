package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Creates a single comic book issue.
 * Can be used to compare to other issues to determine
 * duplicates or missing issues.
 * @author rmbackoTU-dev
 *
 */
public class ComicIssue implements ComicComponent{
	
	private String seriesName;
	private String publishYear;
	private Integer issueNumber;
	private String subIssue;
	private IssueStatusTag status;
	private IssueStatusTag prevStatus;
	
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
		this.prevStatus=null;
	}
	
	
	/**
	 * Sets the status to the correct state if the state is a valid next state
	 * @param status
	 */
	public void updateStatusNext(String status) throws IllegalStateException
	{
		IssueStatusTag newTag=this.status;
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
			this.prevStatus=this.status;
			this.status=this.status.nextState(newTag);
		}
		catch(Exception exception)
		{
			this.status=prevStatus;
			this.prevStatus=null;
			throw new IllegalStateException("An error occured reseting to last known status");
		}
		
		
	}
	
	public void rollbackStatusToPrevious() throws IllegalStateException
	{
		IssueStatusTag current=this.status;
		try
		{
			this.status=status.previousState(this.prevStatus);
			this.prevStatus=null;
		}
		catch(Exception exception)
		{
			this.status=current;
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
