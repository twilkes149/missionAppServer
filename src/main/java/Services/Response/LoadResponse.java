package Services.Response;

/**
 * A class that holds the information needed for a load result
 * Fields: usersAdded, eventsAdded, personsAdded, error flag and a message
 * @author Tucker
 *
 */
public class LoadResponse extends Response{
	private int users;
	private int events;
	private int persons;
	private boolean error;
	private String message;
	
	public LoadResponse() {
		users = 0;
		events = 0;
		persons = 0;
		error = false;
		message = null;
	}
	
	public LoadResponse(int users, int events, int person) {
		this.events = events;
		this.persons = person;
		this.users = users;
		error = false;
		message = "Successfully added " + users + " users, " + persons + " persons, and " + events + " events to the database.";
	}
	
	public LoadResponse(String message) {
		this.message = message;
		error = true;
	}
	
	public boolean getError() {
		return error;
	}
	
	public void setError() {
		error = true;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getUsers() {
		return users;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public int getEvents() {
		return events;
	}

	public void setEvents(int events) {
		this.events = events;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}
	
	public String toString() {
		if (!error)
			return message;
		else
			return "Error: " + message;
	}

}
