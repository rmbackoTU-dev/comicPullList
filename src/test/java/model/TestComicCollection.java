package model;

import model.ComicCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.HashMap;
import org.junit.Test;
import org.junit.After;

public class TestComicCollection {
	
	
	private HashMap<String, String> varStrings=
			new HashMap<String, String>();
	
	private void loadTestVariables( boolean multiple)
	{
		if(multiple)
		{
			varStrings.put("testComicOneName", "The Amazing Test Comic");
			varStrings.put("testComicTwoName", "The Incredible Test Comic");
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
		
	}
	
	@After
	public  void tearDown()
	{
		varStrings=new HashMap<String, String>();
	}
	
	@Test
	public void sunnyDayConstructAndGetters()
	{
		loadTestVariables(false);
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
		loadTestVariables(true);
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
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayRemoveValidationFail()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayAddValidationFail()
	{
		fail("Test not implemented");
	}
	

}
