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
		loadTestVariables(false, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComic=new ComicIssue(issueName, issueYear, issueNumOne);	
		testComicCollection.addComicIssue(testComic);
		String[] allIssueList=testComicCollection.getAllIssues();
		assertEquals(1, allIssueList.length);
		testComicCollection.removeComicIssue(testComic);
		allIssueList=testComicCollection.getAllIssues();
		assertEquals(0, allIssueList.length);
	}
	
	@Test
	public void sunnyDayRemoveMultiples()
	{
		loadTestVariables(true, false);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		String issueNumTwo=varStrings.get("issueNumTwo");
		String issueNumThree=varStrings.get("issueNumThree");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComicOne=new ComicIssue(issueName, issueYear, issueNumOne);
		ComicIssue testComicTwo=new ComicIssue(issueName, issueYear, issueNumTwo);
		ComicIssue testComicThree=new ComicIssue(issueName, issueYear, issueNumThree);
		testComicCollection.addComicIssue(testComicOne);
		testComicCollection.addComicIssue(testComicTwo);
		testComicCollection.addComicIssue(testComicThree);
		//Test remove from the middle, then remove from the end. Add back in reverse order to ensure
		//relative cardinality 
		String[] issueListResult=testComicCollection.getAllIssues();
		assertEquals(3, issueListResult.length);
		testComicCollection.removeComicIssue(testComicTwo);
		issueListResult=testComicCollection.getAllIssues();
		assertEquals(2, issueListResult.length);
		String[] expectedOrder= {"1", "2a"};
		for(int i=0; i<issueListResult.length; i++)
		{
			String actualIssue=issueListResult[i];
			String expectedIssue=expectedOrder[i];
			assertEquals(expectedIssue, actualIssue);
		}
		testComicCollection.removeComicIssue(testComicThree);
		issueListResult=testComicCollection.getAllIssues();
		assertEquals(1, issueListResult.length);
		assertEquals("1", issueListResult[0]);
		testComicCollection.addComicIssue(testComicThree);
		testComicCollection.addComicIssue(testComicTwo);
		issueListResult=testComicCollection.getAllIssues();
		String[] expectedOrderTwo= {"1", "2a", "2"};
		for(int i=0; i<issueListResult.length; i++)
		{
			String actualIssue=issueListResult[i];
			String expectedIssue=expectedOrderTwo[i];
			assertEquals(expectedIssue, actualIssue);
		}
		//remove a collection of comics
		ComicCollection removeComicCollection=new ComicCollection(issueName, issueYear);
		removeComicCollection.addComicIssue(testComicOne);
		removeComicCollection.addComicIssue(testComicThree);
		testComicCollection.removeComicIssue(removeComicCollection);
		issueListResult=testComicCollection.getAllIssues();
		assertEquals(1, issueListResult.length);
		assertEquals("2", issueListResult[0]);
	}
	
	@Test
	public void sunnyDayListMultipleIssues()
	{
		fail("Test not implemented");
	}
	
	
	
	@Test
	public void rainyDayCopyConstructor()
	{
		fail("Test not implemented");
	}
	
	@Test
	public void rainyDayNoComicToRemove()
	{
		loadTestVariables(false, true);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String issueNum=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		ComicIssue testComicIssue=new ComicIssue(issueName, issueYear, issueNum);
		try
		{
			testComicCollection.removeComicIssue(testComicIssue);
			fail("Remove with no found comics should return an error");
		}
		catch(IllegalStateException e)
		{
			//Fill in later once we figure out the proper exception to throw. Change the catch.
			String errorMsg=e.getMessage();
			String expectedErrMsg="Can not remove comic. "+
			"The comic does not already exist in the collection. Try adding a comic first";
			assertEquals(expectedErrMsg, errorMsg);
		}
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
		destComicCollection.addComicIssue(copyComicCollection);
		try
		{
			destComicCollection.removeComicIssue(destNestedComicCollection);
			fail("Should throw error for attempting to remove a nested collection");
		}
		catch(IllegalArgumentException err)
		{
			String errorMsg=err.getMessage();
			String expectedErrorMsg="Error: Trying to remove a collection of collections";
			assertEquals(expectedErrorMsg, errorMsg);
		}
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
	public void rainyDayRemoveYearValidationFail()
	{
		loadTestVariables(false, true);
		String issueName=varStrings.get("testComicOneName");
		String issueYear=varStrings.get("issueYear");
		String rainyIssueYear=varStrings.get("issueYearRainy");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		try 
		{
			ComicIssue comicIssue=new ComicIssue(issueName, issueYear, issueNumOne);
			ComicIssue wrongIssue=new ComicIssue(issueName, rainyIssueYear, issueNumOne);
			testComicCollection.addComicIssue(comicIssue);
			testComicCollection.removeComicIssue(wrongIssue);
			fail("Should fail with IllegalArugmentException");
		}
		catch(IllegalArgumentException iae)
		{
			String errorMsg=iae.getMessage();
			String expectedMsg="The comic you are trying to remove is not in the series";
			assertEquals(expectedMsg, errorMsg);
		}
	}
	
	@Test
	public void rainyDayRemoveNameValidationFail()
	{
		loadTestVariables(false, true);
		String issueName=varStrings.get("testComicOneName");
		String rainyIssueName=varStrings.get("testComicRainyName");
		String issueYear=varStrings.get("issueYear");
		String issueNumOne=varStrings.get("issueNumOne");
		ComicCollection testComicCollection=new ComicCollection(issueName, issueYear);
		try 
		{
			ComicIssue comicIssue=new ComicIssue(issueName, issueYear, issueNumOne);
			ComicIssue wrongIssue=new ComicIssue(rainyIssueName, issueYear, issueNumOne);
			testComicCollection.addComicIssue(comicIssue);
			testComicCollection.removeComicIssue(wrongIssue);
			fail("Should fail with IllegalArugmentException");
		}
		catch(IllegalArgumentException iae)
		{
			String errorMsg=iae.getMessage();
			String expectedMsg="The comic you are trying to remove is not in the series";
			assertEquals(expectedMsg, errorMsg);
		}
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
