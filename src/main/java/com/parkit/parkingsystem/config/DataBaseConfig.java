package com.parkit.parkingsystem.config;

import java.io.BufferedReader;
import java.io.File; // Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DataBaseConfig {
	private static final Logger logger = LogManager.getLogger("DataBaseConfig");
	private static final String FILENAME = "symptoms.txt";

	protected String readFile() throws IOException {
		String pass = " ";
		try {
			pass = FILENAME;
			InputStream inputStream = new FileInputStream("filename.txt");
			Reader fileReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader reader = new BufferedReader(fileReader);
			pass = reader.readLine();
			reader.close();
			if (pass.isEmpty()) {
				throw new IllegalStateException("The symtomps.txt file is empty, so result.out is not written");
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return pass;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
		logger.info("Create DB connection");
		Class.forName("com.mysql.cj.jdbc.Driver");
		String passpass = readFile();
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/prod", "root", passpass);
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
