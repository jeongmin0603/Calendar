package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.scene.layout.Border;

public class CalendarFrame extends BaseFrame {
	JFrame jf = createBaseFrame(500, 600, "달력");
	
	int year;
	int month;

	public CalendarFrame() {
		JPanel panel = new JPanel(new BorderLayout());
		
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		
		panel.add(setTitlePanel());
		
		jf.add(panel);
		jf.setVisible(true);
	}
	
	private JPanel setTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		JPanel yearAndMonthPanel = new JPanel(new BorderLayout());
		JPanel date = new JPanel(new FlowLayout());
		
		JLabel monthInt = new JLabel(String.format("%d", this.month + 1));
		monthInt.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 60));
		
		JLabel monthStr = new JLabel(getMonthString(this.month + 1));
		monthStr.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		monthStr.setForeground(Color.gray);
		
		JLabel yearStr =  new JLabel(Integer.toString(year));
		yearStr.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
		yearStr.setForeground(Color.gray);
		
		yearAndMonthPanel.add(yearStr, BorderLayout.SOUTH);
		yearAndMonthPanel.add(monthStr, BorderLayout.NORTH);
		
		date.add(monthInt);
		date.add(yearAndMonthPanel);
		
		panel.add(date);
		return panel;
	}
	
	private String getMonthString(int month) {
		String monthStr = "";
		
		switch (month) {
		case 1:
			monthStr = "January";
			break;
		case 2:
			monthStr = "February";
			break;
		case 3:
			monthStr = "March";
			break;
		case 4:
			monthStr = "April";
			break;
		case 5:
			monthStr = "May";
			break;
		case 6:
			monthStr = "June";
			break;
		case 7:
			monthStr = "July";
			break;
		case 8:
			monthStr = "August";
			break;
		case 9:
			monthStr = "September";
			break;
		case 10:
			monthStr = "October";
			break;
		case 11:
			monthStr = "November";
			break;
		case 12:
			monthStr = "December";
			break;
		}
		return monthStr;
	}
}
