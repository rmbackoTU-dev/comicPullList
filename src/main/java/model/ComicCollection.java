package model;

import java.util.LinkedList;

public class ComicCollection implements ComicComponent {

	/**Todo: See Personal Notes:
	*
	**/
	
	private String seriesName;
	private String publishYear;
	private LinkedList<ComicComponent> comics;
	
	/** Base Constructor
	 * creates an empty comic collection with a publish year and series Name
	 */
	public ComicCollection(String name, String year)
	{
		this.seriesName=name;
		this.publishYear=year;
		comics=new LinkedList<ComicComponent>();
	}
	
	public ComicCollection(String name, String year, String[] issueNumbers)
	{
		this.seriesName=name;
		this.publishYear=year;
		this.comics=new LinkedList<ComicComponent>();
		int issueNumSize=issueNumbers.length;
		for(int i=0; i<issueNumSize; i++)
		{
			String currNum=issueNumbers[i];
			ComicIssue currentComic=new ComicIssue(this.seriesName, this.publishYear, currNum);
			this.comics.add(currentComic);
		}
	}
	
	/**
	 * Allows children classes to get the list of comics and implement specific changes
	 * to how the collection is handled or ordered.
	 * @return LinkedList of comics
	 */
	protected LinkedList<ComicComponent> getComicList()
	{
		return this.comics;
	}
	
	/**
	 * Allows children class to set the list of comics and implement specific changes
	 * to how the collection is handled or ordered
	 * @return LinkedList of comics
	 */
	protected void setComicList(LinkedList<ComicComponent> comics)
	{
		this.comics=comics;
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
	public void addComicIssue(ComicComponent comicbook)
	throws IllegalArgumentException
	{
		String inputSeriesName=comicbook.getSeriesName();
		String inputSeriesYear=comicbook.getIssuePublishYear();
		if((this.seriesName.equals(inputSeriesName))&& 
				(this.publishYear.equals(inputSeriesYear))) 
		{
			//Strip down to single issues
			if(comicbook.getClass()== ComicIssue.class)
			{
				this.comics.add(comicbook);
			}
			else
			{
				ComicCollection collectedComic=(ComicCollection) comicbook;
				LinkedList<ComicComponent> listOfComics=collectedComic.getComicList();
				int collectionSize=collectedComic.getSize();
				ComicComponent currentComic;
				for (int i=0; i<collectionSize; i++)
				{
					currentComic=listOfComics.get(i);
					if(currentComic.getClass() != ComicIssue.class)
					{
						throw new IllegalArgumentException("Error: Trying to add a collection of collections");
					}
					else
					{
						this.comics.add(currentComic);
					}
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("The comic you are trying to add is not in the series");
		}
	}
	
	/**
	 * Remove the comic book series from the comic book collection
	 * @param comicbook
	 */
	public void removeComicIssue(ComicComponent comicbook)
	throws IllegalArgumentException
	{
		//make sure the comic is part of the series
		String inputSeriesName=comicbook.getSeriesName();
		String inputSeriesYear=comicbook.getIssuePublishYear();
		if((this.seriesName.equals(inputSeriesName))&& 
				(this.publishYear.equals(inputSeriesYear))) 
		{
			/**if it is a single comic validate then remove it
			 *if a collection find each issue and remove individually
			 **/
			if(comicbook.getClass() == ComicIssue.class)
			{
				ComicIssue comic=(ComicIssue) comicbook;
				String comicIssueNum=comic.getIssueNumber();
				Integer comicIndex=findComicIssue(comicIssueNum);
				if(comicIndex != null)
				{
					comics.remove(comicIndex.intValue());
				}
				else
				{
					throw new IllegalStateException("Can not remove comic. "+
							"The comic does not already exist in the collection. Try adding a comic first");
				}
				
			 }
			else
			{
				ComicCollection collectedComic=(ComicCollection) comicbook;
				LinkedList<ComicComponent> listOfComics=collectedComic.getComicList();
				int collectionSize=collectedComic.getSize();
				ComicComponent currentComic;
				for(int i=0; i<collectionSize; i++)
				{
					currentComic=listOfComics.get(i);
					if(currentComic.getClass() != ComicIssue.class)
					{
						throw new IllegalArgumentException("Error: Trying to remove a collection of collections");
					}
					else
					{
						ComicIssue comic=(ComicIssue) currentComic;
						String comicIssueNum=comic.getIssueNumber();
						Integer comicIndex=findComicIssue(comicIssueNum);
						if(comicIndex != null)
						{
							comics.remove(comicIndex.intValue());
						}
						else
						{
							throw new IllegalStateException("Can not remove comic. "
									+ "The comic does not already exist in the collection. Try adding a "
									+ "comic first");
						}
					}
				}
			}
			
		}
		else
		{
			throw new IllegalArgumentException("The comic you are trying to remove is not in the series");
		}
			
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
			if(comicIssueNum.equals(currentIssueNum))
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
		return this.publishYear;
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
