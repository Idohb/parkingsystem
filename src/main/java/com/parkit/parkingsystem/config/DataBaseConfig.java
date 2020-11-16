package com.parkit.parkingsystem.config;

import java.io.File; // Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

public class DataBaseConfig {
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");

	protected String readPassword() throws IOException {
		String pass = " ";
		Properties p = null;
		InputStream is = null;
		try {
			String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator ;
			p = new Properties();
			is = new FileInputStream(filePath + "log4j.properties");
			p.load(is);
			pass = p.getProperty("pass");
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred. The file not found");
			e.printStackTrace();
		} finally {
			if (p != null && is != null) {
				p.clear();
				is.close();
			}
		}
		return pass;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", "root", readPassword());
	}

	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("Closing DB connection");
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	public void closePreparedStatement(PreparedStatement ps) {
		if (ps != null) {
			try {
				ps.close();
				logger.info("Closing Prepared Statement");
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				logger.info("Closing Result Set");
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}
}
