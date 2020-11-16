package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpot {
	private int number;
	private ParkingType parkingType;
	private boolean isAvailable;

	public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
		setId(number);
		setParkingType(parkingType);
		this.isAvailable = isAvailable;
	}

	public int getId() {
		return number;
	}

	protected void setId(int number) {
		this.number = number;
	}

	public ParkingType getParkingType() {
		return parkingType;
	}

	protected void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}
}
