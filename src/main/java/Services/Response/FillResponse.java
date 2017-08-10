package Services.Response;
/**
 * A class that represents the response message of the fill service
 * Inherits from the Response class
 * @author Tucker
 *
 */
public class FillResponse extends Response{
	private int persons;
	private int events;
	private String message;
	private boolean error;
	
	public FillResponse() {
		persons = 0;
		events = 0;
		error = false;
	}
	
	public FillResponse(String message) {
		error = true;
		this.message = message;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
		message = "Successfully added " + persons + " persons and "
				+ events + " events to the database.";
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setFail() {
		error = true;
	}

	public int getEvents() {
		return events;
	}

	public void setEvents(int events) {
		this.events = events;
		message = "Successfully added " + persons + " persons and "
				+ events + "events to the database.";
	}
	
	public String getMessage() {
		if (!error)
			return "Successfully added " + persons + " persons and " + events + " events to the database.";
		else
			return "Error: " + message;
	}
	
	public String toString() {
		return getMessage();
	}

}
