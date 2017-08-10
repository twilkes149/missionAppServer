package serverModel;

import java.util.ArrayList;

import database.Setup;

/**
 * The object oriented model of a person<br>
 * Fields: firstName, lastName, gender, mother, father, spouse, descendant, id<br>
 * @author Tucker
 *
 */
public class Person {
	public static final String INVALID_ID = "0";
	protected static int count = 1;
	protected String firstName;
	protected String lastName;
	protected Person parent;
	protected String parentID;
	protected char gender;
	protected String id;
	protected String familyID;
	protected ArrayList<Event> eventList;
	
	public Person() {
		eventList = new ArrayList<Event>();
		id = "" + count;//the id of the person
		count++;//increment the id for the next person created
		//Setup.updateCount(Person.count, Setup.PERSON_COLUMN);
	}
	
	public static void setCount(int count) {
		Person.count = count;
	}
	
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getFamilyID() {
		return familyID;
	}

	public void setFamilyID(String familyID) {
		this.familyID = familyID;
	}

	public static int getCount() {
		return Person.count;
	}
	
	public Person(String firstName, String lastName, char gender, Person parent, String familyID) {
		id = "" + count;
		count++;
		this.lastName = lastName;
		this.firstName = firstName;		
		this.familyID = familyID;
		eventList = new ArrayList<Event>();
		//Setup.updateCount(Person.count, Setup.PERSON_COLUMN);
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		String result = firstName + " " + lastName;		
		for (int i = 0; i < eventList.size(); i++)
			result += "\nEvent: " + eventList.get(i);
		return result;
	}
	
	/**
	 * Returns an event belonging to this person
	 * @param index the index of the event in the person'ts arrayList
	 * @return an event
	 */
	public Event getEvent(int index) {
		if (index < eventList.size())
			return eventList.get(index);
		else
			return null;
	}
	
	/**
	 * adds an event to this person
	 * @param event the event to add
	 */
	public void addEvent(Event event) {
		eventList.add(event);
	}
	
	public void setParent(Person parent) {
		this.parent = parent;
	}
	
	public Person getParent() {
		return this.parent;
	}
	
	/**
	 * Adds an event to this person
	 * @param type the type of the event
	 * @param owner the owner of the event
	 * @param desccendant the descendant of the event
	 * @param longitude the longitude of the event
	 * @param latitude the latitude of the event
	 * @param city the city of the event
	 * @param country the country of the event
	 * @param year the year of the event
	 */
	public void addEvent(String type, Person owner, String longitude, String latitude, String city, String country, int year) {
		//eventList.add(new Event(type, owner, longitude, latitude, city, country, year));
	}
	
	/**
	 * Sets the user's event list to list
	 * @param list a list of events that belong to this user
	 */
	public void setEventList(ArrayList<Event> list) {
		if (list != null)
			for (int i = 0; i < list.size(); i++)
				eventList.add(list.get(i));
		else
			eventList.clear();
	}
	
	/**
	 * Returns the list of events for this user
	 * @return list of events
	 */
	public ArrayList<Event> getEvents() {
		return eventList;
	}
	
	/**
	 * Returns how many events this user has
	 * @return number of events
	 */
	public int eventListSize() {
		return eventList.size();
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
