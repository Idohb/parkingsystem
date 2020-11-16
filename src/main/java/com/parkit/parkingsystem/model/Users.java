package com.parkit.parkingsystem.model;

public class Users {
	private int idUsers;
	private String vehicleRegNumber;
	private int recurring;
	private boolean isInTheParking;

	public int getId() {
		return idUsers;
	}

	public void setId(int id) {
		this.idUsers = id;
	}

	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	public int getRecurring() {
		return recurring;
	}

	public void setRecurring(int recurring) {
		this.recurring = recurring;
	}

	public boolean getIsInTheParking() {
		return isInTheParking;
	}

	public void setIsInTheParking(boolean isInTheParking) {
		this.isInTheParking = isInTheParking;
	}
}
