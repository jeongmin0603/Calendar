package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.Data;
import db.Schedule;

public class AddScheduleFrame extends BaseFrame {
	JFrame jf = createBaseFrame(140, 150, "스케줄 추가");
	JTextField text = new JTextField();
	Color pick = null;
	String date;
	int x;
	int y;

	public AddScheduleFrame(String date, int x, int y) {
		this.date = date;
		this.x = x;
		this.y = y;

		JPanel panel = new JPanel(new BorderLayout());

		panel.add(getTitlePanel(), BorderLayout.NORTH);
		panel.add(getInputPanel(), BorderLayout.CENTER);
		panel.add(getNavPanel(), BorderLayout.SOUTH);
		
		jf.add(panel);
		jf.setLocation(x, y);
		jf.setVisible(true);
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
				
				new ColorPalette(x - 140, y);
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
				
				if(!Data.schedule.containsKey(date)) {					
					Data.schedule.put(date, new ArrayList<Schedule>());
				} 
				Data.schedule.get(date).add(new Schedule(text.getText()));
				
				CalendarFrame.setDatePanel();
				
				jf.setVisible(false);
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
				jf.setVisible(false);
			}
		});
		
		panel.add(now);
		panel.add(close);

		return panel;
	}

}
