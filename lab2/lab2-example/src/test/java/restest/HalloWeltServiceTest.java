package restest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly.GrizzlyServerFactory;

public class HalloWeltServiceTest {

	private final String urlSrvr = "http://localhost:4434";
	private final String urlClnt = urlSrvr + "/helloworld?name=MeinName";
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
	public void testRESTfulWebService() throws Exception {

		WebResource wrs = Client.create().resource(urlClnt);

		String txt = wrs.accept("text/plain").get(String.class);
		String htm = wrs.accept("text/html").get(String.class);

		assertEquals("Hallo MeinName", txt);
		assertEquals(
				"<html><title>HelloWorld</title><body><h2>Html: Hallo MeinName</h2></body></html>",
				htm);
	}
}