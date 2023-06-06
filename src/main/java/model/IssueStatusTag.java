package model;


	
public enum IssueStatusTag{
	ACTIVE{
		@Override
		public String getStateName()
		{
			return "active";
		}

		@Override
		public IssueStatusTag nextState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("pending"))
			{
				return PENDING;
			}
			else if(tagName.equals("backlog"))
			{
				return BACKLOG;
			}
			else
			{
				throw new IllegalStateException(tag.getStateName()+" is not a valid next state");
			}
		}

		@Override
		public IssueStatusTag previousState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("backlog"))
			{
				return BACKLOG;
			}
			else if(tagName.equals("skip"))
			{
				 return SKIP;
			}
			else if(tagName.equals("pending"))
			{
				return PENDING;
			}
			else
			{
				throw new  IllegalStateException(tag.getStateName()+" is not a valid previous state");
			}
		}
		
	},
	PENDING
	{
		@Override
		public String getStateName()
		{
			return "pending";
		}

		@Override
		public IssueStatusTag nextState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("active"))
			{
				return ACTIVE;
			}
			else if(tagName.equals("skip"))
			{
				return SKIP;
			}
			else if(tagName.equals("backlog"))
			{
				return BACKLOG;
			}
			else
			{
				//If they user asked for the same state that they already have that should be 
				//an IllegalStateException
				if(tagName.equals("pending"))
				{
					throw new IllegalStateException("The status is already set to pending");
				}
				else
				{
					throw new IllegalStateException("Unknown status");
				}
			}
		}

		@Override
		public IssueStatusTag previousState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tag.getStateName().equals("active"))
			{
				return ACTIVE;
			}
			else
			{
				throw new IllegalStateException(tag.getStateName()+" is not a valid previous state");
			}
		}
	},
	SKIP
	{
		@Override
		public String getStateName()
		{
			return "skip";
		}

		@Override
		public IssueStatusTag nextState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("active"))
			{
				return ACTIVE;
			}
			else
			{
				throw new IllegalStateException(tagName+" is not a valid next state");
			}
		}

		@Override
		public IssueStatusTag previousState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tag.getStateName().equals("pending"))
			{
				return PENDING;
			}
			else
			{
				throw new IllegalStateException(tag.getStateName()+" is not a valid previous state");
			}
		}
	},
	BACKLOG 
	{
		@Override
		public String getStateName()
		{
			return "backlog";
		}

		@Override
		public IssueStatusTag nextState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("active"))
			{
			return ACTIVE;
			}
			else
			{
				throw new IllegalStateException(tagName+ " is not a valid next state");
			}
		}

		@Override
		public IssueStatusTag previousState(IssueStatusTag tag) throws IllegalStateException {
			String tagName=tag.getStateName();
			if(tagName.equals("pending"))
			{
				return PENDING;
			}
			else if(tagName.equals("active"))
			{
				return ACTIVE;
			}
			else
			{
				throw new IllegalStateException(tagName+" is not a valid previous state");
			}
		}
	};
		
		
		public abstract String getStateName();
		
		public abstract IssueStatusTag nextState (IssueStatusTag tag) throws IllegalStateException;
		
		public abstract IssueStatusTag previousState(IssueStatusTag tag) throws IllegalStateException;
		
		
}
