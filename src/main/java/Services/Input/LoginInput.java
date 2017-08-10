package Services.Input;
/**
 * A class that contains all of the input information needed for a user to login.
 * Inherits from the Input class
 * @author Tucker
 *
 */
public class LoginInput extends Input{
	private String password;
	private String userName;
	
	public LoginInput() {
		userName = null;
		password = null;
	}
	
	public LoginInput(String un, String pw) {
		userName = un;
		password = pw;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
