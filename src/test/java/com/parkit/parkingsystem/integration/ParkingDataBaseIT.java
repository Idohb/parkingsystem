package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.dao.UserDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static UserDAO userDAO;
    
	private Ticket ticket;

    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception{
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
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown(){

    }

    @Test
    public void testParkingACar() throws Exception{
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);
        parkingService.processIncomingVehicle();

        //WHEN
		ticket = ticketDAO.getTicket("ABCDEF"); // Search ticket in DB from last input Reader
		
		//THEN
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
        assertEquals(ParkingType.CAR, ticket.getParkingSpot().getParkingType());
        assertEquals(2, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

    @Test
    public void testParkingLotExit() throws Exception{
        //TODO: check that the fare generated and out time are populated correctly in the database
    	// GIVEN
		testParkingACar();
		ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO, userDAO);

		Date inTime = new Date();
		Date outTime = new Date();
		inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));
		ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
		ticket.setInTime(inTime);
		ticket.setOutTime(outTime);		
		ticketDAO.saveTicket(ticket);

		// WHEN
		parkingService.processExitingVehicle();


		// THEN
		ticket = ticketDAO.getTicket(inputReaderUtil.readVehicleRegistrationNumber());
		double inHour = ticket.getInTime().getTime();
		double outHour = ticket.getOutTime().getTime();
		double duration = (outHour - inHour) / (3600 * 1000); // Convert it in hours
		assertEquals(duration * Fare.CAR_RATE_PER_HOUR, ticket.getPrice(), 3);
        assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        
        assertTrue(ticket.getOutTime() != null);
        
    }

}
