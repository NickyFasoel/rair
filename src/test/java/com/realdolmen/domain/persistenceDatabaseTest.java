package com.realdolmen.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.course.utilities.persistence.JpaPersistenceTest;

public class persistenceDatabaseTest extends JpaPersistenceTest{
	
	@Test
	public void initPersistTest() {
		
		// Make EntityManager with function from JpaPersistenceTest
		EntityManager em = entityManager();
		
		//Make Address a, persist for Id and check if Id exists
		Address a = new Address("DummyStraat", 42, 9999, "Belgium");
		em.persist(a);
		assertNotNull(a.getId());
		
		//Make Customer c, persist, check Id for null and check if link with Address a is there
		Customer c = new Customer(a, "jon.snow@gmail.com", 
				"Jon", "Snow", "iknownothing", "KingOfTheNorth");
		em.persist(c);
		assertNotNull(c.getId());
		assertEquals("DummyStraat", em.find(Customer.class, c.getId()).getAddress().getStreet());
		
		
		//Make Partner p, persist, check Id for null and check if a String field checks out
		Partner p = new Partner("BryanAir", "fName", "lName", "BryanAir", "pass");
		em.persist(p);
		assertNotNull(p.getId());
		assertEquals("BryanAir", em.find(Partner.class, p.getId()).getName());
		
		
		// Make two Location objects l1 and l2, persist both and check Ids for null
		Location l1 = new Location("Airport1", "Belgium", "111", GlobalRegion.WesternEurope);
		Location l2 = new Location("Airport2", "Italy", "222", GlobalRegion.SouthernEurope);
		em.persist(l1);
		assertNotNull(l1.getId());
		em.persist(l2);
		assertNotNull(l2.getId());

		// Make Fligt f with l1 and l2, persist and check Id for null
		Flight f = new Flight(p, new ArrayList<BookingOfFlight>(), l1, l2,
				Calendar.getInstance(), Duration.ofMinutes(120));
		em.persist(f);
		assertNotNull(f.getId());
		
		// Find persistedFlight and check if l1 and l2 are linked 
		// 	through globalRegion, this way the enum is also tested
		Flight persistedFlight = em.find(Flight.class, f.getId());
		assertEquals("WesternEurope", persistedFlight.getDepartureLocation().getGlobalRegion().toString());
		assertEquals("SouthernEurope", persistedFlight.getDestinationLocation().getGlobalRegion().toString());
		
		// Check departure date stuff:
		//	- check if depature date is before current date
		//	- check if getFlightDurationMethod gives the correct time
		//	- check the print versions...
		//	- check if the time between landingtime (calculated by getLandingTime()) 
		//		and dateOfDeparture minus the flight duration equals zero
		assertTrue(Calendar.getInstance().after(persistedFlight.getDateOfDeparture()));
		assertEquals(120, persistedFlight.getFlightDuration().toMinutes());
		System.err.println("Date of departure: " + f.getDateOfDeparture().getTime().toString());
		System.err.println("Flight duration " + f.getFlightDuration().toMinutes());
		System.err.println("Date of landing: " + f.getLandingTime().getTime().toString());
		assertEquals(0, (int)((f.getLandingTime().getTime().getTime()/60000) 
				- (f.getDateOfDeparture().getTime().getTime()/60000))
				- f.getFlightDuration().toMinutes());
		
		// Make Seat s, persist and check Id for Null
		Seat s = new Seat(SeatType.Business, 50.0);
		em.persist(s);
		assertNotNull(s.getId());
		
		// Add Seat s to Flight f and check if it checks out
		//	=> enum SeatType tested
		f.addSeat(s);
//		em.merge(f); // TODO: WAT DOET MERGE juist want hier is het niet nodig?!
		assertEquals("Business", em.find(Flight.class, f.getId()).getSeatList().get(0).getType().toString());
		
		// Partner with Flight linkt test
		p.addFlight(f);
		assertEquals("BryanAir", persistedFlight.getPartner().getUserName());
		
		// Make Booking b, persist and check Id for null
		Booking b = new Booking("Payed", c, Calendar.getInstance());
		em.persist(b);
		assertNotNull(b.getId());
		
		// Make BookingOfFLight bf, persist, check Id for null
		//		and add it to the Booking b
		BookingOfFlight bf = new BookingOfFlight(999.99, f, b);
		em.persist(bf);
		assertNotNull(bf.getId());
		b.addBookingOfFlight(bf);
		em.merge(b);
		
		// Check if the Booking and Flight link is good
		// Check if the Booking and Customer link is good
		assertEquals(f.getId(), 
				em.find(Booking.class, b.getId()).getBookingOfFlightList().get(0).getFlight().getId());
		assertEquals("Jon", 
				em.find(Booking.class, b.getId()).getCustomer().getFirstName());
		
	}
}