package model;

import model.ComicIssue;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.After;



public class TestComicIssue {
	
	private HashMap<String, String> varStrings=
		new HashMap<String, String>();

	
	public void loadVariables(String status, String condition )
	{
		if(status.equals("Success"))
		{
			if(condition.equals("issue"))
			{
				varStrings.put("testIssueName", "The Amazing Test Comic");
				varStrings.put("issueNum", "1");
				varStrings.put("issueYear","2023");
			}
			else if(condition.equals("subIssue"))
			{
				varStrings.put("testIssueName", "The Amazing Test Comic");
				varStrings.put("issueNum", "1a");
				varStrings.put("issueYear","2023");
			}
		}
		else if(status.equals("Fail"))
		{
			if(condition.equals("Empty issue"))
			{
				varStrings.put("testIssueName","");
				varStrings.put("issueNum", "1");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Empty sub issue number")
			{
				varStrings.put("testIssueName", "The Amazing Test Comic");
				varStrings.put("issueNum", "1 ");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Empty issue number")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Flipped sub issue and issue number")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "a1");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Not a Number in issue number")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "a");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Not a number year") {
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "1a");
				varStrings.put("issueYear","TwentyTwentyThree");
			}
			else if(condition == "Empty year")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "1a");
				varStrings.put("issueYear","");
			}
		}
	}
	
	@After
	public void tearDown()
	{
		varStrings=new HashMap<String,String>();
	}
	
	@Test
	public void sunnyDayConstructorIssue()
	{
		System.out.println("Sunny Day Constructor Issue");
		loadVariables("Success", "issue");
		ComicIssue testIssueSuccess=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		assertEquals(varStrings.get("testIssueName"), testIssueSuccess.getIssueName());
		System.out.println(testIssueSuccess.getIssuePublishYear());
		assertEquals(varStrings.get("issueYear"), testIssueSuccess.getIssuePublishYear());
		assertEquals(varStrings.get("issueNum"),testIssueSuccess.getIssueNumber());
		
		
	}
	
	@Test
	public void sunnyDayConstructorSubIssue()
	{
		System.out.println("Sunny Day Constructor  Sub Issue");
		loadVariables("Success", "subIssue");
		ComicIssue testIssueSuccess=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		assertEquals(varStrings.get("testIssueName"), testIssueSuccess.getIssueName() );
		assertEquals(varStrings.get("issueYear"), testIssueSuccess.getIssuePublishYear() );
		assertEquals(varStrings.get("issueNum"),testIssueSuccess.getIssueNumber() );
	}
	
	@Test
	public void rainyDayIsYearFormatIAE()
	{
		loadVariables("Fail", "Empty year");
		try
		{
			System.out.println("Rainy Day Empty Year");
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
					varStrings.get("issueYear"), varStrings.get("issueNum"));
			fail("Constructor should throw an IllegalArgument Exception");
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(), "Year must be 4 consecutive decimals in the range 0-9");
		}
	}
	
	@Test
	public void rainyDayParseIssueNumberNAN()
	{
		System.out.println("Rainy Day Issue Not a Number");
		loadVariables("Fail", "Not a Number in issue number");
		try
		{
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
			fail("Constructor should throw an IllegalArgument Exception");
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(), "Issue does not start with a number");
		}
		
	}
	
	@Test
	public void rainyDayParseIssueNumberFlippedFormat()
	{
		System.out.println("Rainy Day Flipped Issue Format");
		loadVariables("Fail", "Flipped sub issue and issue number");
		try
		{
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
			fail("Constructor should throw an illegal argument exception");
			
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(),"Issue does not start with a number");
		}
	}
	
	@Test
	public void rainyDayParseIssueNumberEmptyString()
	{
		System.out.println("Rainy Day empty issue number");
		loadVariables("Fail", "Empty issue number");
		try
		{
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
			fail("Constructor should throw illegal argument exception");
		
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(), "No valid issue String provided");
		}
	}
	
	@Test
	public void rainyDayParseSubIssueEmptyString()
	{
		System.out.println("Rainy Day Empty sub issue number");
		loadVariables("Fail", "Empty sub issue number");
		ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
			varStrings.get("issueYear"), varStrings.get("issueNum"));
			
		String expectedIssue="1";
		String expectedSubIssue="";
		
		assertEquals(expectedIssue, testIssueFail.getIssueNumber());
		assertEquals(expectedSubIssue, testIssueFail.getSubIssueNumber());
		
	}
	
	@Test
	public void rainyDayConstructorEmptyString()
	{
		System.out.println("Rainy Day Empty Issue String");
		loadVariables("Fail", "Empty issue");
		try
		{
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
			
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(), "String must not be empty or null");
		}
	}
	
}
