package serverModel;
/**
 * The object oriented model for a location<br>
 * Fields: id, longitude, latitude, country, city, year<br>
 * @author Tucker
 *
 */
public class Location {
	//Note: make longitude and latitude a String for json and gson
	protected static int count=0;//keeps track of how many locations have been created
	private String eventID;
	private String longitude;
	private String latitude;
	private String country;
	private String city;
	
	public Location(String id) {
		eventID = id;
		longitude = null;
		latitude = null;
		country = null;
		city = null;
	}

	public Location(String eventID, String longitude, String latitude, String city, String country) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.country = country;
		this.city = city;
		this.eventID = eventID;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		String result = "Longitude: " + longitude + ", Latitude: " + latitude;
		result += "\n City: " + city + ", Country: " + country;
		result += "\n ID: " + eventID;
		return result;
	}

	public String getEventID() {
		return eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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
}
