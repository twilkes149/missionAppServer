package Services;

import Services.Input.*;
import database.AuthTokenDAO;
import database.FamilyDAO;
import database.FamilyIDDAO;
import database.UserDAO;
import Services.Response.*;
import serverModel.AuthToken;
import serverModel.FamilyID;
import serverModel.User;

/**
 * A service class to access the database and register a user implements the service class
 * @author Tucker
 *
 */
public class RegisterService implements Service {
	private UserDAO userDriver;
	private AuthTokenDAO authTokenDriver;
	private FamilyIDDAO familyUserDriver;
	private FamilyDAO familyDriver;
	private String databaseName;
	
	public RegisterService(String databaseName) {
		this.databaseName = databaseName;
		userDriver = new UserDAO(databaseName);
		authTokenDriver = new AuthTokenDAO(databaseName);
		familyUserDriver = new FamilyIDDAO(databaseName);
		familyDriver = new FamilyDAO(databaseName);
	}

	/** Creates a user using the provided information. Adds data to the database and returns an authToken for the user
	 * @param data the information needed to register a person:\n
	 * user name, password, email, first name, last name, and gender
	 * @return returns a response object containing the user's authToken, userName, and ID
	 */
	public Response fillService(Input data) throws Exception{
		RegisterInput input = null;
		if (data instanceof RegisterInput)
			input = (RegisterInput) data;
		else
			throw new Exception("Input data was wrong");
		
		//getting the user data from the input
		User user = new User(input.getUsername(), input.getPassword());
		
		//check to make sure there are no user's with same userName
		if (userDriver.getUser(input.getUsername()) != null)
			throw new Exception("That userName already exists");
		
		//if we have a family id, save it to familyID
		if (input.getFamilyID() != null && input.getFamilyID().length() > 1)
			familyUserDriver.saveFamilyID(user.getUserId(), input.getFamilyID());
		
		//if we have a family name, create a new family and add the current user to familyID
		if (input.getFamilyName() != null && input.getFamilyName().length() > 1) {
			FamilyID newFamily = new FamilyID(input.getFamilyName());
			familyDriver.saveFamily(newFamily.getId(), newFamily.getFamilyName());//saving the family name
			familyUserDriver.saveFamilyID(newFamily.getId(), user.getUserId());//saving the userID and familyID in cross reference table
		}			
		
		//saving the user&authToken to the database
		userDriver.saveUser(user);
		authTokenDriver.saveAuthToken(user.getAuthToken());
		
		RegisterResponse result = new RegisterResponse();
		result.setID("" + user.getUserId());
		result.setUserName(user.getUserName());
		result.setAuthToken(user.getAuthToken());	
		
		return result;
	}
	
	/**
	 * A simple test method
	 * @param args
	 */
	public static void main (String[] args) {
		RegisterService val = new RegisterService("testDatabase");
		UserDAO test = new UserDAO("testDatabase");
		
		try {
			//test.clear();
			test.initilize();
			RegisterInput input = new RegisterInput("twilkes", "password");
			input.setFamilyName("wilkes");
			val.fillService(input);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
