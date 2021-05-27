package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.SQLite;
import db.Style;

public class AddScheduleFrame extends BaseFrame {
	static JFrame menu = new JFrame();
	static JFrame colorPalette = new JFrame();
	JTextField text = new JTextField();
	
	String pick = null;
	String date;
	String s_no;
	
	Color color = null;

	boolean isChange = false;
	
	int x;
	int y;

	public AddScheduleFrame(String s_no, String date, String text, int x, int y, Color color) {
		this.s_no = s_no;
		this.x = x;
		this.y = y;
		
		this.text.setText(text);
		this.color = color;
		this.date = date;
		
		isChange = true;
		
		setBaseFrame();
	}
	public AddScheduleFrame(String date, int x, int y) {
		this.date = date;
		this.x = x;
		this.y = y;
		
		setBaseFrame();
	}
	private void setBaseFrame() {
		if (menu.isVisible()) {
			menu.setVisible(false);
		}
		if (colorPalette.isVisible()) {
			colorPalette.setVisible(false);
		}
		menu = createBaseFrame(140, 150);
		colorPalette = createBaseFrame(100, 50);

		setColorPaletteFrame();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new LineBorder(Color.gray));
		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(getInputPanel(), BorderLayout.CENTER);
		panel.add(getNavPanel(), BorderLayout.SOUTH);

		panel.setBackground(background);
		menu.add(panel);
		menu.setLocation(x + 1160, y + 157);
		
		menu.setVisible(true);
	}

	private void setColorPaletteFrame() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(getColorPalette(), BorderLayout.CENTER);
		panel.setBackground(background);

		colorPalette.add(panel);
	}

	private JPanel getColorPalette() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(background);

		JPanel palette = new JPanel(new GridLayout(0, 5, 1, 1));
		palette.setBackground(background);
		
		try {
			ResultSet rs = SQLite.getResultSet("select * from color");
			while(rs.next()) {
				JButton btn = new JButton();
				btn.setBackground(Color.BLACK);
				btn.setBackground(new Color(rs.getInt("r"), rs.getInt("g"), rs.getInt("b")));
				btn.addActionListener(v -> {
					pick = btn.getName();
					colorPalette.dispose();
				});
				btn.setName(rs.getString("c_no"));
				btn.setPreferredSize(new Dimension(19, 19));
				palette.add(btn);			
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.add(palette);

		return panel;
	}

	private JPanel getInputPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		text.setPreferredSize(new Dimension(130, 65));
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!text.getText().isEmpty()) {
					if (text.getText().length() > 10) {
						text.setText(text.getText().substring(0, 10));
						CalendarFrame.setDatePanel();
					}
				}
			}

		});
		panel.setBackground(background);
		panel.add(text);
		return panel;
	}

	private String getFilePath() {
		String file;
		if (background.equals(Style.getBackgroundColor_dark())) {
			file = "dark";
		} else {
			file = "light";
		}
		return file;
	}

	private JPanel getNavPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(background);

		JPanel palette = new JPanel(new FlowLayout());
		palette.setBackground(background);
		palette.add(new JLabel(getImageIcon(getFilePath() + "\\palette.png", 20, 20)));
		palette.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (colorPalette.isVisible()) {
					colorPalette.setVisible(false);
				} else {
					colorPalette.setLocation(x + 1220, y + 265);
					colorPalette.setVisible(true);
				}
			}
		});

		JPanel complete = new JPanel(new FlowLayout());
		complete.add(new JLabel(getImageIcon(getFilePath() + "\\check.png", 20, 20)));
		complete.setBackground(background);
		complete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (!text.getText().isEmpty()) {
						if(isChange) {
							if (pick != null) {
								System.out.println("update schedule set text = '" + text.getText() + "' and c_no = '" + pick + "' where s_no = '" + s_no + "'");
								SQLite.setDb("update schedule set text = \"" + text.getText() + "\", c_no = \"" + pick + "\" where s_no = '" + s_no + "'");
								
							} else {
								ResultSet rs = SQLite.getResultSet("select * from color where r = '" + color.getRed() + "' and g = '" + color.getGreen() + "' and b = '" + color.getBlue() + "'");
								System.out.println("select * from color where r = '" + color.getRed() + "' and g = '" + color.getGreen() + "' and b = '" + color.getBlue() + "'");
								if(rs.next()) {
									String c_no = rs.getString("c_no");
									System.out.println("update schedule set text = '" + text.getText() + "' and c_no = '" + c_no + "' where s_no = '" + s_no + "'");
									SQLite.setDb("update schedule set text = \"" + text.getText() + "\", c_no = \"" + c_no + "\" where s_no = '" + s_no + "'");
								} else {
									SQLite.setDb("update schedule set text = \"" + text.getText() + "\" where s_no = '" + s_no + "'");
								}
								
								rs.close();
							}
						} else {							
							if (pick != null) {
								SQLite.insertSchedule(date, text.getText(), pick);
							} else {
								SQLite.insertSchedule(date, text.getText());
							}
						}

						CalendarFrame.setDatePanel();
					}
					
					menu.setVisible(false);
					colorPalette.setVisible(false);
				} catch(Exception e1) {e1.printStackTrace();} 
				
			}
		});

		panel.add(palette);
		panel.add(complete);

		return panel;
	}

	private JPanel getTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(background);
		
		JPanel now = new JPanel(new FlowLayout());
		now.add(getTextLabel(date.substring(0, 4) + "년 " + date.substring(5, 6) + "월 " + date.substring(7, 8) + "일", 12, font));
		now.setBackground(background);

		JPanel close = new JPanel(new FlowLayout());
		close.add(new JLabel(getImageIcon("light\\close.png", 10, 10)));
		close.setBackground(background);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				menu.setVisible(false);
				colorPalette.setVisible(false);
			}
		});

		panel.add(now);
		panel.add(close);

		return panel;
	}

}
