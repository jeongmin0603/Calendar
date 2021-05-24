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
	public static Color sundayColor = new Color(237, 76, 64);
	public static Color saturdayColor = new Color(54, 57, 255);
	
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
	
}
