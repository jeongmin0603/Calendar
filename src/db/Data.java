package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {
	public static Statement stmt;
	public static Connection con;
	
	public static ResultSet getResultSet(String sql) {
		try {
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void setDb(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isDbInit() {
		boolean exists = true;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root/&serverTimezome=UTC", "user", "1234");
			stmt = con.createStatement();
			
			stmt.execute("use calendar");
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Information_schema.tables WHERE table_schema = 'calendar' and table_name = 'schedule' or table_name = 'color'");
			
			if (rs.next()) {				
				if(rs.getInt("count(*)") != 2) {
					exists = false;
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}
	
	public static void createDb() {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root/&serverTimezome=UTC", "root", "1234");
			Statement stmt = con.createStatement();
			
			stmt.execute("drop database if exists Calendar");
			stmt.execute("create database if not exists Calendar");
			stmt.execute("create user if not exists 'user'@'%' identified by '1234'");
			stmt.execute("grant insert, select, delete, update on Calendar.* to 'user'@'%'");

			stmt.execute("use Calendar");
			stmt.execute("CREATE TABLE IF NOT EXISTS `color` (\r\n" + 
					"  `c_no` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `r` INT NULL,\r\n" + 
					"  `g` INT NULL,\r\n" + 
					"  `b` INT NULL,\r\n" + 
					"  PRIMARY KEY (`c_no`))");
			stmt.execute("CREATE TABLE IF NOT EXISTS `schedule` (\r\n" + 
					"  `date` DATE NULL,\r\n" + 
					"  `text` VARCHAR(45) NOT NULL,\r\n" + 
					"  `c_no` INT NULL)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
