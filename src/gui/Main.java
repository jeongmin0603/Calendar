package gui;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JFrame;
import javax.swing.JLabel;

import db.SQLite;

public class Main {
	public static void main(String[] args) {
		try {
			SQLite.setTable();
			new CalendarFrame();
			
		} catch(Exception e) {
			JFrame jf = new JFrame();
			
			StringWriter str = new StringWriter();
			e.printStackTrace(new PrintWriter(str));
			
			jf.add(new JLabel(str.toString()));
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setSize(500, 500);
			jf.setVisible(true);
		}
	}
}
