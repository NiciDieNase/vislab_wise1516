package restest;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FibonacciServiceTest {

	private final String urlSrvr = "http://localhost:4434";
	private final String urlClnt = urlSrvr + "/fibonacci";
	private SelectorThread srv;

	@Before
	public void setUp() throws Exception {
		srv = GrizzlyServerFactory.create(urlSrvr);
	}

	@After
	public void tearDown() throws Exception {
		srv.stopEndpoint();
	}

	@Test
	public void testPOST() throws Exception {

		WebResource wrs = Client.create().resource(urlClnt + "?sessionID=42");
		String txt = wrs.accept("text/plain").post(String.class);
		assertEquals(txt, "your session-ID: 42");
	}

	@Test
	public void testPUT() throws Exception {

		WebResource wrs = Client.create().resource(urlClnt + "?sessionID=23");
		String txt = wrs.accept("text/plain").post(String.class);
		assertEquals(txt, "your session-ID: 23");

		wrs = Client.create().resource(urlClnt + "?sessionID=23&a=5&b=3");
		wrs.accept("text/plain").put();

		wrs = Client.create().resource(urlClnt + "?sessionID=23");
		String reply = wrs.accept("text/plain").get(String.class);

		assertEquals("8",reply);

	}

	@Test
	public void testDELETE() throws Exception {

		WebResource wrs = Client.create().resource(urlClnt + "?sessionID=13");
		String txt = wrs.accept("text/plain").post(String.class);
		assertEquals(txt, "your session-ID: 13");
		assertEquals("0",txt);

		wrs = Client.create().resource(urlClnt + "?sessionID=13");
		String reply = wrs.accept("text/plain").get(String.class);

		assertEquals("2",reply);

		txt = wrs.accept("text/plain").delete(String.class);
		assertEquals("removed 13",txt);

		reply = wrs.accept("text/plain").get(String.class);
		assertEquals("0",reply);


	}
}