package Services.Response;

/**
 * A class that is a string representation of an event, used in eventResponse
 * @author Tucker
 *
 */
public class EResponse extends Response{
	private String user;
	private String id;
	private String personID;
	private String latitude;
	private String longitude;
	private String country;
	private String city;
	private String type;
	private String year;
	
	public EResponse() {
		user = null;
		id = null;
		personID = null;
		latitude = "0";
		longitude = "0";
		country = null;
		city = null;
		type = null;
		year = null;
	}
	
	public String toString() {
		return "ID: " + id + " type: " + type + " personID: " + personID + "\n";
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
}
