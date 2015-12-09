package restest;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by felix on 03.12.15.
 */

@Path("/fibonacci/")
public class FibonacciService {

	final int[] initValues = {1,1};

	Map<String,int[]> sessions = new HashMap<String,int[]>();

	@POST @Produces("text/plain")
	public void createSession(@QueryParam("sessionID") String sessionID){
		newSession(sessionID);
	}

	@GET @Produces("text/plain")
	public int next(@QueryParam("sessionID") String sessionID){
		int[] numbers;
		int newFib;
		try {
			numbers = sessions.get(sessionID);
			newFib = numbers[0] + numbers[1];
			numbers[0] = numbers[1];
			numbers[1] = newFib;
			return newFib;
		} catch (IndexOutOfBoundsException e){
			// TODO session does not exist, return error
		}
		return 0;
	}

	private void newSession(String sessionID) {
		if(sessions.containsKey(sessionID)){
			sessions.remove(sessions.get(sessionID));
		}
		sessions.put(sessionID, initValues);
	}


}
