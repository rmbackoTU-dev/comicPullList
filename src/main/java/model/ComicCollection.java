package model;

import java.util.LinkedList;

public class ComicCollection implements ComicComponent {

	/**Todo: See Personal Notes:
	*
	**/
	
	private String seriesName;
	private Integer publishYear;
	private LinkedList<ComicComponent> comics;
	
	/** Base Constructor
	 * creates an empty comic collection with a publish year and series Name
	 */
	public ComicCollection(String name, String year)
	{
		this.seriesName=name;
		this.publishYear=Integer.getInteger(year);
	}
	
	@Override
	public ComicComponent getComicByIssue(String issue) {
		int comicIndex=this.findComicIssue(issue);
		ComicComponent book=this.comics.get(comicIndex);
		return book;
	}
	
	/**
	 * Add a new Issue to the array list of Comic books
	 * @param comicbook a single issue you would like to add
	 */
	public void addComicIssue(ComicIssue comicbook)
	{
		// TODO add validation that comicbook is in comic book series
		this.comics.add(comicbook);
	}
	
	/**
	 * Remove the comic book series from the comic book collection
	 * @param comicbook
	 */
	public void removeComicIssue(ComicIssue comicbook)
	{
		String comicIssueNum=comicbook.getIssueNumber();
		Integer comicIndex=findComicIssue(comicIssueNum);
		comics.remove(comicIndex.intValue());
	}
	
	/**
	 * Search the array of comics and find the integer of a comic with
	 * a specific issue
	 * @return index number of comic as an int
	 */
	public Integer findComicIssue(String comicIssueNum)
	{
		/** TODO create a function in comicIssue that will compare 2 issues
		*we can later optimize how we find an issue right now just to get the function
		* on paper we can just brute force it.
		**/
		int collectionSize=this.comics.size();
		Integer foundIndex=null;
		String currentIssueNum;
		ComicIssue currentComic;
		int i=0;
		while( (foundIndex == null) && (i < collectionSize))
		{
			currentComic=(ComicIssue)this.comics.get(i);
			currentIssueNum=currentComic.getIssueNumber();
			if(comicIssueNum == currentIssueNum)
			{
				foundIndex=i;
			}
			else
			{
				i++;
			}
		}
		return foundIndex;
		
	}

	@Override
	public String getIssuePublishYear() {
		// TODO Auto-generated method stub
		return this.getIssuePublishYear();
	}
	
	@Override
	public String getSeriesName()
	{
		return this.seriesName;
	}
	
	/**
	 * 
	 * @return size of collection
	 */
	public int getSize()
	{
		return this.comics.size();
	}
	
	/**
	 * 
	 * @return a List of all the issues for this series
	 */
	public String[] getAllIssues()
	{
		int numOfComics=getSize();
		String[] issues=new String[numOfComics];
		ComicIssue current;
		String currentIssue;
		for(int i=0; i< numOfComics; i++)
		{
			current=(ComicIssue) this.comics.get(i);
			currentIssue=current.getIssueNumber();
			issues[i]=currentIssue;
		}
		return issues;
	}
	
	
	
	
	

}
