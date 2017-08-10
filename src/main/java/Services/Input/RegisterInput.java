package Services.Input;

/**
 * A class that contains all of the information needed for a user to register.
 * Fields: password, email, firstName, lastName, gender
 * @author Tucker
 *
 */
public class RegisterInput extends Input {
	private String password;	
	private String userName;
	private String familyID;
	private String familyName;
	
	public String getFamilyID() {
		return familyID;
	}

	public void setFamilyID(String familyID) {
		this.familyID = familyID;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public RegisterInput() {
		
	}
	
	public RegisterInput(String userName, String password) {		
		this.userName = userName;
		this.password = password;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		String result = "";
		result += "\nUser name: " + userName;
		result += "\nPassoword: " + password;		
		return result;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
