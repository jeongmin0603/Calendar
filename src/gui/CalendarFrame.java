package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CalendarFrame extends BaseFrame {
	JFrame jf = createBaseFrame(500, 600, "달력");
	JPanel titleDatePanel = new JPanel(new FlowLayout());
	JPanel datePanel = new JPanel(new GridLayout(0,7));
	int year;
	int month;

	public CalendarFrame() {
		JPanel panel = new JPanel(new BorderLayout());
		
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		
		panel.add(setTitlePanel(), BorderLayout.NORTH);
		panel.add(getDatePanel(), BorderLayout.CENTER);
		
		jf.add(panel);
		jf.setVisible(true);
	}
	private JPanel getDatePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel dayOfWeekPanel = new JPanel(new GridLayout(0, 7));
		for (int i = 0; i < 7; i++) {
			JLabel dayOfWeek = new JLabel(getDayOfWeek(i));
			dayOfWeek.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
			dayOfWeek.setHorizontalAlignment(JLabel.CENTER);
			dayOfWeekPanel.add(dayOfWeek);
		}
		
		setDatePanel();
		
		panel.add(dayOfWeekPanel, BorderLayout.NORTH);
		panel.add(datePanel, BorderLayout.CENTER);
		
		return panel;
	}
	private JPanel setTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		JPanel back = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel next = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		ImageIcon icon;
		Image img; 
		
		icon = new ImageIcon(System.getProperty("user.dir") + "\\icon\\arrow_back.png");
		img = icon.getImage();
		img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		back.add(new JLabel(icon));
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				month--;
				setTitleDatePanel();
				setDatePanel();
			}
		});
		
		icon = new ImageIcon(System.getProperty("user.dir") + "\\icon\\arrow_next.png");
		img = icon.getImage();
		img = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		next.add(new JLabel(icon));
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				month++;
				setTitleDatePanel();
				setDatePanel();
			}
		});
		
		setTitleDatePanel();
		panel.add(back);
		panel.add(titleDatePanel);
		panel.add(next);
		return panel;
	}
	private void setDatePanel() {
		datePanel.removeAll();
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		
		int day = 1; 
		int max = cal.getActualMaximum(Calendar.DATE);
		int start = cal.get(Calendar.DAY_OF_WEEK);
		
		for(int i = 1; day <= max; i++) {
			if(start <= i) {
				JPanel p = new JPanel(new BorderLayout());
				p.setBackground(Color.white);
				
				JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel date = new JLabel(Integer.toString(day++));
				date.setFont(new Font("HY헤드라인M", Font.PLAIN, 15));
				flow.setBackground(Color.WHITE);
				flow.add(date);
				
				p.add(flow, BorderLayout.NORTH);
				
				p.setBorder(new LineBorder(new Color(209, 209, 209)));
				
				datePanel.add(p);
			} else {
				datePanel.add(new JPanel());
			}
		}
		datePanel.revalidate();
		datePanel.repaint();
	}

	private void setTitleDatePanel() {
		titleDatePanel.removeAll();
		
		JPanel yearAndMonthPanel = new JPanel(new BorderLayout());
		
		JLabel monthInt = new JLabel(String.format("%02d", this.month + 1));
		monthInt.setFont(new Font("HY헤드라인M", Font.PLAIN, 60));
		
		JLabel monthStr = new JLabel(getMonthString(this.month + 1));
		monthStr.setFont(new Font("HY헤드라인M", Font.BOLD, 25));
		monthStr.setForeground(Color.gray);
		
		JLabel yearStr =  new JLabel(Integer.toString(year));
		yearStr.setFont(new Font("HY헤드라인M", Font.BOLD, 25));
		yearStr.setForeground(Color.gray);
		
		yearAndMonthPanel.add(yearStr, BorderLayout.SOUTH);
		yearAndMonthPanel.add(monthStr, BorderLayout.NORTH);
		
		titleDatePanel.add(monthInt);
		titleDatePanel.add(yearAndMonthPanel);
		
		titleDatePanel.revalidate();
	}
	private String getDayOfWeek(int date) {
		String dateOfWeek = "";
		
		switch (date) {
		case 0:
			dateOfWeek = "SUN";
			break;
		case 1:
			dateOfWeek = "MON";
			break;
		case 2:
			dateOfWeek = "TUE";
			break;
		case 3:
			dateOfWeek = "WED";
			break;
		case 4:
			dateOfWeek = "THU";
			break;
		case 5:
			dateOfWeek = "FRI";
			break;
		case 6:
			dateOfWeek = "SAT";
			break;

		}
		
		return dateOfWeek;
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
