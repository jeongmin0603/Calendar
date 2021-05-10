package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import db.Colors;
import db.Data;
import db.Schedule;

public class AddScheduleFrame extends BaseFrame {
	static JFrame menu = createBaseFrame(140, 150);
	static JFrame colorPalette = createBaseFrame(100, 50);
	JTextField text = new JTextField();
	Color pick = null;
	String date;
	int x;
	int y;

	public AddScheduleFrame(String date, int x, int y) {
		if(menu.isVisible()) {
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

		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(getInputPanel(), BorderLayout.CENTER);
		panel.add(getNavPanel(), BorderLayout.SOUTH);
		
		menu.add(panel);
		menu.setLocation(x + 1220, y + 115);
		menu.setVisible(true);
	}

	private void setColorPaletteFrame() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(getColorPalette(), BorderLayout.CENTER);
		
		colorPalette.add(panel);
	}
	private JPanel getColorPalette() {
		JPanel panel = new JPanel(new FlowLayout());
		
		JPanel palette = new JPanel(new GridLayout(0, 5, 1, 1));
		for (int i = 0; i < Colors.getCheckColors().size(); i++) {
			JPanel p = new JPanel(new FlowLayout());
			JButton btn = new JButton();
			btn.setBackground(Color.BLACK);
			btn.setBackground(Colors.getCheckColors().get(i));
			btn.addActionListener(v -> {
				pick = btn.getBackground();
				
			
			});
			btn.setPreferredSize(new Dimension(19, 19));
			palette.add(btn);
		}
		panel.add(palette);
		
		return panel;
	}

	private JPanel getInputPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		text.setPreferredSize(new Dimension(140, 80));
		panel.add(text);
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!text.getText().isEmpty()) {
					if(text.getText().length() > 10) {
						System.out.println(text.getText());
						text.setText(text.getText().substring(0, 10));
						CalendarFrame.setDatePanel();
					}
				}
			}
		});
		return panel;
	}
	
	private JPanel getNavPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		
		ImageIcon icon = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\icon\\palette.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JLabel palette = new JLabel(icon);
		palette.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (colorPalette.isVisible()) {
					colorPalette.setVisible(false);
				} else {
					colorPalette.setLocation(x + 1220, y + 265);
					colorPalette.setVisible(true);
				}
			}
		});
		
		icon = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\icon\\check.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		JLabel complete = new JLabel(icon);
		
		panel.add(palette);
		panel.add(complete);
		complete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				if(!text.getText().isEmpty()) {
					if(!Data.schedule.containsKey(date)) {					
						Data.schedule.put(date, new ArrayList<Schedule>());
					} 
					if (pick != null) {
						Data.schedule.get(date).add(new Schedule(text.getText(), pick));
					} else {						
						Data.schedule.get(date).add(new Schedule(text.getText()));
					}
					
					CalendarFrame.setDatePanel();
				}
				menu.setVisible(false);
				colorPalette.setVisible(false);
			}
		});
		return panel;
	}
	private JPanel getTitlePanel() {
		JPanel panel = new JPanel(new FlowLayout());
		String[] split = date.split("-");

		JPanel now = new JPanel(new FlowLayout());
		JLabel nowLabel = new JLabel(split[0] + "년 " + split[1] + "월 " + split[2] + "일");
		nowLabel.setFont(new Font("HY헤드라인M", Font.PLAIN, 12));
		now.add(nowLabel);

		ImageIcon icon = new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\icon\\close.png").getImage()
				.getScaledInstance(17, 17, Image.SCALE_SMOOTH));
		JLabel close = new JLabel(icon);
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
