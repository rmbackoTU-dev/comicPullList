package model;

import model.ComicIssue;
import model.IssueStatusTag;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestComicIssueEnum {

	public final ExpectedException thrown=ExpectedException.none();
	
	@Test
	public void testContructorStatus()
	{
		ComicIssue testComic=new ComicIssue("The Amazing Test Comic", "2023", "1");
		String statusIndex=testComic.getStatus();
		assertEquals("pending",statusIndex);
	}
	
	
	/**
	 * Test may over test but I wanted to walk through all the 
	 * transitions in the state machine, by setting up next states and rolling back 
	 * to an available preivous
	 */
	@Test
	public void testStatusStateMachineSunny()
	{ 
		//Sunny day valid paths
		ComicIssue testComic=new ComicIssue("The Amazing Test Comic", "2023", "1");
		String statusName=testComic.getStatus();
		assertEquals("pending",statusName);
		//For Sunny day we must progress before we roll back
		//Test PENDING SUNNY
		//path 1
		testComic.updateStatusNext("active");
		testComic.updateStatusNext("pending");
		//Follow chart for next few statuses
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("active", statusName);
		//path 2
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("skip");
		testComic.updateStatusNext("active");
		testComic.updateStatusNext("pending");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("active", statusName);
		//path 3
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("backlog");
		testComic.updateStatusNext("active");
		testComic.updateStatusNext("pending");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("active", statusName);
		//Test ACTIVE SUNNY
		//backlog path 1
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("backlog");
		testComic.updateStatusNext("active");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("backlog", statusName);
		//pending path loop
		testComic.updateStatusNext("active");
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("active");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("pending", statusName);
		//backlog path 2
		testComic.updateStatusNext("active");
		testComic.updateStatusNext("backlog");
		testComic.updateStatusNext("active");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("backlog", statusName);
		//TEST SKIP SUNNY
		testComic.updateStatusNext("active");
		//skip path 1
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("skip");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("pending", statusName);
		//TEST BACKLOG SUNNY
		testComic.updateStatusNext("active");
		//backlog path 3 (1 partial)
		testComic.updateStatusNext("pending");
		testComic.updateStatusNext("backlog");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("pending", statusName);
		//Test rest of Active Previous
		testComic.updateStatusNext("active");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("pending", statusName);
		testComic.updateStatusNext("skip");
		testComic.updateStatusNext("active");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("skip", statusName);
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("pending", statusName);
		testComic.updateStatusNext("backlog");
		testComic.updateStatusNext("active");
		statusName=testComic.getStatus();
		assertEquals("active", statusName);
		//Finish off Testing Backlog States.
		testComic.updateStatusNext("backlog");
		testComic.rollbackStatusToPrevious();
		statusName=testComic.getStatus();
		assertEquals("active", statusName);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyActiveNextSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.ACTIVE;
		testStatus.nextState(IssueStatusTag.ACTIVE);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyActivePreviousSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.ACTIVE;
		testStatus.previousState(IssueStatusTag.ACTIVE);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyActiveToSkip()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.ACTIVE;
		testStatus.nextState(IssueStatusTag.SKIP);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyPendingPreviousSelf()
	{ 
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.PENDING;
		testStatus.nextState(IssueStatusTag.PENDING);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyPendingNextSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.PENDING;
		testStatus.previousState(IssueStatusTag.PENDING);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyPendingPreviousSkip()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.PENDING;
		testStatus.previousState(IssueStatusTag.SKIP);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyPendingPreviousBacklog()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.PENDING;
		testStatus.previousState(IssueStatusTag.BACKLOG);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipNextSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.nextState(IssueStatusTag.SKIP);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipPreviousSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.previousState(IssueStatusTag.SKIP);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipNextBacklog()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.nextState(IssueStatusTag.BACKLOG);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipNextPending()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.nextState(IssueStatusTag.PENDING);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipPreviousActive()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.previousState(IssueStatusTag.ACTIVE);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainySkipPreviousBacklog()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.SKIP;
		testStatus.previousState(IssueStatusTag.BACKLOG);
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyBacklogNextSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.BACKLOG;
		testStatus.nextState(IssueStatusTag.BACKLOG);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyBacklogPreviousSelf()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.BACKLOG;
		testStatus.previousState(IssueStatusTag.BACKLOG);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyBacklogNextSkip()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.BACKLOG;
		testStatus.nextState(IssueStatusTag.SKIP);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyBacklogNextPending()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.BACKLOG;
		testStatus.nextState(IssueStatusTag.PENDING);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testStatusStateMachineRainyBacklogPreviousSkip()
	{
		thrown.expect(IllegalStateException.class);
		IssueStatusTag testStatus=IssueStatusTag.BACKLOG;
		testStatus.previousState(IssueStatusTag.SKIP);
	}
	
	@Test
	public void testUpdateStatus()
	{
		//Go through all legalStatuses then all illegalStatuses
		ComicIssue testComic=new ComicIssue("The Amazing Test Comic", "2023", "1");
		String statusIndex=testComic.getStatus();
		assertEquals("pending",statusIndex);
	}
	
	
}
