
package chatbot.utils;

public class InputData
{
	private String originalText;
	private String interpretation;
	private String response;
	private String category;
	private String operator;
	private String location;
	private int sentiment;
	public  int numCount = 0;
	private int[] nums;
	
	public InputData( String originalText )
	{
		this.originalText = originalText;
		category = "none";
		operator = "none";
		location = "none";
		nums = new int[ 2 ];
	}
	
	public void setResponse( String response )
	{
		this.response = response;
	}
	
	public String getResponse()
	{
		return response;
	}
	
	public void setInterpretation( String interpretation )
	{
		this.interpretation = interpretation;
	}
	
	public String getInterpretation()
	{
		return interpretation;
	}
	
	public void setSentiment( int sentiment )
	{
		this.sentiment = sentiment;
	}
	
	public int getSentiment()
	{
		return sentiment;
	}
	
	public void setCategory( String category )
	{
		this.category = category;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public boolean isCategory( String query )
	{
		return category.equals( query );
	}
	
	public void setLocation( String location )
	{
		this.location = location;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void addInteger( int number )
	{
		if ( numCount <= 1 )
			nums[ numCount++ ] = number;
	}
	
	public int getInteger( int index )
	{
		return nums[ index ];
	}
	
	public void setMathOperator( String operator )
	{
		this.operator = operator;
	}
	
	public String getMathOperator()
	{
		return operator.toLowerCase();
	}
}
