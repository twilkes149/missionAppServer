package Services.Response;

import serverModel.FamilyID;

/**
 * A class that contains all the information representing a person in the database
 * Not meant to be directly instantiated.
 * @author Tucker
 *
 */
public class PResponse extends Response {	
	private String personId;
	private String firstName;
	private String lastName;
	private char gender;
	private String parentLink;
	private String familyName;
	
	public PResponse(String id, String firstName, String lastName, char gender, String parentLink, String familyName) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.parentLink = parentLink;
		this.familyName = familyName;
		this.personId = id;
	}
	
	public PResponse() {		
		personId = null;
		firstName = null;
		lastName = null;
		familyName = null;
		parentLink = null;
		gender = '\0';
	}
	
	/**
	 * returns a string representation of this object
	 */
	public String toString() {
		String result = firstName + " " + lastName + ", ID: " + personId;
		return result;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
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

	public String getParentLink() {
		return parentLink;
	}

	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyID(String familyName) {
		this.familyName = familyName;
	}	
}
