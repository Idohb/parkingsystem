package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.model.Users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

	private static final Logger logger = LogManager.getLogger("UserDAO");

	public DataBaseConfig dataBaseConfig = new DataBaseConfig();

	public boolean saveUser(Users users) {
		Connection con = null;
        boolean result = false;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_USER);
//			PreparedStatement ps = con.prepareStatement(DBConstants.SAVE_TICKET);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			//ps.setInt(1,user.getId());
			//ps.setInt(1, user.getParkingSpot().getId());
			ps.setInt(1, users.getRecurring());
			ps.setString(2, users.getVehicleRegNumber());
            result = ps.execute();
            dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return result;
	}

	public Users getUserRecurring(String vehicleRegNumber) {
		Connection con = null;
		Users users = null;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.GET_USER);
			// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME)
			ps.setString(1, vehicleRegNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				users = new Users();
				//users.setId(rs.getInt(1));
				users.setRecurring(rs.getInt(1));
				users.setVehicleRegNumber(vehicleRegNumber);
			}
			dataBaseConfig.closeResultSet(rs);
			dataBaseConfig.closePreparedStatement(ps);
		} catch (Exception ex) {
			logger.error("Error fetching next available slot", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
			return users;
		}
	}

	public int updateUser(Users users) {
		Connection con = null;
		int result = -1;
		try {
			con = dataBaseConfig.getConnection();
			PreparedStatement ps = con.prepareStatement(DBConstants.UPDATE_USER);
			ps.setDouble(1, users.getRecurring());
			ps.setString(2, users.getVehicleRegNumber());
			result = ps.executeUpdate();
            dataBaseConfig.closePreparedStatement(ps);

		} catch (Exception ex) {
			logger.error("Error saving ticket info", ex);
		} finally {
			dataBaseConfig.closeConnection(con);
		}
		return result;
	}
}
