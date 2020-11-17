package com.parkit.parkingsystem.model;

public class Users {
	private int idUsers;
	private String vehicleRegNumber;
	private int recurring;
	private boolean isInTheParking;

	
	/**
	 * get the Id of hte user
	 * @return int
	 */
	public int getId() {
		return idUsers;
	}

	/**
	 * set the Id of the user
	 * @param id
	 */
	public void setId(int id) {
		this.idUsers = id;
	}

	/**
	 * get the vehicle reg number of the user
	 * @return String
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	/**
	 * set the vehicle reg number
	 * @param vehicleRegNumber
	 */
	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	
	/**
	 * get the number of recurring the user entering in the parking
	 * @return int
	 */
	public int getRecurring() {
		return recurring;
	}

	/**
	 * set the recurring of the user
	 * @param recurring
	 */
	public void setRecurring(int recurring) {
		this.recurring = recurring;
	}

	/**
	 * check if the vehicle of the user is in the parking
	 * @return boolean
	 */
	public boolean getIsInTheParking() {
		return isInTheParking;
	}

	/**
	 * set the value isInTheParking to inform that the user is in the parking
	 * @param isInTheParking
	 */
	public void setIsInTheParking(boolean isInTheParking) {
		this.isInTheParking = isInTheParking;
	}
}
