package model;

import model.ComicCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.HashMap;
import org.junit.Test;
import org.junit.After;
import testUtils.NestedComicCollection;

public class TestComicCollection {
	
	
	private HashMap<String, String> varStrings=
			new HashMap<String, String>();
	
	private void loadTestVariables( boolean multiple, boolean rainy)
	{
		if(multiple)
		{
			varStrings.put("testComicOneName", "The Amazing Test Comic");
			varStrings.put("issueNumOne", "1");
			varStrings.put("issueNumTwo", "2");
			varStrings.put("issueNumThree", "2a");
			varStrings.put("issueYear", "2023");
		}
		else if (!multiple)
		{
			varStrings.put("testComicOneName", "The Amazing Test Comic");
			varStrings.put("issueNumOne", "1");
			varStrings.put("issueYear", "2023");
		}
		
		if(rainy)
		{
			varStrings.put("testComicRainyName", "The Incredible Test Comic");
			varStrings.put("issueYearRainy", "2112");
		}
		
	}
	
	@After
	public  void tearDown()
	{
		varStrings=new HashMap<String, String>();
	}
	
	@Test
	public void sunnyDayConstructAndGetters()
	{
		loadTestVariables(false, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		String actualSeriesName=testComicCollection.getSeriesName();
		String actualYear=testComicCollection.getIssuePublishYear();
		assertEquals(issueName,actualSeriesName);
		assertEquals(issueYear,actualYear);
	}
	
	@Test
	public void sunnyDayAddComic()
	{
		loadTestVariables(false, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComic=new ComicIssue(issueName, issueYear, issueNumOne);	
		testComicCollection.addComicIssue(testComic);
		ComicIssue resultComic=(ComicIssue)testComicCollection.getComicByIssue(issueNumOne);
		String actualIssueNum=resultComic.getIssueNumber();
		assertEquals(issueNumOne, actualIssueNum);
	}
	
	@Test
	public void sunnyDayAddMultipleComic()
	{
		loadTestVariables(true, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		String issueNumTwo=varStrings.get("issueNumTwo");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComicOne=new ComicIssue(issueName, issueYear,issueNumOne);
		ComicIssue testComicTwo=new ComicIssue(issueName, issueYear, issueNumTwo);
		testComicCollection.addComicIssue(testComicOne);
		testComicCollection.addComicIssue(testComicTwo);
		ComicIssue resultOneComic=(ComicIssue)testComicCollection.getComicByIssue(issueNumOne);
		ComicIssue resultTwoComic=(ComicIssue)testComicCollection.getComicByIssue(issueNumTwo);
		String actualIssueNumOne=resultOneComic.getIssueNumber();
		String actualIssueNumTwo=resultTwoComic.getIssueNumber();
		assertEquals(issueNumOne, actualIssueNumOne);
		assertEquals(issueNumTwo, actualIssueNumTwo);
	}
	
	@Test
	public void sunnyDayAddMultipleComicByCollection()
	{
		loadTestVariables(true, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		String issueNumTwo=varStrings.get("issueNumTwo");
		ComicCollection copyComicCollection=new ComicCollection(issueName, issueYear);
		ComicCollection destComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComicOne=new ComicIssue(issueName, issueYear,issueNumOne);
		ComicIssue testComicTwo=new ComicIssue(issueName, issueYear, issueNumTwo);
		copyComicCollection.addComicIssue(testComicOne);
		copyComicCollection.addComicIssue(testComicTwo);
		destComicCollection.addComicIssue(copyComicCollection);
		ComicIssue resultOneComic=(ComicIssue) destComicCollection.getComicByIssue(issueNumOne);
		ComicIssue resultTwoComic=(ComicIssue) destComicCollection.getComicByIssue(issueNumTwo);
		String actualIssueNumOne=resultOneComic.getIssueNumber();
		String actualIssueNumTwo=resultTwoComic.getIssueNumber();
		assertEquals(issueNumOne, actualIssueNumOne);
		assertEquals(issueNumTwo, actualIssueNumTwo);
	}
	
	@Test
	public void sunnyDayRemoveComic()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void sunnyDayListMultipleIssues()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void sunnyDayListOneIssue()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayConstructor()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayNoComicToRemove()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayListEmptyCollection()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayFindIssueEmptyCollection()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayFindIssueNotInCollection()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayRemoveNestedCollection()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayAddNestedCollection()
	{
		/**Class needs to use an extended version of comic collection. While
		* a little far fetched it is possible we could extend either of these classes
		* to add things or even encapsulate them in other objects with more meta data
		*/
		loadTestVariables(true, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		String issueNumTwo=varStrings.get("issueNumTwo");
		ComicCollection copyComicCollection=new ComicCollection(issueName, issueYear);
		NestedComicCollection destNestedComicCollection=new NestedComicCollection(issueName, issueYear);
		ComicCollection destComicCollection= new ComicCollection(issueName, issueYear);
		ComicIssue testComicOne=new ComicIssue(issueName, issueYear,issueNumOne);
		ComicIssue testComicTwo=new ComicIssue(issueName, issueYear, issueNumTwo);
		copyComicCollection.addComicIssue(testComicOne);
		copyComicCollection.addComicIssue(testComicTwo);
		destNestedComicCollection.nestedAdd(copyComicCollection);
		try
		{
			destComicCollection.addComicIssue(destNestedComicCollection);
			fail("Should throw IllegalArgumentException");
		}
		catch(IllegalArgumentException iae)
		{
			String errorMsg=iae.getMessage();
			String expectedErrorMsg="Error: Trying to add a collection of collections";
			assertEquals(expectedErrorMsg, errorMsg);
		}
		
	}
	
	@Test
	public void rainyDayRemoveValidationFail()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayAddNameValidationFail()
	{
		loadTestVariables(false, true);
		String issueName=varStrings.get("testComicOneName");
		String rainyIssueName=varStrings.get("testComicRainyName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		try 
		{
			ComicIssue wrongIssue=new ComicIssue(rainyIssueName, issueYear, issueNumOne);
			testComicCollection.addComicIssue(wrongIssue);
			fail("Should fail with IllegalArugmentException");
		}
		catch(IllegalArgumentException iae)
		{
			String errorMsg=iae.getMessage();
			String expectedMsg="The comic you are trying to add is not in the series";
			assertEquals(expectedMsg, errorMsg);
		}
	}
	
	@Test
	public void rainyDayAddYearValidationFail()
	{
		loadTestVariables(false, true);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String rainyIssueYear=varStrings.get("issueYearRainy");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		try 
		{
			ComicIssue wrongIssue=new ComicIssue(issueName, rainyIssueYear, issueNumOne);
			testComicCollection.addComicIssue(wrongIssue);
			fail("Should fail with IllegalArugmentException");
		}
		catch(IllegalArgumentException iae)
		{
			String errorMsg=iae.getMessage();
			String expectedMsg="The comic you are trying to add is not in the series";
			assertEquals(expectedMsg, errorMsg);
		}
	}
	

}
