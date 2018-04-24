
package chatbot.utils;

public class InputData
{
	private String originalText;
	private String category;
	private String operator;
	private String location;
	public static int numCount = 0;
	private int[] nums;
	
	public InputData( String originalText )
	{
		this.originalText = originalText;
		category = "none";
		operator = "none";
		location = "none";
		nums = new int[ 2 ];
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
