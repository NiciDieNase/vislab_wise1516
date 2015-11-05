package de.hska.iwi.vislab.lab1.fibonacci;

import javax.jws.WebService;

/**
 * Created by felix on 05.11.15.
 */
@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.fibonacci.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService{
	@Override
	public int getFibonacci(int i) {
		if( i <= 0 ){
			return 0;
		} else if(i == 1 || i == 2){
			return 1;
		} else {
			return getFibonacci(i-1) + getFibonacci(i-2);
		}
	}
}
