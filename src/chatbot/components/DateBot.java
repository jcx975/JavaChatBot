
/*
 * DateBot
 * 
 * Gets the current day, date and time.
 */

package chatbot.components;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import org.joda.time.LocalDateTime;

import chatbot.utils.InputData;
public class DateBot {

	public static String getInformation( InputData input )
	{	
		String response = "";
		
		LocalTime now = LocalTime.now();
		LocalDate localDate = LocalDate.now();
		DayOfWeek day = localDate.getDayOfWeek();
		
		response = "The day is " + day.toString().toLowerCase() + 
				"\nThe date is " + localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
				"\nThe time is " + now.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
//		System.out.println("The day is " + day.toString().toLowerCase());
//		System.out.println("The Date is " + localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		//now.toSecondOfDay();
		//LocalTime.parse(now);
//		System.out.println("The time is " + now.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		
		return response;
		
	}

}
