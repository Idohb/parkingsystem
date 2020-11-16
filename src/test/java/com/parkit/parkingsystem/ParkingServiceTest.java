package com.parkit.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.dao.UserDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.model.Users;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
	private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

	private static ParkingService parkingService;

	@Mock
	private static InputReaderUtil inputReaderUtil;
	@Spy
	private static ParkingSpotDAO parkingSpotDAO;
	@Spy
	private static TicketDAO ticketDAO;
	@Mock
	private static DataBasePrepareService dataBasePrepareService;
	@Spy
	private static UserDAO userDAO;



	@AfterAll
	private static void tearDown() {
		
		parkingSpotDAO = new ParkingSpotDAO();
		parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
		ticketDAO = new TicketDAO();
		ticketDAO.dataBaseConfig = dataBaseTestConfig;
		userDAO = new UserDAO();
		userDAO.dataBaseConfig = dataBaseTestConfig;
		dataBasePrepareService = new DataBasePrepareService();

	}
	
	@BeforeEach
	private void setUpPerTest() throws Exception {
		dataBasePrepareService.clearDataBaseEntries();
	}

	@Test
	public void processIncommingVehicleTest()  throws Exception {

		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
		parkingService.processIncomingVehicle();
		ParkingSpot parkingSpot = parkingService.getNextParkingNumberIfAvailable();
		assertTrue(parkingSpot.isAvailable());
	}

	
	@Test
	public void testCannotIncomingACarTwiceInARow() throws Exception {
		when(inputReaderUtil.readSelection()).thenReturn(1);
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
		assertThrows(Exception.class, () -> {
			processIncommingVehicleTest();
			parkingService.processIncomingVehicle();
		});
	}
	
	@Test
	public void testOtherVehicleType()  throws Exception {

		when(inputReaderUtil.readSelection()).thenReturn(3);
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
		assertThrows(IllegalArgumentException.class, () -> {
			parkingService.getNextParkingNumberIfAvailable();
		});
	}
	

	@Test
	public void processExitingVehicleTest() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
		ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
		Ticket ticket = new Ticket();
		ticket.setInTime(new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
		ticket.setParkingSpot(parkingSpot);
		ticket.setVehicleRegNumber("ABCDEF");
		when(ticketDAO.getTicket(anyString())).thenReturn(ticket);
		when(ticketDAO.updateTicket(any(Ticket.class))).thenReturn(1);

		Users users = new Users();
		users.setRecurring(1);
		users.setVehicleRegNumber("ABCDEF");
		when(userDAO.getUserRecurring(anyString())).thenReturn(users);
		when(userDAO.updateUser(any(Users.class))).thenReturn(1);
		parkingService.processExitingVehicle();
		verify(userDAO, Mockito.times(1)).updateUser(any(Users.class));
		verify(ticketDAO, Mockito.times(1)).updateTicket(any(Ticket.class));
		verify(parkingSpotDAO, Mockito.times(1)).updateParking(any(ParkingSpot.class));
	}

	@Test
	public void testCannotExitingACarTwiceInARow() throws Exception {
		when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
		parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
		assertThrows(Exception.class, () -> {
			processExitingVehicleTest();
			parkingService.processExitingVehicle();
		});
	}



}
