
package chatbot.components;

import java.util.Arrays;
import java.util.*;

public class BotMath {
	
	private static String[] mathStrings = { "multiply", "times","*", "add", "plus","+", "subtract", "minus","-", "divide", "divided","/" };
	public static List<String> mathOperations = new ArrayList<>( Arrays.asList( mathStrings ) );
	
	public static String calculate( int num1, int num2, String operation )
	{
		int result = 9999;
		
		operation = operation.toLowerCase();
		
		if ( operation.equals( "multiply" ) || operation.equals( "times" ) || operation.equals( "*" ) )
			result = num1 * num2;
		else if ( operation.equals( "add" ) || operation.equals( "plus" ) || operation.equals( "+" ) )
			result = num1 + num2;
		else if ( operation.equals( "subtract" ) || operation.equals( "minus" ) || operation.equals( "-" ) )
			result = num1 - num2;
		else if ( operation.equals( "divide" ) || operation.equals( "divided" ) || operation.equals( "/" ) )
			result = num1 / num2;
		
		return "The result of your query is: " + result;
		
	}
}
