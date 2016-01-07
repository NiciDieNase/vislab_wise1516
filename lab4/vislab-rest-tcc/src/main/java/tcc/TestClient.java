package tcc;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.core.MediaType;

import tcc.flight.FlightReservationDoc;
import tcc.hotel.HotelReservationDoc;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Simple non-transactional client.
 * Can be used to populate the booking services with some requests.
 */
public class TestClient {

	public static void main(String[] args) {
		try {

			Client client = Client.create();

			GregorianCalendar tomorrow = new GregorianCalendar();
			tomorrow.setTime(new Date());
			tomorrow.add(GregorianCalendar.DAY_OF_YEAR, 1);

			// book flight

			WebResource webResourceFlight = client.resource(TestServer.urlSrvr + "/flight");

			FlightReservationDoc docFlight = new FlightReservationDoc();
			docFlight.setName("Christian");
			docFlight.setFrom("Karlsruhe");
			docFlight.setTo("Berlin");
			docFlight.setAirline("airberlin");
			docFlight.setDate(tomorrow.getTimeInMillis());

			ClientResponse responseFlight = webResourceFlight.type(MediaType.APPLICATION_XML)
					.accept(MediaType.APPLICATION_XML).entity(docFlight).post(ClientResponse.class);

			if (responseFlight.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseFlight.getStatus());
			}

			FlightReservationDoc outputFlight = responseFlight.getEntity(FlightReservationDoc.class);
			System.out.println("Output from Server: " + outputFlight);

			// book hotel

			WebResource webResourceHotel = client.resource(TestServer.urlSrvr + "/hotel");

			HotelReservationDoc docHotel = new HotelReservationDoc();
			docHotel.setName("Christian");
			docHotel.setHotel("Interconti");
			docHotel.setDate(tomorrow.getTimeInMillis());

			ClientResponse responseHotel = webResourceHotel.type(MediaType.APPLICATION_XML)
					.accept(MediaType.APPLICATION_XML).entity(docHotel).post(ClientResponse.class);

			if (responseHotel.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : " + responseHotel.getStatus());
			}

			HotelReservationDoc outputHotel = responseHotel.getEntity(HotelReservationDoc.class);
			System.out.println("Output from Server: " + outputHotel);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
