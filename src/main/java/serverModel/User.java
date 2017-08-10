//this is a test
package serverModel;
import java.util.ArrayList;

import database.Setup;

/**
 * The object oriented model of a User<br>
 * Fields: userName, password, email, firstName, lastName, eventList, mother, father, spouse and gender<br>
 * @author Tucker
 *
 */
public class User {
	private String userName;
	private String password;
	private AuthToken authToken;
	private String userId;
	protected static int count=1;	
	protected Person parent;
	protected ArrayList<String> familyIDs;
	
	public User() {
		this.userId = "" + User.count;
		User.count++;
		
		userName = null;
		password = null;
		authToken = null;
		authToken = new AuthToken(userId);
		parent = null;
	}
	
	public User(String userName, String password) {
		this.userId = "" + User.count;
		User.count++;
		this.userName = userName;
		this.password = password;
		authToken = new AuthToken(userId);
	}
	
	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}

	public ArrayList<String> getFamilyIDs() {
		return familyIDs;
	}

	public void setFamilyIDs(ArrayList<String> familyIDs) {
		this.familyIDs = familyIDs;
	}

	public static void setCount(int count) {
		User.count = count;
	}
	
	public static int getCount() {
		return User.count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public AuthToken getAuthToken() {
		return authToken;
	}
	public void setAuthToken(AuthToken authToken) {
		this.authToken = authToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString() {
		return this.userName + " " + this.password;
	}

}
