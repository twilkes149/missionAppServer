package Services;
import Services.Input.*;
import Services.Response.*;
import database.AuthTokenDAO;
import database.DatabaseDriver;
import database.Setup;
import database.UserDAO;
import serverModel.AuthToken;
import serverModel.User;

/**
 * A class that implements the load service <br>
 * takes a list of users, person, and events and populates the database with them
 * @author Tucker
 *
 */
public class LoginService implements Service {
	private UserDAO userDriver;
	private String databaseName;
	
	public LoginService(String databaseName) {
		this.databaseName = databaseName;
		userDriver = new UserDAO(databaseName);
	}
	/**
	 * Logs a user into the server;
	 * 	checks if the userName is in the database, and if so checks to see if the 
	 * 	supplied password matches 
	 * generates an authToken and returns it. 
	 * @param data the data required to log a user in: userName and password
	 * @return returns a Response object containing an authToken, userName and personID
	 */
	public Response fillService(Input data) throws Exception {
		AuthTokenDAO authTokenDriver = new AuthTokenDAO(databaseName);
		LoginInput input = null;

		if (data instanceof LoginInput)
			input = (LoginInput) data;
		else
			throw new Exception("Input data was wrong");			
		
		//search the database for the userName
		User user = userDriver.getUser(input.getUsername());
		
		//creating the response object
		LoginResponse result = new LoginResponse();
		
		//checking to see if the user was in the database
		if (user != null) {
			System.out.println(user.toString());
			if (!user.getPassword().equals(input.getPassword()))
				return new LoginResponse("Password/username is not correct");
			
			//updating the authToken
			AuthToken token = authTokenDriver.getAuthToken(user.getUserId());
			authTokenDriver.deleteAuthToken(user.getUserId());
			AuthToken newToken = new AuthToken(user.getUserId());
			authTokenDriver.saveAuthToken(newToken);
			
			result.setAuthToken(newToken);
			result.setId("" + user.getUserId());
			result.setUserName(user.getUserName());		

		}
		else {
			return new LoginResponse("Username/password is not correct");
		}
		return result;
	}
	
	public static void main(String[] args) {
		LoginService service = new LoginService("testDatabase");
		LoginResponse result;
		try {
			result = (LoginResponse)
				service.fillService(new LoginInput("twilkes", "password"));
			if (result != null && !result.isError())
				System.out.println(result.getAuthTokenValue());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
