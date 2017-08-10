package serverModel;

import database.Setup;

/**
 * The object oriented model for an event<br>
 * Fields: id, owner, descendant, location, type, year<br>
 * @author Tucker
 *
 */
public class Event {
	protected static int count;//keeps track of how many events have been created
	private String id;
	private Person owner;
	private User descendant;
	private Location location;
	private String type;
	private int year;
	
	public Event() {
		owner = null;
		descendant = null;
		location = null;
		type = null;
		year = 0;
		id = "" + Event.count;
		Event.count++;
		//Setup.updateCount(Event.count, Setup.EVENT_COLUMN);
	}
	
	public Event(String type, Person owner, User descendant, String longitude, String latitude, String city, String country, int year) {
		this.type = type;
		this.owner = owner;
		this.descendant = descendant;
		this.year = year;
		id = "" + Event.count;
		Event.count++;
		location = new Location(id, longitude, latitude, city, country);
		//Setup.updateCount(Event.count, Setup.EVENT_COLUMN);
	}
	
	public static void setCount(int count) {
		Event.count = count;
	}
	
	public static int getCount() {
		return Event.count;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		String result = "Type: " + type;
		result += "\nYear: " + year;
		if (owner != null)
			result += "\nOwner: " + owner.getFirstName() + " " + owner.getLastName();
		if (location != null)
			result += "\n Location: " + location.toString() + "\n";
		return result;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public User getDescendant() {
		return descendant;
	}
	public void setDescendant(User descendant) {
		this.descendant = descendant;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
}
