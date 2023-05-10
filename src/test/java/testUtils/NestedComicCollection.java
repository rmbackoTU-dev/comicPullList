package testUtils;

import model.ComicCollection;
import model.ComicComponent;
import java.util.LinkedList;

public class NestedComicCollection extends ComicCollection {

	private LinkedList<ComicComponent> NestComicList;
	
	public NestedComicCollection(String name, String year) {
		super(name, year);
	}
	
	public void nestedAdd(ComicCollection nestedCollection)
	{
		this.NestComicList=super.getComicList();
		this.NestComicList.add(nestedCollection);
		super.setComicList(NestComicList);
	}

	
}
