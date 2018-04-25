package components;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;

import org.joda.time.LocalDateTime;
public class DateBot {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LocalTime now = LocalTime.now();
		LocalDate localDate = LocalDate.now();
		DayOfWeek day = localDate.getDayOfWeek();
		
		
		System.out.println("The day is " + day.toString().toLowerCase());
		System.out.println("The Date is " + localDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		//now.toSecondOfDay();
		//LocalTime.parse(now);
		System.out.println("The time is " + now.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		
		
	}

}
