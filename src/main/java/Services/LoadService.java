package Services;
import java.util.ArrayList;

import Services.Input.*;
import Services.Response.*;
import database.DatabaseDriver;
import database.EventDAO;
import database.PersonDAO;
import database.UserDAO;
import serverModel.Event;
import serverModel.Person;
import serverModel.User;
/**
 * A class that performs the load service. Clears all data from the database and loads in information supplied 
 * by the client.
 * @author Tucker
 *
 */
public class LoadService implements Service{
	private ArrayList<User> users;
	private ArrayList<LoadEvent> events;
	private ArrayList<LoadPerson> persons;
	/**
	 * Calls the clear service method, then takes the list of data from the input and creates users and persons for each user
	 * populates the database with newly created users, then it adds the events to the database
	 * @param data the list of users, persons, and events to replace the database
	 * @return returns a message body containing how many users, persons, and events were added to database
	 */
	public Response fillService(Input data) {
		ClearService clearDriver = new ClearService();
		UserDAO userDriver = new UserDAO(DatabaseDriver.databaseName);
		EventDAO eventDriver = new EventDAO(DatabaseDriver.databaseName);
		PersonDAO personDriver = new PersonDAO(DatabaseDriver.databaseName);
		
		LoadInput input = null;
		if (data instanceof LoadInput)
			input = (LoadInput) data;
		else //bad input
			return new LoadResponse("Bad input");
		
		//clearing the database
		System.out.print("Clear: " + clearDriver.fillService(null));
		
		
		users = input.getUsers();
		events = input.getEvents();
		persons = input.getPersons();
		
		//TODO might need to check to see fields are correct
		try {
			//saving the users
			for (int i = 0; i < users.size(); i++) {
				userDriver.saveUser(users.get(i));
			}
			
			//saving persons
			for (int i = 0; i < persons.size(); i++) {
				setPersonDescendant(i);
				personDriver.savePerson(persons.get(i));
			}
			//saving events
			for (int i = 0; i < events.size(); i++) {
				setEventDescendant(i);
				eventDriver.saveEvent(events.get(i));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return new LoadResponse("Error: " + e.getMessage());
		}
		System.out.println("persons: " + persons.size());
		return new LoadResponse(users.size(), events.size(), persons.size());
	}
	
	/**
	 * Sets the descendant of a person at index
	 * @param index the index of the person in the event list
	 */
	public void setPersonDescendant(int index) {
		//gets the id of the at index
		String id = findUser(persons.get(index).getDescendant());
		persons.get(index).setDescendant("" + id);
	}
	
	/**
	 * Sets event an event's descendant
	 * @param index the index of the event
	 */
	public void setEventDescendant(int index) {
		//gets the id of the user at index
		String id = findUser(events.get(index).getDescendant());
		events.get(index).setDescendant("" + id);		
	}
	
	/**
	 * Finds a user form the loaded list and returns its ID
	 * @param name the first name of the user
	 * @return the id of the user
	 */
	public String findUser(String name) {
		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getFirstName().equalsIgnoreCase(name))
				return users.get(i).getUserId();
		return "0";
	}
}
