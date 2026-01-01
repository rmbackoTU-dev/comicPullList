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
	private Integer collectionID;
	private String isbn;
	//implement book interface later for larger app. Highlight this does not look like a typical isbn
	private String isbnType="comicUPC";
	
	public ComicIssue(String name, String year, String issue)
	throws IllegalArgumentException
	{
		if(name=="" || name == null )
		{
			throw new IllegalArgumentException(
					"Comic Name must not be empty or null");
			
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
		collectionID=0;
	}

	
	private static boolean isYearFormat(String year)
	{
		boolean result=false;
		if(year == null)
		{
			return result;
		}
		int inputSize=year.length();
		
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
		if(issue == null)
		{
			throw new IllegalArgumentException("Issue attribute shall not be null");
		}
		int inputLength=issue.length();
		Integer issueNumber=0;
		if(inputLength == 0)
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

	
	public void setCollection(int idNum)
	{
		this.collectionID=idNum;
	}
	
	public boolean isInCollection()
	{
		boolean collectionStatus=(this.collectionID != 0);
		return collectionStatus;
		
	}
	
	public Integer getCollectionID()
	{
		return this.collectionID;
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
		String fullIssueNum=this.issueNumber.toString();
		return fullIssueNum;
	}
	
	public void setIssueNumber(String issueNumber)
	{
		this.issueNumber=parseIssueNumber(issueNumber);
	}
	
	public void setSeriesName(String name)
	{
		if(name=="" || name == null )
		{
			throw new IllegalArgumentException(
					"Comic Name must not be empty or null");	
		}
		else
		{
			this.seriesName=name;
		}
		
	}
	
	public void setPublishYear(String year)
	{
		if(!isYearFormat(year))
		{
			throw new IllegalArgumentException(
					"Year must be 4 consecutive decimals in the range 0-9");
		}
		else
		{
			this.publishYear=year;
		}
	}

	@Override
	public String toString()
	{
		String issueString=this.seriesName+" - ("+this.publishYear+")\n"
				+ "issue: "+this.issueNumber.toString();
		return issueString;
	}

}
