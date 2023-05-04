package testUtils;

import model.ComicCollection;

public class NestedComicCollection extends ComicCollection {

	public NestedComicCollection(String name, String year) {
		super(name, year);
	}
	
	public void nestedAdd(ComicCollection nestedCollection)
	{
		super.comics.add(nestedCollection);
	}

	
}
