package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

	public void calculateFare(Ticket ticket) {
		if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
			throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
		}
		double inHour = ticket.getInTime().getTime();
		double outHour = ticket.getOutTime().getTime();

		// TODO: Some tests are failing here. Need to check if this logic is correct
		double duration = (outHour - inHour) / (3600 * 1000); // Convert it in hours

		switch (ticket.getParkingSpot().getParkingType()) {
		case CAR: {
			ticket.setPrice(duration > Fare.FREE_FIRST_THIRTY_MINUTES ? duration * Fare.CAR_RATE_PER_HOUR : 0);
			break;
		}
		case BIKE: {
			ticket.setPrice(duration > Fare.FREE_FIRST_THIRTY_MINUTES ? duration * Fare.BIKE_RATE_PER_HOUR : 0);
			break;
		}
		default:
			throw new IllegalArgumentException("Unkown Parking Type");
		}
		if (ticket.isDiscount())
			ticket.setPrice(ticket.getPrice() * Fare.DISCOUNT);
	}
}