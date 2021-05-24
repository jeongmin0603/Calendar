package gui;

import db.SQLite;

public class Main {
	public static void main(String[] args) {
		SQLite.setTable();
		new CalendarFrame();
	}
}
