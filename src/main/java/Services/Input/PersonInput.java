package Services.Input;

/**
 * A class that holds all of the information needed for the person service
 * <br>Fields: userName, personID
 * @author Tucker
 *
 */
public class PersonInput extends Input{
	private String userName;
	private String personID;
	
	public PersonInput() {
		userName = null;
		personID = null;
	}
	
	public PersonInput(String userName) {
		this.userName = userName;
	}
	
	public PersonInput(String userName, String personID) {
		this.userName = userName;
		this.personID = personID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}
	
	
}
