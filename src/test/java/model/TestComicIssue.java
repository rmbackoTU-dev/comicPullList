package model;

import model.ComicIssue;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import junit.framework.Assert;

import org.junit.After;


//Todo: Change condition names to an ENUM In a switch should simplify load variables.
/*
 * TODO: 
 *   [] Add Sunny test for set issueName
 *   [] Add Sunny test for set issueNumber
 *   [] Add Sunny test for set issueYear
 *   [] Add Rainy test for set issueName
 *   [] Add Rainy test for set issueNumber
 *   [] Add Rainy test for set issueYear
 */
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
		}
		else if(status.equals("Fail"))
		{
			if(condition.equals("Empty issue name"))
			{
				varStrings.put("testIssueName","");
				varStrings.put("issueNum", "1");
				varStrings.put("issueYear","2023");
			}
			else if(condition == "Empty issue number")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "");
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
				varStrings.put("issueNum", "1");
				varStrings.put("issueYear","TwentyTwentyThree");
			}
			else if(condition == "Empty year")
			{
				varStrings.put("testIssueName","The Amazing Test Comic");
				varStrings.put("issueNum", "1");
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
	public void sunnyDayTestOptionalISBN()
	{
		System.out.println("Sunny Day Constructor Issue/ get ISBN-Sunny");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		String testComicISBN="41941 37681 3 00011";
		testComic.setISBN(testComicISBN);
		assertEquals(testComicISBN, testComic.getISBN());	
	}
	
	@Test
	public void sunnyDayTestOptionalISBNType()
	{
		System.out.println("Sunny Day Constructor Issue/ get ISBN Type (should be static for all books)");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		String testISBNType="comicUPC";
		assertEquals(testComic.getISBNType(), testISBNType);
	}
	
	@Test
	public void SunnyDayTestisInCollectionDefault()
	{
		System.out.println("Sunny Day Constructor Issue/ testing default issue is not in a collection ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		assertFalse("test comic should not be in a collection by default", testComic.isInCollection());
	}
	
	@Test
	public void SunnyDayTestisInCollectionWithCollectionSet()
	{
		System.out.println("Sunny Day Constructor Issue/ testing issue is in collection after id is set. ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		testComic.setCollection(1);
		assertTrue("test comic should be in collection 1", testComic.isInCollection());
	}
	
	/*
	 * Validate the issue is set
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void sunnyDayTestIssueSetter(){
		System.out.println("Sunny Day Constructor Issue/ issue number can be updated");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	

	/*
	 * Validate the series name is set
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void sunnyDayTestSeriesNameSetter(){
		System.out.println("Sunny Day Constructor Issue/ issue series can be updated");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	

	/*
	 * Validate the year is set
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void sunnyDayTestPublishYearSetter(){
		System.out.println("Sunny Day Constructor Issue/ issue year can be updated" );
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on issue setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestIssueSetterEmptyString(){
		System.out.println("Sunny Day Constructor Issue/ issue series with incorrect input fails no issue");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on issue setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestIssueSetterNotANumber(){
		System.out.println("Sunny Day Constructor Issue/ issue series with incorrect input fails not an issue");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on series name setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestSeriesNameSetterEmptyString(){
		System.out.println("Sunny Day Constructor Issue/ issue name with incorect input fails no name ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on series name setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestSeriesNameSetterNullSeries(){
		System.out.println("Sunny Day Constructor Issue/ issue name with incorect input fails null ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on publish year setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestPublishYearSetterNoYear(){
		System.out.println("Sunny Day Constructor Issue/ setting issue year fails no year ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	/*
	 * Validate bounds checking on publish year setting
	 * Later todo determine how a collection constraint may a effect this
	 */
	@Test
	public void rainyDayTestPublishYearSetterNAN(){
		System.out.println("Sunny Day Constructor Issue/ setting issue year fails not a number string ");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
	}
	
	@Test
	public void sunnyDayGetters()
	{
		System.out.println("Sunny Day Constructor Issue/ testing getters");
		loadVariables("Success", "issue");
		ComicIssue testComic=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
		System.out.println("Before Issue Series Name: "+varStrings.get("testIssueName"));
		System.out.println("Before Issue Publish Year: "+varStrings.get("issueYear"));
		System.out.println("Before Issue Issue Num: "+varStrings.get("issueNum"));
		String actualSeriesName=testComic.getSeriesName();
		String actualSeriesYear=testComic.getIssuePublishYear();
		assertEquals(varStrings.get("testIssueName"), actualSeriesName);
		assertEquals(varStrings.get("issueYear"), actualSeriesYear);
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
	public void rainyDayConstructorEmptyString()
	{
		System.out.println("Rainy Day Empty Issue String");
		loadVariables("Fail", "Empty issue name");
		try
		{
			ComicIssue testIssueFail=new ComicIssue(varStrings.get("testIssueName"),
				varStrings.get("issueYear"), varStrings.get("issueNum"));
			
		}
		catch(IllegalArgumentException iae)
		{
			assertEquals(iae.getMessage(), "Comic Name must not be empty or null");
		}
	}
	
}
