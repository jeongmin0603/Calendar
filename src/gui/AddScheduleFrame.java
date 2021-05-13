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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.Data;
import db.Schedule;
import db.Style;

public class AddScheduleFrame extends BaseFrame {
	static JFrame menu = new JFrame();
	static JFrame colorPalette = new JFrame();
	JTextField text = new JTextField();
	Color pick = null;
	String date;

	int x;
	int y;

	public AddScheduleFrame(String date, int x, int y) {
		if (menu.isVisible()) {
			menu.setVisible(false);
		}
		if (colorPalette.isVisible()) {
			colorPalette.setVisible(false);
		}

		menu = createBaseFrame(140, 150);
		colorPalette = createBaseFrame(100, 50);

		this.date = date;
		this.x = x;
		this.y = y;

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

		for (int i = 0; i < Style.getCheckColors().size(); i++) {
			JButton btn = new JButton();
			btn.setBackground(Color.BLACK);
			btn.setBackground(Style.getCheckColors().get(i));
			btn.addActionListener(v -> {
				pick = btn.getBackground();
				colorPalette.dispose();
			});
			btn.setPreferredSize(new Dimension(19, 19));
			palette.add(btn);
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
						if (pick != null) {
							int cnt = 0;
							for(int i = 0; i < Style.checkColors.size(); i++) {
								if(pick.equals(Style.getCheckColors().get(i))) {
									cnt = i;
									break;
								}
							}
							
							Data.setDb("insert into schedule(date, text, c_no) values('" + date + "', '" + text.getText() + "', '" + cnt + "')");
						} else {
							Data.setDb("insert into schedule(date, text) values('" + date.replace("-", "") + "', '" + text.getText() + "')");
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
