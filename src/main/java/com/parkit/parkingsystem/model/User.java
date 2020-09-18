package com.parkit.parkingsystem.model;

public class User {
	private int idUser;
	private String vehicleRegNumber;
	private int recurring;

	public int getId() {
		return idUser;
	}

	public void setId(int id) {
		this.idUser = id;
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
}
