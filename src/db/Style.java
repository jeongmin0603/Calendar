package db;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Style { 
	public static String font = "HY헤드라인M";
	
	public static Color fontColor_light = new Color(0, 0, 0);
	public static Color fontColor_dark = new Color(241, 241, 241);
	public static Color backgroundColor_light = new Color(250, 250, 250);	
	public static Color backgroundColor_dark = new Color(35, 35,35);
	
	public static String getFont() {
		return font;
	}

	public static Color getFontColor_light() {
		return fontColor_light;
	}

	public static Color getFontColor_dark() {
		return fontColor_dark;
	}

	public static Color getBackgroundColor_light() {
		return backgroundColor_light;
	}

	public static Color getBackgroundColor_dark() {
		return backgroundColor_dark;
	}

	public static Color getSundayColor() {
		return sundayColor;
	}

	public static Color getSaturdayColor() {
		return saturdayColor;
	}

	public static List<Color> getCheckColors() {
		return checkColors;
	}

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


}
