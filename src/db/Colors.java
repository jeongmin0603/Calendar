package db;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Colors { 
	public static Color fontColor = new Color(0, 0, 0);
	public static Color backGroundColor = new Color(255, 255, 255);	
	public static Color sundayColor = new Color(237, 76, 64);
	public static Color saturdayColor = new Color(54, 57, 255);
	public static List<Color> checkColors = new ArrayList<>();
	
	public static void setSheduleColors() {
		Color[] colors = {
				new Color(249, 237, 105),
				new Color(240, 138, 93),
				new Color(184, 59, 94),
				new Color(106, 44, 112),
				new Color(247, 56, 89),
				new Color(167, 255, 131),
				new Color(23, 185, 120),
				new Color(28, 197, 220),
				new Color(192, 226, 24),
				new Color(199, 0, 57),
		};
		
		checkColors = Arrays.asList(colors);
		
	}
	public static Color getSundayColor() {
		return sundayColor;
	}
	public static Color getSaturdayColor() {
		return saturdayColor;
	}
	public static Color getFontColor() {
		return fontColor;
	}
	public static Color getBackGroundColor() {
		return backGroundColor;
	}
	public static List<Color> getCheckColors() {
		return checkColors;
	}
	public static void addScheduleColors(Color color) {
		checkColors.add(color);
	}
}
