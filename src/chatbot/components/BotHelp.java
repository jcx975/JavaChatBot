
/*
 * BotHelp
 * 
 * Shows help information about the bot.
 */

package chatbot.components;

public class BotHelp {
	public static String getHelp() {
		String response = "Hello! I can help with many things, such as: " +
						"\nMath (for example, \"what is two plus two?\")" +
						"\nWeather (for example, \"what is the weather in winona,us?\")" +
						"\nDirections (for example, \"directions to Rochester\")" +
                        "\nTweets (for example, \"tweets from @kanyewest\"";
	
		return response;				
	}
}
