package gui;

import db.Colors;

public class Main {
	public static void main(String[] args) {
		Colors.setSheduleColors();
		new CalendarFrame();
	}
}
