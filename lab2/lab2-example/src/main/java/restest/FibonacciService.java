package restest;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by felix on 03.12.15.
 */

@Path("/fibonacci")
public class FibonacciService {

	private Map<String,int[]> sessions = new HashMap<String,int[]>();

	@POST @Produces("text/plain")
	public String createSession(@QueryParam("sessionID") String sessionID){
		newSession(sessionID);
		return "your session-ID: " + sessionID;
	}

	@GET @Produces("text/plain")
	public int next(@QueryParam("sessionID") String sessionID){
		int[] numbers;
		int newFib;
		try {
			numbers = sessions.get(sessionID);
			System.out.println(sessionID + " " + numbers);
			newFib = numbers[0] + numbers[1];
			numbers[0] = numbers[1];
			numbers[1] = newFib;
			return newFib;
		} catch (IndexOutOfBoundsException e){
			// TODO session does not exist, return error
		} catch (NullPointerException e){
			e.printStackTrace();
		}
		return 0;
	}

	@DELETE
	public void deleteSession(@QueryParam("sessionID") String sessionID){
		if(sessions.containsKey(sessionID)){
			sessions.remove(sessions.get(sessionID));
			System.out.println("replacing existing session");
		}
	}

	private void newSession(String sessionID) {
		deleteSession(sessionID);
		sessions.put(sessionID, new int[]{1, 1});
		System.out.println("new session created: " + sessionID + " " + sessions.get(sessionID)[0] + " " + sessions.get(sessionID)[1]);
	}


}
