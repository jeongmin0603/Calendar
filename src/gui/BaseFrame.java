package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db.Colors;

public class BaseFrame {
	public JFrame createBaseFrame(int w, int h, String title) {
		JFrame jf = new JFrame();
		jf.setSize(w, h);
		jf.setTitle(title);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setUndecorated(true);
		jf.setBackground(new Color(0,0,0,0));
		return jf;
	}
	
	public JLabel getTextField(int size) {
		JLabel text = new JLabel();
		text.setFont(new Font("맑은 고딕", Font.BOLD, size));
		text.setForeground(Colors.getFontColor());
		return text;
	}
	

	
}
