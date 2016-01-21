package restest;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by felix on 03.12.15.
 */

@Path("/fibonacci")
public class FibonacciService {

	private static Map<String,int[]> sessions = new HashMap<String,int[]>();

	@POST @Produces("text/plain")
	public String createSession(@QueryParam("sessionID") String sessionID){
		newSession(sessionID);
		return "your session-ID: " + sessionID;
	}

	@PUT
	public void updateSession(
			@QueryParam("a") int a,
			@QueryParam("b") int b,
			@QueryParam("sessionID") String sessionID){
		if(a<b){
			sessions.put(sessionID,new int[]{a,b});
		} else {
			sessions.put(sessionID,new int[]{b,a});
		}
	}

	@GET @Produces("text/plain")
	public String next(@QueryParam("sessionID") String sessionID){
		int[] numbers;
		int newFib;
		try {
			numbers = sessions.get(sessionID);
			newFib = numbers[0] + numbers[1];
			numbers[0] = numbers[1];
			numbers[1] = newFib;
			return Integer.toString(newFib);
		} catch (IndexOutOfBoundsException e){
			// session does not exist
			return "Session does not exist";
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return "0";
	}

	@DELETE
	public String deleteSession(@QueryParam("sessionID") String sessionID){
		sessions.remove("sessionID");
		return "removed " + sessionID;
	}

	private void newSession(String sessionID) {
		deleteSession(sessionID);
		sessions.put(sessionID, new int[]{1, 1});
	}


}
