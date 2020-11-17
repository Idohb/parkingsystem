package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	/**
	 * Calculate the price ticket according to the time the vehicle was in the parking
	 * The user get 30 minutes free ticket
	 * Also, he can get a discount if he entering once before.
	 * @param ticket
	 */
	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString() + " In time provided :" + ticket.getInTime().toString());
		}
		double inHour = ticket.getInTime().getTime();
		double outHour = ticket.getOutTime().getTime();
		double duration = (outHour - inHour) / (3600 * 1000); // Convert it in hours

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR:
			ticket.setPrice(duration > Fare.FREE_FIRST_THIRTY_MINUTES ? duration * Fare.CAR_RATE_PER_HOUR : 0);
			break;
		case BIKE:
			ticket.setPrice(duration > Fare.FREE_FIRST_THIRTY_MINUTES ? duration * Fare.BIKE_RATE_PER_HOUR : 0);
			break;
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		if (ticket.isDiscount()) {
			ticket.setPrice(ticket.getPrice() * Fare.DISCOUNT);
		}
	}
}
