package Services.Response;

import database.AuthTokenDAO;
import serverModel.AuthToken;

/**
 * A class that contains all of the information that the login service returns
 * Inherits from the response class
 * Fields: userName, id
 * @author Tucker
 *
 */
public class LoginResponse extends Response{
	private String userName;
	private String id;
	private boolean error;
	private String message;
	private AuthToken authToken;	
	
	/**
	 * @param at the authToken of the user
	 * @param un the userName of the user
	 * @param i the id of the user
	 */
	public LoginResponse(AuthToken at, String un, String i) {
		authToken = at;
		userName = un;
		id = i;
		error = false;
	}
	
	public LoginResponse(String errorMessage) {
		this.message = errorMessage;
		error = true;
	}
	
	public AuthToken getAuthToken() {
		return authToken;
	}

	public void setAuthToken(AuthToken authToken) {
		this.authToken = authToken;
	}

	public String getAuthTokenValue() {
		return authToken.getToken();
	}
	
	public boolean isError() {
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

	public LoginResponse() {
		userName = null;
		id = null;
		authToken = null;
	}
	
	/**
	 * Returns a string representation of this object
	 */
	public String toString() {
		if (!error)
			return "Username: " + userName + "\nID: " + id + "\nAuthToken: " + authToken;
		else
			return "Error: " + message; 
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

}
