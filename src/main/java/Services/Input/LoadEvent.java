package Services.Input;

/**
 * A class that contains a string representation of an event
 * Only used internally
 * @author Tucker
 *
 */
public class LoadEvent {
	private String descendant;
	private String eventID;
	private String personID;
	private String latitude;
	private String longitude;
	private String country;
	private String city;
	private int year;
	private String eventType;
	
	public LoadEvent(String descendant, String eventId, String personID, String latitude, String longitude,
			String country, String city, int year, String type) {
		this.descendant = descendant;
		this.eventID = eventId;
		this.personID = personID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.country = country;
		this.city = city;
		this.year = year;
		this.eventType = type;
	}
	
	public String getDescendant() {
		return descendant;
	}
	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}
	public String getEventId() {
		return eventID;
	}
	public void setEventId(String eventId) {
		this.eventID = eventId;
	}
	public String getPersonID() {
		return personID;
	}
	public void setPersonID(String personID) {
		this.personID = personID;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getType() {
		return eventType;
	}
	public void setType(String type) {
		this.eventType = type;
	}
	
	
}
