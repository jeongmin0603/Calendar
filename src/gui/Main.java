package gui;

import db.Style;

public class Main {
	public static void main(String[] args) {
		Style.setSheduleColors();
		new CalendarFrame();
	}
}
