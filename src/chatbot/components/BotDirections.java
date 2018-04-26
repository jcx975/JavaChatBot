
/*
 * BotDirections
 * 
 * Opens Google Maps with directions to specified location.
 */

package chatbot.components;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import chatbot.utils.InputData;

public class BotDirections {
	public static void goToMaps( InputData inputdata )
	{
		URI url = null;
		try {
			url = new URI( "https://www.google.com/maps?q=directions+to+" + inputdata.getLocation() );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		try {
			java.awt.Desktop.getDesktop().browse( url );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
