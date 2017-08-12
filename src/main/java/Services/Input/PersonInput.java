package Services.Input;

/**
 * A class that holds all of the information needed for the person service
 * <br>Fields: userName, personID
 * @author Tucker
 *
 */
public class PersonInput extends Input{
	private String userID;
	private String personID;
	
	public PersonInput() {
		userID = null;
		personID = null;
	}
	
	public PersonInput(String userID) {
		this.userID = userID;
	}
	
	public PersonInput(String userID, String personID) {
		this.userID = userID;
		this.personID = personID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userName) {
		this.userID = userName;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}
}
