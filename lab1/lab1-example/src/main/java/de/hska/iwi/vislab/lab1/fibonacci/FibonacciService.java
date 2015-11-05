package de.hska.iwi.vislab.lab1.fibonacci;

import javax.jws.WebService;

/**
 * Created by felix on 05.11.15.
 */
@WebService
public interface FibonacciService {

	public int getFibonacci(int i);
}
