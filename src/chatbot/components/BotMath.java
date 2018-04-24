
package chatbot.components;

import java.util.Arrays;

import chatbot.utils.InputData;

import java.util.*;

public class BotMath {
	
	private static String[] mathStrings = { "multiply", "times","*", "add", "plus","+", "subtract", "minus","-", "divide", "divided","/" };
	public static List<String> mathOperations = new ArrayList<>( Arrays.asList( mathStrings ) );
	
<<<<<<< HEAD
	public static int calculate( InputData inputdata )
=======
	public static String calculate( int num1, int num2, String operation )
>>>>>>> origin/master
	{
		int result = 9999;
		
		int num1 = inputdata.getInteger( 0 );
		int num2 = inputdata.getInteger( 1 );
		String operation = inputdata.getMathOperator();
		
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
