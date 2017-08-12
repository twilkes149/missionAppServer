package Services.Response;
import serverModel.AuthToken;

/**
 * A class that holds all of the information for a register result
 * Fields: userName, id, and an AuthToken
 * @author Tucker
 *
 */
public class RegisterResponse extends Response{
	private String userName;
	private String ID;
	private AuthToken authToken;
	private boolean error;
	private String message;	
	
	/**
	 * sets all fields to null
	 */
	public RegisterResponse() {
		authToken = null;
		userName = null;
		ID = null;
	}
	
	public RegisterResponse(String message) {
		this.message = message;
		error = true;
	}
	
	public boolean getError() {
		return error;
	}

	public void setError() {
		this.error = true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param at the authToken of the user
	 * @param un the userName of the user
	 * @param id the id of the user
	 */
	public RegisterResponse(AuthToken at, String un, String id) {
		ID = id;
		authToken = at;
		userName = un;
	}
	
	public AuthToken getAuthToken() {
		return authToken;
	}
	public void setAuthToken(AuthToken authToken) {
		this.authToken = authToken;
	}
	public String toString() {
		if (!error)
			return "UserName: " + userName +"\nID: " + ID + 
				"\nAuthToken: " + authToken; 
		else
			return "\nError: " + 
			error + "\nMessage: " + message;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
}
