
package chatbot.components;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class BotDirections {
	public static void goToMaps( String location )
	{
		URI url = null;
		try {
			url = new URI( "https://www.google.com/maps?q=directions+to+" + location );
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			java.awt.Desktop.getDesktop().browse( url );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
