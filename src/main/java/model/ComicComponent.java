package model;

/**
 * The comic component interface is a component pattern
 * that can be used to implement a single comic or a series of
 * comics
 * @author rmbackoTU-dev
 *
 */
public interface ComicComponent {
	

	public ComicComponent getComicComponent(String name, String year, String issue);
	
	public String getIssuePublishYear();
	
	
	
	@Override
	public String toString();
	
	
}
