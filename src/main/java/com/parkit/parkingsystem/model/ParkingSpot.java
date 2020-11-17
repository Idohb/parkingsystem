package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;

public class ParkingSpot {
	private int number;
	private ParkingType parkingType;
	private boolean isAvailable;

	/**
	 * Constructor
	 * @param number
	 * @param parkingType
	 * @param isAvailable
	 */
	public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
		setId(number);
		setParkingType(parkingType);
		this.isAvailable = isAvailable;
	}
	
	/**
	 * get Id of parking
	 * @return int
	 */
	public int getId() {
		return number;
	}

	/**
	 * set Id of Parking
	 * @param number
	 */
	protected void setId(int number) {
		this.number = number;
	}
	
	/**
	 * return the current type of the vehicle
	 * @return ParkingType
	 */
	public ParkingType getParkingType() {
		return parkingType;
	}

	/**
	 * set the vehicle type
	 * @param parkingType
	 */
	protected void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}

	/**
	 * check if there is a slot in the parking
	 * @return
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/**
	 * set the availability of a slot in the parking
	 * @param available
	 */
	public void setAvailable(boolean available) {
		isAvailable = available;
	}
}
