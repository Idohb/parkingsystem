package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.dao.UserDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.model.Users;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class ParkingService {

	private static final Logger logger = LogManager.getLogger("ParkingService");

	private static FareCalculatorService fareCalculatorService = new FareCalculatorService();

	private InputReaderUtil inputReaderUtil;
	private ParkingSpotDAO parkingSpotDAO;
	private TicketDAO ticketDAO;
	private UserDAO userDAO;

	public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO,
			UserDAO userDAO) {
		this.inputReaderUtil = inputReaderUtil;
		this.parkingSpotDAO = parkingSpotDAO;
		this.ticketDAO = ticketDAO;
		this.userDAO = userDAO;
	}

	public void processIncomingVehicle() throws Exception {
			ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
			if (parkingSpot != null && parkingSpot.getId() > 0) {

				String vehicleRegNumber = getVehichleRegNumber();
				if (userDAO.getUsers(vehicleRegNumber)) {
					throw new Exception("This Vehicle is already in the parking");
				}

				parkingSpot.setAvailable(false);

				parkingSpotDAO.updateParking(parkingSpot);// allot this parking space and mark it's availability as
															// false
				Date inTime = new Date();
				Ticket ticket = new Ticket();
				Users users = new Users();

				// ID, PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME, DISCOUNT)
				// ticket.setId(ticketID);
				if (userDAO.getUserRecurring(vehicleRegNumber) != null) {
					System.out.println(
							"Welcome back! As a recurring user of our parking lot, you'll benefit from a 5% discount.");
				} else {
					users.setRecurring(0);
					ticket.setDiscount(false);
				}
				users.setVehicleRegNumber(vehicleRegNumber);
				users.setIsInTheParking(true);
				ticket.setParkingSpot(parkingSpot);
				ticket.setVehicleRegNumber(vehicleRegNumber);
				ticket.setPrice(0);
				ticket.setInTime(inTime);
				ticket.setOutTime(null);
				ticketDAO.saveTicket(ticket);
				userDAO.saveUser(users);
				System.out.println("Generated Ticket and saved in DB");
				System.out.println("Please park your vehicle in spot number:" + parkingSpot.getId());
				System.out.println("Recorded in-time for vehicle number:" + vehicleRegNumber + " is:" + inTime);
			}

	}

	private String getVehichleRegNumber() throws IllegalArgumentException {
		System.out.println("Please type the vehicle registration number and press enter key");
		return inputReaderUtil.readVehicleRegistrationNumber();
	}

	public ParkingSpot getNextParkingNumberIfAvailable() throws Exception {
		int parkingNumber = 0;
		ParkingSpot parkingSpot = null;
			ParkingType parkingType = getVehichleType();
			parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
			if (parkingNumber > 0) {
				parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
			} else {
				throw new Exception("Error fetching parking number from DB. Parking slots might be full");
			}
		return parkingSpot;
	}

	private ParkingType getVehichleType() {
		System.out.println("Please select vehicle type from menu");
		System.out.println("1 CAR");
		System.out.println("2 BIKE");
		int input = inputReaderUtil.readSelection();
		switch (input) {
		case 1:
			return ParkingType.CAR;
		case 2:
			return ParkingType.BIKE;
		default:
			System.out.println("Incorrect input provided");
			throw new IllegalArgumentException("Entered input is invalid");
		}
	}

	public void processExitingVehicle() throws Exception {
		try {
			String vehicleRegNumber = getVehichleRegNumber();
			Ticket ticket = ticketDAO.getTicket(vehicleRegNumber);
			Users users = userDAO.getUserRecurring(vehicleRegNumber);
			if (ticket == null || ticket.getOutTime() != null) {
				throw new Exception("This Vehicle is not in the parking");
			}
			Date outTime = new Date();
			outTime.setTime(outTime.getTime() + 1000L);
			ticket.setOutTime(outTime);
			fareCalculatorService.calculateFare(ticket);
			if (users.getRecurring() > 0) {
				ticket.setDiscount(true);
			}
			users.setRecurring(users.getRecurring() + 1);
			users.setIsInTheParking(false);
			userDAO.saveUser(users);
			userDAO.updateUser(users);
			if (ticketDAO.updateTicket(ticket) != 0) {
				ParkingSpot parkingSpot = ticket.getParkingSpot();
				parkingSpot.setAvailable(true);
				parkingSpotDAO.updateParking(parkingSpot);
				System.out.println("Please pay the parking fare:" + ticket.getPrice());
				System.out.println(
						"Recorded out-time for vehicle number:" + ticket.getVehicleRegNumber() + " is:" + outTime);
			} else {
				System.out.println("Unable to update ticket information. Error occurred");
			}
		} catch (IllegalArgumentException ie) {
			logger.error("Unable to process exiting vehicle", ie);
		}
	}
}
