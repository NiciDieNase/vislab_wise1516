package de.hska.iwi.vislab.lab1;

import static org.testng.Assert.assertEquals;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.testng.annotations.Test;

import de.hska.vism.lab1.ref.ws.FibonacciService;
import de.hska.vism.lab1.ref.ws.FibonacciServiceImpl;

public class FibonacciTest {

	@Test
	public void testGetFibonacci() throws Exception {

		String url = "http://localhost:4434/fibonacciservice";

		Endpoint ep = Endpoint.publish(url, new FibonacciServiceImpl());

		Service service = Service.create(new URL(url + "?wsdl"), new QName(
				"http://ws.example.lab1.vislab.iwi.hska.de/",
				"FibonacciServiceImplService"));

		FibonacciService fibonacciService = service
				.getPort(FibonacciService.class);

		int max = 25;
		int result=0;
		
		for (int i = 1; i < max; i++) {
			if (i > 1)
			result = fibonacciService.getFibonacci(i);
			System.out.println(result);
		}

		assertEquals(result, 46368);

		ep.stop();
		
	}

}
