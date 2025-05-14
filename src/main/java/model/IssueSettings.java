package model;


/**
 * Class is used to further define issue predicting 
 * it gets the next issue in the series
 */
public class IssueSettings {

		private Integer issue;
		private String issueString;
		private boolean subIssue;
		private boolean staticSubIssue;
		private String staticIssueString;
		private static final char[] ALPHABET= {'a', 'b', 'c', 'd', 'e',
			'f','g','h','i','j','k','l','m','n','o','p','q','r',
			's','t','u','v','w','x','y','z'};
		private String lastUsedIssue="";
		
		public IssueSettings()
		{
			subIssue=false;
			staticSubIssue=false;
			staticIssueString="";
			issue=1;
		}
		
		
		
		public String getIssue()
		{
			return this.issueString;
		}
		
		public void setIssue(int num)
		{
			this.issue=num;
			this.issueString=this.issue.toString();
		}
		
		/**
		 * This setting states whether the next comic in the series can expect to be a sub issue
		 * developers can set this when they believe a comic is going into a sub issue run and unset it if it is not
		 * in a sub issue run.
		 * @param setting
		 *
		 */
		public void setSubIssueSetting(boolean setting)
		{
			this.subIssue=setting;
		}
		
		/**
		 * A Static sub issue is a sub issue which progresses with only one alphabetic sequence 
		 * AKA Amazing Spider-Man 551DAR1 
		 * DAR is that static sub issue and the number progresses
		 * if static subissue is set then the string for the static part of the sub issue must be set via setStatic Issue
		 * @param setting
		 */
		public void setStaticSubIssueSetting(boolean setting)
		{
			this.staticSubIssue=false;
		}
		
		/**
		 * Getter for if an issue is in a sub issue run
		 * @return
		 */
		public boolean getSubIssueSetting()
		{
			return subIssue;
		}
		
		/**
		 * Getter for is an issue sub issue is static.
		 * @return
		 */
		public boolean getStaticSubIssueSetting()
		{
			return staticSubIssue;
		}
		
		
		/**
		 * Getter for the static issue used in the sub issue.
		 * @return
		 */
		public void setStaticIssue(String issue)throws IllegalStateException
		{
			if(this.staticSubIssue)
			{
				this.staticIssueString=issue;
			}
			else
			{
				throw new IllegalStateException("Static Sub issue setting not set");
			}
		}
		
		
		public String getNextIssue() 
		{
			if(this.subIssue)
			{
				return this.issueString+getNextSubIssue();
			}
			else
			{
				this.issue+=1;
				this.issueString=this.issue.toString();
				return this.issueString;
			}
		}
		
		/**
		 * For non static sub issues gets the last letter used for the sub issue.
		 * @return
		 * @throws IllegalStateException
		 */
		public String getLastSubIssue() throws IllegalStateException
		{
			if(this.lastUsedIssue != "")
			{
				return this.lastUsedIssue;
			}
			else
			{
				throw new IllegalStateException("No Sub Issues created yet.");
			}
		}
		
		public String getNextSubIssue() throws IllegalStateException
		{
			String subIssueResult;
			if(this.subIssue)
			{
				if(this.staticSubIssue)
				{
					if(this.staticIssueString.equals(""))
					{
						throw new IllegalStateException("Static issue set but no static issue defined, please set static issue");
					}
					else
					{
						subIssueResult=this.staticIssueString;
					}
				}
				else
				{
					int issueLength=this.lastUsedIssue.length();
					if(issueLength == 0)
					{
						this.lastUsedIssue=String.valueOf(ALPHABET[0]);
						subIssueResult=this.lastUsedIssue;
					}
					else if(issueLength ==2)
					{
						//loop up to twice 2 chooses 26 is still 351 sub issues
						String lastChar=this.lastUsedIssue.substring(issueLength-1);
						String firstChar=this.lastUsedIssue.substring(0);
						if(lastChar.equals("z"))
						{
							if(firstChar.equals("z"))
							{
								throw new  IllegalStateException("subIssue limit reached can no longer increment");
							}
							else
							{
								//find next letter
								char firstToChar=firstChar.charAt(0);
								firstChar=getNextLetter(firstToChar);
								lastChar=String.valueOf(ALPHABET[0]);
								subIssueResult=firstChar+lastChar;
								this.lastUsedIssue=subIssueResult;
							}
						}
						else
						{
							char lastToChar=lastChar.charAt(0);
							lastChar=getNextLetter(lastToChar);
							subIssueResult=firstChar+lastChar;
							this.lastUsedIssue=subIssueResult;
						}
					}
					else if(issueLength == 1)
					{
						String currentChar=this.lastUsedIssue.substring(0);
						char currentToChar=currentChar.charAt(0);
						currentChar=getNextLetter(currentToChar);
						subIssueResult=currentChar;
						this.lastUsedIssue=subIssueResult;
					}
					else
					{
						throw new IllegalStateException("Non static issues should not have length greater than 2");
					}
				}
			}
			else
			{
				throw new IllegalStateException("Issue Settings not set to generate sub issues");
			}
			
			return subIssueResult;
		}
		
		private  static String getNextLetter(char letter) throws IllegalArgumentException
		{
			int nextCharIndex=-1;
			int index=0;
			while((nextCharIndex ==-1) && (index < 26))
			{
				if(letter == ALPHABET[index])
				{
					nextCharIndex=index;
				}
				else
				{
					index++;
				}
			}
			
			if(nextCharIndex == -1)
			{
				throw new IllegalArgumentException("Letter not a lower case alphabet letter");
				
			}
			else
			{
				String nextLetter=String.valueOf(ALPHABET[nextCharIndex]);
				return nextLetter;
			}
		}
}
