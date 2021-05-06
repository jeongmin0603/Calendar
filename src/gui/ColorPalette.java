package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import db.Colors;

public class ColorPalette extends BaseFrame {
	JFrame jf = createBaseFrame(130, 150, "색 선택");
	
	public ColorPalette(int x, int y) {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add(getColorPanel(), BorderLayout.CENTER);
		
		jf.add(panel);
		jf.setLocation(x, y);
		jf.setVisible(true);
	}
	public JPanel getColorPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		JPanel palette = new JPanel(new GridLayout(0, 5));
		
		for(int i = 0; i < Colors.getCheckColors().size(); i++) {
			JPanel p = new JPanel(new FlowLayout());
			JButton btn = new JButton();
			
			btn.setBackground(Colors.getCheckColors().get(i));
			btn.setBorder(new LineBorder(new Color(0,0,0,0)));
			btn.setPreferredSize(new Dimension(20, 20));
			p.add(btn);
			
			panel.add(p);
		}
		
		return panel;
	}
}
