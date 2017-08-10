package Services.Input;

import java.util.ArrayList;
import serverModel.User;

/**
 * A class that contains all of the information needed for the Load Service.
 * Fields: user list, person list, event list
 * @author Tucker
 *
 */
public class LoadInput extends Input{
	private ArrayList<serverModel.User> users;
	private ArrayList<LoadPerson> persons;
	private ArrayList<LoadEvent> events;
	
	
	public LoadInput() {
		users = new ArrayList<serverModel.User>();
		persons = new ArrayList<LoadPerson>();
		events = new ArrayList<LoadEvent>();
	}

	public ArrayList<serverModel.User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<serverModel.User> users) {
		this.users = users;
	}

	public ArrayList<LoadPerson> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<LoadPerson> persons) {
		this.persons = persons;
	}

	public ArrayList<LoadEvent> getEvents() {
		return events;
	}

	public void setEvents(ArrayList<LoadEvent> events) {
		this.events = events;
	}
	
	/**
	 * adds a user to the list
	 * @param user the user to add
	 */
	public void addUser(User user) {
		users.add(user);
	}
	
	/**
	 * adds a person the the list
	 * @param person the person to add
	 */
	public void addPerson(LoadPerson person) {
		persons.add(person);
	}
	
	/**
	 * adds an even to the list
	 * @param event the even to add
	 */
	public void addEvent(LoadEvent event) {
		events.add(event);
	}
	
	/**
	 * Returns the size of the user list
	 * @return size of the user list
	 */
	public int userSize() {
		return users.size();
	}
	
	/**
	 * Returns the size of the person list
	 * @return size of the person list
	 */
	public int personSize() {
		return persons.size();
	}
	
	/**
	 * Returns the size of the event list
	 * @return size of the event list
	 */
	public int eventSize() {
		return events.size();
	}
}
