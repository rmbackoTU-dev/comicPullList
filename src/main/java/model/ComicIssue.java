package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class ComicIssue implements ComicComponent {
	
	public String issueName;
	public String publishYear;
	public Integer issueNumber;
	public String subIssue;
	
	public ComicIssue(String name, String publishYear, String issue)
	throws IllegalArgumentException
	{
		issueName=name;
		if(!isYearFormat(publishYear))
		{
			throw new IllegalArgumentException(
					"Year must be 4 consecutive decimals in the range 0-9");
		}
		
	}
	
	private static boolean isYearFormat(String year)
	{
		int inputSize=year.length();
		if(inputSize != 4)
		{
			return false;
		}
		else 
		{
			return Pattern.matches("//d//d//d//d", year);
		}
	}
	
	private static int parseIssueNumber(String issue)
	throws IllegalArgumentException
	{
		int inputLength=issue.length();
		if(inputLength == 0)
		{
			throw new IllegalArgumentException("No valid issue String provided");
		}
		return 0;
	}
	
	private static String parseSubIssue(String issue)
	throws IllegalArgumentException
	{
		if(!hasSubIssue(issue))
		{
			throw new IllegalArgumentException("This issue does not have a sub issue");
		}
		
		return "";
	}
	
	private static boolean hasSubIssue(String issue)
	{
		boolean hasSubIssue=false;
		int inputLength=issue.length();
		if(inputLength > 2)
		{
			Pattern subIssuePattern=Pattern.compile("//d{1,}[a-z]{1,}");
			
			Matcher subIssueMatcher=subIssuePattern.matcher(issue);
			
			hasSubIssue=subIssueMatcher.find();
			
		}
		
		return hasSubIssue;
	}
	
	@Override
	public ComicComponent getComicComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String toString()
	{
		String issueString=this.issueName+" - ("+this.publishYear+")";
		return issueString;
	}

}
