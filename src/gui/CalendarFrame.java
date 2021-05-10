package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.Colors;
import db.Data;
import db.Schedule;

public class CalendarFrame extends BaseFrame {
	JFrame jf = createBaseFrame(700, 800);
	JPanel titleDatePanel = new JPanel(new FlowLayout());
	static JPanel datePanel = new JPanel(new GridLayout(0, 7));

	static int year;
	static int month;

	public CalendarFrame() {
		Colors.setSheduleColors();

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(0, 0, 0));
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);

		panel.add(setTitlePanel(), BorderLayout.NORTH);
		panel.add(getDatePanel(), BorderLayout.CENTER);

		jf.setLocation(1220, 0);
		jf.add(panel);
		jf.setVisible(true);
	}

	private JPanel getDatePanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));

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
		JPanel panel = new JPanel(new BorderLayout());

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
				if (month + 1 < 1) {
					year--;
					month = 11;
				}
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
				if (month + 1 > 12) {
					year++;
					month = 0;
				}
				setTitleDatePanel();
				setDatePanel();
			}
		});

		JPanel date = new JPanel(new FlowLayout());

		setTitleDatePanel();
		date.add(back);
		date.add(titleDatePanel);
		date.add(next);

		icon = new ImageIcon(System.getProperty("user.dir") + "\\icon\\close.png");
		img = icon.getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		icon = new ImageIcon(img);
		JLabel close = new JLabel(icon);

		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
				super.mouseClicked(e);
			}
		});

		panel.add(close, BorderLayout.EAST);
		panel.add(date, BorderLayout.CENTER);
		return panel;
	}

	public static void setDatePanel() {
		datePanel.removeAll();

		Map<String, String> holidays = db.Holiday.getHoliday(year, month + 1);

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		int day = 1;
		int max = cal.getActualMaximum(Calendar.DATE);
		int start = cal.get(Calendar.DAY_OF_WEEK);

		for (int i = 1; day <= max; i++) {
			if (start <= i) {
				JPanel p = new JPanel(new BorderLayout());
				
				JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel date = new JLabel(Integer.toString(day));
				
				date.setFont(new Font(Data.getFont(), Font.PLAIN, 15));
				
				String holiday = holidays.get(String.format("%04d%02d%02d", year, month + 1, day));
			
				if (i % 7 == 1 || holiday != null)
					date.setForeground(Colors.getSundayColor());
				else if (i % 7 == 0)
					date.setForeground(Colors.getSaturdayColor());
					
				flow.setBackground(Color.WHITE);
				flow.add(date);
					
				if(holiday != null) {
					JLabel hol = new JLabel(holiday);
					hol.setFont(new Font(Data.getFont(), Font.PLAIN, 10));
					hol.setForeground(Colors.getSundayColor());
					flow.add(hol);
				}
				
				String now = String.format("%04d-%02d-%02d", year, month, day);

				if (Data.schedule.containsKey(now)) {
					p.add(getSchedulePanel(now), BorderLayout.CENTER);
				}


				p.add(flow, BorderLayout.NORTH);

				p.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						if (e.getClickCount() == 2) {
							if (Data.schedule.containsKey(p.getName())) {
								if (Data.schedule.get(p.getName()).size() > 4) {
									JOptionPane.showMessageDialog(null, "스케줄은 최대 4개까지 입력 가능합니다.", "경고",
											JOptionPane.ERROR_MESSAGE);
									return;
								}
							}
							new AddScheduleFrame(p.getName(), p.getX(), p.getY());
						}
					}
				});
				p.setName(now);
				p.setBorder(new LineBorder(new Color(209, 209, 209)));
				p.setBackground(Color.white);
				datePanel.add(p);

				day++;
			} else {
				datePanel.add(new JPanel());
			}
		}
		datePanel.revalidate();
		datePanel.repaint();
	}

	private static JPanel getSchedulePanel(String date) {
		JPanel panel = new JPanel(new GridLayout(0, 1));

		for (int j = 0; j < Data.schedule.get(date).size(); j++) {
			JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
			Schedule s = Data.schedule.get(date).get(j);

			JLabel label = new JLabel(s.getText());
			label.setFont(new Font(Data.getFont(), Font.PLAIN, 10));

			if (s.getColor() != null) {
				line.setBackground(s.getColor());
			} else {
				line.setBackground(Color.WHITE);
			}

			line.add(label);
			panel.add(line);
		}

		panel.setBackground(Color.WHITE);

		JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flow.setBackground(Color.white);
		flow.add(panel);
		return flow;
	}

	private void setTitleDatePanel() {
		titleDatePanel.removeAll();

		JPanel yearAndMonthPanel = new JPanel(new BorderLayout());

		JLabel monthInt = new JLabel(String.format("%02d", this.month + 1));
		monthInt.setFont(new Font(Data.getFont(), Font.PLAIN, 60));

		JLabel monthStr = new JLabel(getMonthString(this.month + 1));
		monthStr.setFont(new Font(Data.getFont(), Font.BOLD, 25));
		monthStr.setForeground(Color.gray);

		JLabel yearStr = new JLabel(Integer.toString(year));
		yearStr.setFont(new Font(Data.getFont(), Font.BOLD, 25));
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
