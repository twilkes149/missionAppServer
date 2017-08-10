package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import serverModel.Event;
import serverModel.Person;
import serverModel.User;

/**
 * The DAO for a user<br>
 * @author Tucker
 *
 */
public class UserDAO extends DatabaseDriver {
	AuthTokenDAO authTokenDriver;
	PersonDAO personDriver;
	EventDAO eventDriver;
	public UserDAO(String name) {
		super(name);
		eventDriver = new EventDAO(DatabaseDriver.databaseName);
		personDriver = new PersonDAO(DatabaseDriver.databaseName);
		authTokenDriver = new AuthTokenDAO(DatabaseDriver.databaseName);
	}
	
	/**
	 * Deletes a user and all data relating to the user from the database
	 * @param userName the userName of the user
	 * @return returns true if successful
	 */
	public boolean deleteUser(String userName) {
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();
			//deleting the user
			stat.executeUpdate("DELETE FROM user WHERE userName ='" + userName + "';");
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			System.err.println("Error deleting a user");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** adds a user to the database
	 * @param user the user to save to the database
	 * @return returns true if successful
	 */
	public boolean saveUser(User user) throws Exception{
	
		if (getUser(user.getUserName()) != null)
			throw new Exception("Username already exists");
		
		Connection connection = Setup.initialize(databaseName);
		PreparedStatement stat = connection.prepareStatement("insert into user values(?,?,?)");
		
		stat.setString(1, "" + user.getUserId());
		stat.setString(2, user.getUserName());
		stat.setString(3, user.getPassword());
		stat.addBatch();
		
		connection.setAutoCommit(false);
		stat.executeBatch();
		connection.setAutoCommit(true);
		stat.close();
		connection.close();
	
		return true;
	}
	
	/** gets information about a user from the database
	 * @param id the unique ID of the user
	 * @return returns the user if it exists, otherwise returns null
	 */
	public User getUserID(String id) {
		Connection connection = null;
		User result = null;
		
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			//getting the user from the database
			ResultSet rs = stat.executeQuery("SELECT * from user WHERE id = '" + id + "';");
			result = new User();
			result.setUserId(rs.getString(1));
			result.setUserName(rs.getString(2));
			result.setPassword(rs.getString(3));
			
			rs.close();
			connection.close();
		}
		catch (Exception e) {
			//TODO comment out these lines
			System.err.println("error getting a user");
			System.err.println(e.getMessage());
			return null;
		}
		return result;
	}
	
	/** gets information about a user from the database
	 * @param userName the userName of the user
	 * @return returns the user if it exists, otherwise returns null
	 */
	public User getUser(String userName) {
		Connection connection = null;
		User result = null;
		
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			
			//getting the user from the database
			ResultSet rs = stat.executeQuery("SELECT * from user WHERE username = '" + userName + "';");			
			if (rs.next()) {
				result = new User();
				result.setUserId(rs.getString(1));
				result.setUserName(rs.getString(2));
				result.setPassword(rs.getString(3));
			}
			rs.close();
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
			
		return result;
	}
	
	public static void main(String args[]) {
		UserDAO driver = new UserDAO("testDatabase");
		
		try {
			driver.saveUser(new User("twilkes", "password"));
			System.out.println("got here");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
	}
	
	

}
