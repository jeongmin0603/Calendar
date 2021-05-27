package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.Holiday;
import db.SQLite;
import db.Style;

public class CalendarFrame extends BaseFrame {
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JPanel> panels = new ArrayList<JPanel>();
	
	JFrame jf = createBaseFrame(800, 900);
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel titleDatePanel = new JPanel(new FlowLayout());
	JPanel close = new JPanel(new FlowLayout());
	JPanel back = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JPanel next = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	JPanel lightOrDark = new JPanel(new FlowLayout());
	JPanel nowDatePanel = new JPanel(new FlowLayout());
	static JPanel datePanel = new JPanel(new GridLayout(0, 7));

	static int year;
	static int month;

	public CalendarFrame() {
		x = 1120;
		y = 0;
		
		background = Style.getBackgroundColor_light();
		font = Style.getFontColor_light();
		
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);

		mainPanel.add(getTitlePanel(), BorderLayout.NORTH);
		mainPanel.add(getCalendarPanel(), BorderLayout.CENTER);
	
		jf.setLocation(x, y);
		jf.add(mainPanel);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	private JPanel getNowTimePanel() {
		nowDatePanel.setBackground(background);
		nowDatePanel.setForeground(font);
		
		new Thread(new Runnable() {			
			@Override
			public void run() {
				SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
				String time;
				while(true) {
					try {
						Thread.sleep(1000);
						time = format.format(Calendar.getInstance().getTime());
						nowDatePanel.removeAll();
						nowDatePanel.add(getTextLabel(time, 20, font));
						nowDatePanel.revalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}			
				}
			}
		}).start();
		
		
		return nowDatePanel;
	}
	
	private JPanel getTitlePanel() {
		JPanel panel = new JPanel(new BorderLayout());

		setIcons("light");
		
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actNextArrow();
			}
		});

		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actBackArrow();
			}
		});

		JPanel date = new JPanel(new FlowLayout());
		setTitleDatePanel();
		date.add(back);
		date.add(titleDatePanel);
		date.add(next);

		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		lightOrDark.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (background.equals(Style.getBackgroundColor_light())) {
					font = Style.getFontColor_dark();
					background = Style.getBackgroundColor_dark();
					setIcons("dark");
				} else {
					background = Style.getBackgroundColor_light();
					font = Style.getFontColor_light();
					setIcons("light");
				}
				
				for(int i = 0; i < panels.size(); i++) {
					panels.get(i).setBackground(background);
				}
				for(int i = 0; i < labels.size(); i++) {
					labels.get(i).setForeground(font);
				}
				
				nowDatePanel.setBackground(background);
				nowDatePanel.getComponent(0).setForeground(font);
				
				setTitleDatePanel();
				setDatePanel();
			}
		});

		panel.add(lightOrDark, BorderLayout.WEST);
		panel.add(close, BorderLayout.EAST);
		panel.add(date, BorderLayout.CENTER);
		panel.add(getNowTimePanel(), BorderLayout.SOUTH);
		
		panels.add(panel);
		panels.add(date);
		
		date.setBackground(background);
		return panel;
	}
	private void setIcons(String bright) {
		mainPanel.setBorder(new LineBorder(background, 40, false));
		
		close.removeAll();
		back.removeAll();
		next.removeAll();
		lightOrDark.removeAll();
		
		close.add(new JLabel(getImageIcon(bright + "\\close.png", 30, 30)));
		back.add(new JLabel(getImageIcon(bright + "\\arrow_back.png", 40, 40)));
		next.add(new JLabel(getImageIcon(bright + "\\arrow_next.png", 40, 40)));
		
		if(bright.equals("light")) {
			lightOrDark.add(new JLabel(getImageIcon(bright + "\\sun.png", 30, 30)));
		} else {
			lightOrDark.add(new JLabel(getImageIcon(bright + "\\moon.png", 30, 30)));
		}
		
		close.setBackground(background);
		back.setBackground(background);
		next.setBackground(background);
		lightOrDark.setBackground(background);
	}
	private void setTitleDatePanel() {
		titleDatePanel.removeAll();

		JPanel yearAndMonthPanel = new JPanel(new BorderLayout());

		JLabel monthInt = getTextLabel(String.format("%02d", month + 1), 60, font);
		JLabel monthStr = getTextLabel(getMonthString(month + 1), 25, font);
		JLabel yearStr = getTextLabel(Integer.toString(year), 25, font);
		
		yearAndMonthPanel.add(yearStr, BorderLayout.SOUTH);
		yearAndMonthPanel.add(monthStr, BorderLayout.NORTH);

		titleDatePanel.add(monthInt);
		titleDatePanel.add(yearAndMonthPanel);
		
		titleDatePanel.setBackground(background);
		yearAndMonthPanel.setBackground(background);
		
		titleDatePanel.revalidate();
	}

	private JPanel getCalendarPanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		JPanel dayOfWeekPanel = new JPanel(new GridLayout(0, 7));
		
		for (int i = 0; i < 7; i++) {
			JLabel label = getTextLabel(getDayOfWeek(i), 15, font);
			label.setHorizontalAlignment(JLabel.CENTER);
			dayOfWeekPanel.add(label);
			labels.add(label);
		}

		setDatePanel();

		panel.add(dayOfWeekPanel, BorderLayout.NORTH);
		panel.add(datePanel, BorderLayout.CENTER);

		panels.add(panel);
		panels.add(dayOfWeekPanel);
		
		dayOfWeekPanel.setBackground(background);
		panel.setBackground(background);
		
		return panel;
	}

	private void actNextArrow() {

		month--;
		if (month + 1 < 1) {
			year--;
			month = 11;
		}
		setTitleDatePanel();
		setDatePanel();
	}

	private void actBackArrow() {
		month++;
		if (month + 1 > 12) {
			year++;
			month = 0;
		}
		setTitleDatePanel();
		setDatePanel();
	}

	public static void setDatePanel() {
		datePanel.removeAll();
		
		Map<String, String> holidays = Holiday.getHoliday(year, month + 1);

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		int day = 1;
		int max = cal.getActualMaximum(Calendar.DATE);
		int start = cal.get(Calendar.DAY_OF_WEEK);

		for (int i = 1; day <= max; i++) {
			JPanel p = new JPanel(new BorderLayout());
			p.setBackground(background);
			
			if (start <= i) {
				JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
				JLabel date = getTextLabel(Integer.toString(day), 15, font);
				
				String holiday = holidays.get(String.format("%04d%02d%02d", year, month + 1, day));
				
				if (i % 7 == 1 || holiday != null)
					date.setForeground(Style.getSundayColor());
				else if (i % 7 == 0)
					date.setForeground(Style.getSaturdayColor());
				else 
					date.setForeground(font);
			
				flow.add(date);
				flow.setBorder(new LineBorder(background, 5, false));
				flow.setBackground(background);
				
				
				if (holiday != null) {
					JLabel hol = getTextLabel(holiday, 10, font);
					hol.setForeground(Style.getSundayColor());
					flow.add(hol);
				}

				p.add(getSchedulePanel(String.format("%04d%02d%02d", year, month + 1, day)), BorderLayout.CENTER);
				p.add(flow, BorderLayout.NORTH);
				p.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							ResultSet rs = SQLite.getResultSet("select count(*) from schedule where date = '" + p.getName() + "'");
							try {
								if(rs.next()) {
									if(rs.getInt("count(*)") > 2) {
										JOptionPane.showMessageDialog(null, "스케줄은 최대 3개까지 입력 가능합니다.", "경고",
												JOptionPane.ERROR_MESSAGE);
									} else {		
										new AddScheduleFrame(p.getName(), p.getX(), p.getY());
									}
								} else {
									new AddScheduleFrame(p.getName(), p.getX(), p.getY());
								}
								rs.close();								
							}catch(Exception e1) {}
						}
					}
				});
				
				p.setName(String.format("%04d%02d%02d", year, month + 1, day));
				p.setBorder(new LineBorder(new Color(209, 209, 209)));
				datePanel.add(p);
				day++;
			} else {
				datePanel.add(p);
			}
		}
		datePanel.setBackground(background);
		datePanel.revalidate();
		datePanel.repaint();
	}
	
	private static JPanel getSchedulePanel(String date) {
		JPanel panel = new JPanel(new GridLayout(0, 1));
		List<Map<String, JPanel>> list = new ArrayList<>();
		
		ResultSet rs = SQLite.getResultSet("select * from schedule where date = '" + date + "'");
		
		if(rs != null) {
			try {
				while(rs.next()) {
					JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JLabel label = getTextLabel(rs.getString("text"), 10, font);

					line.setName(rs.getString("s_no"));
					String c_no = rs.getString("c_no");
					
					if(c_no != null) {	
						Map<String, JPanel> map = new HashMap<>();
						map.put(c_no, line);
						list.add(map);
						label.setForeground(Style.getFontColor_light());
					}else {
						line.setBackground(background);	
					}					
						
					line.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(e.getClickCount() == 2) {
								int answer = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "경고", JOptionPane.ERROR_MESSAGE);
								if(answer == 0) {
									SQLite.setDb("delete from schedule where s_no = '" + line.getName() + "'");
									setDatePanel();
								}
							}
						}
					});
					line.add(label);
					panel.add(line);
				}		
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i < list.size(); i++) {
			Map<String, JPanel> map = list.get(i);
			for(String key : map.keySet()) {
				try {
					ResultSet rs1 = SQLite.getResultSet("select * from color where c_no = '" + key + "'");	
					Color color = new Color(rs.getInt("r"), rs.getInt("g"), rs.getInt("b"));
					map.get(key).setBackground(color);
					rs1.close();					
				}catch(Exception e) {}
			}
		}
		
		JPanel flow = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flow.add(panel);
		panel.setBackground(background);
		flow.setBackground(background);
		
		return flow;
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
