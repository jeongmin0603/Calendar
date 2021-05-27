package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import db.Style;

public class BaseFrame {
	static Color background = null;
	static Color font = null;
	
	public static int x;
	public static int y;
	public static int w;
	public static int h;
	
	public static JFrame createBaseFrame(int w, int h) {
		JFrame jf = new JFrame();
		jf.setSize(w, h);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setUndecorated(true);
		jf.setBackground(new Color(0,0,0,0));
		return jf;
	}
	
	public static JLabel getTextLabel(String text, int size, Color color) {
		JLabel label = new JLabel(text);
		label.setFont(new Font("HY헤드라인M", Font.PLAIN, size));
		label.setForeground(color);
		return label;
	}
 	
	public static ImageIcon getImageIcon(String file, int width, int heigth) {
		return new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "\\icon\\" + file).getImage().getScaledInstance(width, heigth, Image.SCALE_SMOOTH));
	}
	
	
}
