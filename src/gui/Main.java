package gui;

import db.Data;
import db.Style;

public class Main {
	public static void main(String[] args) {
		Style.setSheduleColors();
		
		if(!Data.isDbInit()) {
			System.out.println("hello");
		}
		Data.createDb();
		
		new CalendarFrame();
	}
}
