package db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	public static Map<String, List<Schedule>> schedule = new HashMap<>();
	public static String font = "HY헤드라인M";
	
	public static String getFont() {
		return font;
	}
}