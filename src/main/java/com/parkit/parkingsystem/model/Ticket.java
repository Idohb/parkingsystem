package com.parkit.parkingsystem.model;

import java.util.Date;

public class Ticket {
	private int id;
	private ParkingSpot parkingSpot;
	private String vehicleRegNumber;
	private double price;
	private Date inTime;
	private Date outTime;
	private boolean discount;

	
	/**
	 * get the Id of a ticket
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * set the Id of a Ticket
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get the parking spot relative to the current Ticket
	 * @return
	 */
	public ParkingSpot getParkingSpot() {
		return parkingSpot;
	}

	/**
 	 * set the parking spot relative to the current Ticket
	 * @param parkingSpot
	 */
	public void setParkingSpot(ParkingSpot parkingSpot) {
		this.parkingSpot = parkingSpot;
	}

	
	/**
	 * get the vehicle Reg Number
	 * @return
	 */
	public String getVehicleRegNumber() {
		return vehicleRegNumber;
	}

	/**
	 * set the vehicle Reg Number
	 * @param vehicleRegNumber
	 */
	public void setVehicleRegNumber(String vehicleRegNumber) {
		this.vehicleRegNumber = vehicleRegNumber;
	}

	/**
	 * get the price when the vehicle exiting
	 * @return double
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * set the price of a ticket after Fare calculation 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * get the time when the vehicle is entering in the parking
	 * @return Date
	 */
	public Date getInTime() {
		if (inTime == null) {
			return null;
		} else {
			return new Date(inTime.getTime());
		}
	}

	/**
	 * set the time when the vehicle is entering in the parking
	 * @param inTime
	 */
	public void setInTime(Date inTime) {
		if (inTime != null) {
			this.inTime = new Date(inTime.getTime());
		}
	}

	/**
	 * get the time when the vehicle is exiting in the parking
	 * @return Date
	 */
	public Date getOutTime() {
		if (outTime == null) {
			return null;
		} else {
			return new Date(outTime.getTime());
		}
	}

	/**
	 * set the time when the vehicle is exiting in the parking
	 * @param outTime
	 */
	public void setOutTime(Date outTime) {
		if (outTime != null) {
			this.outTime = new Date(outTime.getTime());
		}
	}

	
	/**
	 * check if the ticket benefit a discount into Fare calculation 
	 * @return boolean
	 */
	public boolean isDiscount() {
		return discount;
	}

	/**
	 * set the discount value of the ticket
	 * @param discount
	 */
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
}
