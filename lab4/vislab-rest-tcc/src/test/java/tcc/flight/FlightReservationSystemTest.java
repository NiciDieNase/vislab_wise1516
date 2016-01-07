package tcc.flight;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import tcc.BookingException;
import tcc.flight.FlightReservationSystem.FlightReservation;

public class FlightReservationSystemTest {
	GregorianCalendar tomorrow = new GregorianCalendar();

	@Before
	public void setUp() throws Exception {
		tomorrow.setTime(new Date());
		tomorrow.add(GregorianCalendar.DAY_OF_YEAR, 1);
	}

	@Test
	public void testExplicitCancel() {
		try {
			int rSize1 = FlightReservationSystem.getInstance().getReservations().size();
			FlightReservation[] r = new FlightReservation[1000];
			// reserve all flights
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				r[i - 1] = FlightReservationSystem.getInstance().createReservation("Christian", "Lufthansa",
						"Karlsruhe", "Berlin", tomorrow.getTimeInMillis());
				assertEquals(FlightReservationSystem.getInstance().getReservation(r[i - 1].id), r[i - 1]);
				assertEquals(FlightReservationSystem.getInstance().getReservations().size(), rSize1 + i);
			}
			int rSize2 = FlightReservationSystem.getInstance().getReservations().size();
			// cancel all flights
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				FlightReservationSystem.getInstance().cancelReservation(r[i - 1].id);
				assertNull(FlightReservationSystem.getInstance().getReservation(r[i - 1].id));
				assertEquals(FlightReservationSystem.getInstance().getReservations().size(), rSize2 - i);
			}
			assertEquals(rSize1, FlightReservationSystem.getInstance().getReservations().size());
		} catch (BookingException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testImplicitCancel() {
		try {
			int rSize1 = FlightReservationSystem.getInstance().getReservations().size();
			FlightReservation[] r = new FlightReservation[1000];
			// reserve all flights
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				r[i - 1] = FlightReservationSystem.getInstance().createReservation("Christian", "Lufthansa",
						"Karlsruhe", "Berlin", tomorrow.getTimeInMillis());
				assertEquals(FlightReservationSystem.getInstance().getReservation(r[i - 1].id), r[i - 1]);
				assertEquals(FlightReservationSystem.getInstance().getReservations().size(), rSize1 + i);
			}
			// wait for reservation timeout
			Thread.sleep(1000 + FlightReservationSystem.LEASE);
			// check that all flights are gone
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				assertNull(FlightReservationSystem.getInstance().getReservation(r[i - 1].id));
			}
			assertEquals(rSize1, FlightReservationSystem.getInstance().getReservations().size());
		} catch (BookingException e) {
			fail(e.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = BookingException.class)
	public void testCommit() throws BookingException {
		try {
			FlightReservation[] r = new FlightReservation[1000];
			// reserve and confirm until complete
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				r[i - 1] = FlightReservationSystem.getInstance().createReservation("Christian", "Lufthansa",
						"Karlsruhe", "Berlin", tomorrow.getTimeInMillis());
				FlightReservationSystem.getInstance().confirmReservation(r[i - 1].id);
			}
			// wait for reservation timeout
			Thread.sleep(1000 + FlightReservationSystem.LEASE);
			// check that no flights are gone
			for (int i = 1; i < FlightReservationSystem.MAXBOOKINGS; i++) {
				assertTrue(FlightReservationSystem.getInstance().getReservation(r[i - 1].id).confirmed);
			}
			// no more reservations should be possible for tomorrow 
			FlightReservationSystem.getInstance().createReservation("Christian", "Lufthansa", "Karlsruhe", "Berlin",
					tomorrow.getTimeInMillis());
		} catch (InterruptedException e) {
			// eat
		}
	}

}
