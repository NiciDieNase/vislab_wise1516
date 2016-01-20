package de.hska.vism.lab5.ex.ws;

import javax.jws.WebService;

@WebService(endpointInterface = "de.hska.vism.lab5.ex.ws.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService {

	/**
	 * Web service operation
	 */
	public int getFibonacci(int number) {

		int[] fibonacci;
		fibonacci = new int[number + 1];
		fibonacci[0] = 0;
		fibonacci[1] = 1;
		if (number > 1) {
			for (int i = 2; i <= number; i++) {
				fibonacci[i] = fibonacci[i - 1] + fibonacci[i - 2];
			}
		}
		
		return fibonacci[number];
		
	}

}
