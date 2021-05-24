package db;


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConnection;

public class SQLite {
	public static Statement stmt;
	public static Connection con;
	
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			SQLiteConfig config = new SQLiteConfig();
			config.enableLoadExtension(true);
					
			con = DriverManager.getConnection("jdbc:sqlite:db\\schedule.db", config.toProperties());
			stmt = con.createStatement();		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void setTable() {
		ResultSet rs = getResultSet("select count(*) from sqlite_master where name = 'schedule' or name = 'color'");
		
		try {
			if(rs.getInt("count(*)") != 2) {
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
				
				stmt.execute("CREATE TABLE if not exists \"schedule\" (\r\n" + 
						"	\"s_no\"	INTEGER NOT NULL,\r\n" + 
						"	\"date\"	TEXT NOT NULL,\r\n" + 
						"	\"text\"	TEXT NOT NULL,\r\n" + 
						"	\"c_no\"	INTEGER,\r\n" + 
						"	PRIMARY KEY(\"s_no\" AUTOINCREMENT)\r\n" + 
						");");
				stmt.execute("CREATE TABLE if not exists \"color\" (\r\n" + 
						"	\"c_no\"	INTEGER NOT NULL,\r\n" + 
						"	\"r\"	INTEGER NOT NULL,\r\n" + 
						"	\"g\"	INTEGER NOT NULL,\r\n" + 
						"	\"b\"	INTEGER NOT NULL,\r\n" + 
						"	PRIMARY KEY(\"c_no\" AUTOINCREMENT)\r\n" + 
						");");
			
				PreparedStatement pst = con.prepareStatement("insert into color values(NULL, ?, ?, ?)");
				for(int i = 0; i < colors.length; i++) {
					Color color = colors[i];
					pst.setInt(1, color.getRed());
					pst.setInt(2, color.getGreen());
					pst.setInt(3, color.getBlue());
					pst.executeUpdate();
				}
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}  		
	}
	public static void setDb(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertSchedule(String date, String text, String c_no) {
		try {
			PreparedStatement pst = con.prepareStatement("insert into schedule values(NULL, ?, ?, ?)");
			pst.setString(1, date);
			pst.setString(2, text);
			pst.setString(3, c_no);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void insertSchedule(String date, String text) {
		try {
			PreparedStatement pst = con.prepareStatement("insert into schedule values(NULL, ?, ?, NULL)");
			pst.setString(1, date);
			pst.setString(2, text);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ResultSet getResultSet(String sql) {
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			return null;
		}
		
	}
	
}
