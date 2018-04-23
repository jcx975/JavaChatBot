package chatbot.components;
import java.io.*;
import java.util.*;
import java.net.*;
import com.google.gson.*;
import com.google.gson.reflect.*;


public class WeatherBot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String API_KEY = "371db33cd6e9a110a47526681330b720";
		String LOCATION = "Winona,us";
		String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + LOCATION 
				+ "&appid=" + API_KEY + "&units=imperial";
		try{
			StringBuilder result = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null){
				result.append(line);
			}
			rd.close();
			System.out.println(result);
			
			Map<String, Object> respMap = jsonToMap(result.toString());
			Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
			Map<String, Object> winMap = jsonToMap(respMap.get("wind").toString());
			
			System.out.println("Current Temperature: " + mainMap.get("temp"));
			System.out.println("Current Humidity: " + mainMap.get("humidity"));
			System.out.println("Wind speeds: " + mainMap.get("speed"));
			System.out.println("Wind Angle: " + mainMap.get("deg"));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
	public static Map<String, Object> jsonToMap(String str){
		Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>()
				{}.getType());
		return map;
	}

}
