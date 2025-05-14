package model;

import java.util.HashMap;

//Make the collection Manager a context 
public class ComicCollectionManager {
	
	/**
	 * need Issue settings for the nextIssue and the last Issue
	 * next issue is the next issue that was generated 
	 * last issue is to get the last issue that was generated
	 */
	private IssueSettings settings=new IssueSettings();
	
	private ComicCollection comicCollection=null;
	private String comicSeriesName;
	private String comicPublishYear;
	private String lastComicIssueGenerated;
	
	
	public ComicCollectionManager(String name, String publishYear)
	{
		this.comicSeriesName=name;
		this.comicPublishYear=publishYear;
		this.lastComicIssueGenerated="0";	
		
	}
	
	public String getCollectionSeriesName()
	{
		return this.comicSeriesName;
	}
	
	public String getCollectionPublishYear()
	{
		return this.comicPublishYear;
	}
	
	/*
	 * If a Collection does not exist create one if
	 * one does exist return a String array of it's issues
	 */
	public String[] getComicCollection()
	{
		String[] collection;
		if(this.comicCollection != null)
		{
			collection=this.comicCollection.getAllIssues();
			return collection;
		}
		else
		{
			this.comicCollection=new ComicCollection(this.comicPublishYear, this.comicSeriesName);
			collection=this.comicCollection.getAllIssues();
			return collection;
		}
	}
	
	public String getCurrentComicIssue()
	{
		return this.lastComicIssueGenerated;
	}
	
	public void addNextComic()
	{
		String issueNum;
		//seperate the numeric part from the subIssue using a validator
		int numb=Integer.valueOf(this.lastComicIssueGenerated);
		if(this.settings.getSubIssueSetting())
		{
			//next issue is a sub Issue
			String subIssue=this.settings.getNextSubIssue();
		}
		
	}
	
	

	/**
	 * Set the sub issue settings
	 * If staticSubIssue is true staticIssueSetting must have a length >1 
	 * additionally static Issue setting will be checked against validator
	 * to verify it meets all subIssue settings
	 */
	public void setSubIssueSettings(boolean subIssue, boolean staticSubIssue, String  staticIssueSetting)
	{
		//use to set the subIssueSettings
	

	}
	
}
