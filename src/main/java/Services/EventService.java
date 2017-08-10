package Services;
import java.util.ArrayList;

import Services.Input.*;
import Services.Response.*;
import database.EventDAO;

/**
 * A class that implements the EventService
 * @author Tucker
 *
 */
public class EventService implements Service {
	EventDAO eventDriver = new EventDAO("familyHistory");
	
	/** If an event id is supplied, this returns information about the event, otherwise it returns all events that belong to the user with the authToken
	 * @param data the data for a user to user the eventService: optional event id, and an authToken
	 * @return 
	 */
	public Response fillService(Input data) {
		EventInput input = null;
		if (data instanceof EventInput)
			input = (EventInput) data;
		if (input == null)
			return new EventResponse("Invalid input");
		System.out.println("Getting events for: " + input.getUserName());
		ArrayList<EResponse> list = eventDriver.getEventsOfUserName(input.getUserName());
		System.out.println("result size: " + list.size());
		System.out.println("Event id: " + input.getEventID());
		//we only need to get one event
		if (input.getEventID() != null) {
			for (int i = 0; i < list.size(); i++)
				if (!list.get(i).getId().equals(input.getEventID())) {
					list.remove(i);
					i--;
				}
		}
		EventResponse result = new EventResponse();
		if (list.size() <= 0)
			result = new EventResponse("Error occured in getting events, there were  no events for this user");
		
		
		result.setList(list);
		
		return result;
	}
	
	/**
	 * a test method
	 * @param args
	 */
	public static void main (String[] args) {
		EventService service = new EventService();
		
		EventResponse result = (EventResponse) service.fillService(new EventInput("twilkes", "1"));
		System.out.println(result);
	}

}
