package db;

import java.awt.Color;

public class Schedule {
	String text;
	Color color = null;
	public Schedule(String text) {
		this.text = text;
	}
	public Schedule(String text, Color color) {
		this.text = text;
		this.color = color;
	}
	public String getText() {
		return text;
	}
	public Color getColor() {
		return color;
	}
}
