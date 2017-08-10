package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import serverModel.AuthToken;
/**
 * A class that will retrieve and save authTokens to the database
 * @author Tucker
 *
 */

public class AuthTokenDAO extends DatabaseDriver{

	public AuthTokenDAO(String name) {
		super(name);
	}
	
	/**
	 * This method updates the auth token for a user
	 * @param userID the id of the user we are update the token for
	 * @return true if succesful
	 */
	public boolean updateToken(String userID) {
		return false;
	}
	
	/**
	 * Deletes an authToken from the database
	 * @param userID the userID of the authToken to delete
	 * @return true if deletion was successful
	 */
	public boolean deleteAuthToken(String userID) {
		Connection connection;
		try {
			connection = Setup.initialize(this.databaseName);
			Statement stat = connection.createStatement();
			stat.executeUpdate("DELETE FROM authToken WHERE userLink = '" + userID + "';");
			return true;
		}
		catch (Exception e) {
			System.err.println("Error deleting authToken");
			System.err.println(e.getMessage());
			return false;
		}
	}

	/** 
	 * Saves an authToken to the database
	 * @param token the token to save
	 * @return true if successful 
	 * @throws Exception throws exception if an error occurred
	 */
	public boolean saveAuthToken(AuthToken token) throws Exception{
		Connection connection = null;
		connection = Setup.initialize(databaseName);
		PreparedStatement stat = connection.prepareStatement("insert into authToken values(?,?,?,?,?,?,?);");
		
		stat.setString(1, "" + token.getUserID());
		stat.setString(2, "" + token.getMin());
		stat.setString(3, "" + token.getHour());
		stat.setString(4, "" + token.getDay());
		stat.setString(5, "" + token.getMonth());
		stat.setString(6, "" + token.getYear());
		stat.setString(7, token.getToken());
		stat.addBatch();
		
		connection.setAutoCommit(false);
		stat.executeBatch();
		connection.setAutoCommit(true);
		connection.close();
		return true;
	}
	
	/**
	 * Retrieves an authToken from the database
	 * @param userID the userID of the token to retrieve
	 * @return the authToken if found, if not found returns null
	 */
	public AuthToken getAuthToken(String userID) {
		Connection connection = null;
		AuthToken result = null;
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from authToken WHERE userLink = '" + userID + "';");
			
			int min = rs.getInt(2);
			int hour = rs.getInt(3);
			int day = rs.getInt(4);
			int month = rs.getInt(5);
			int year = rs.getInt(6);
			String value = rs.getString(7);
			result = new AuthToken(userID, hour, min, day, month, year);
			result.setToken(value);
			connection.close();
			return result;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Gets an authToken from the database
	 * @param token the value of the authToken
	 * @return the authToken if found, otherwise it returns null
	 */
	public AuthToken getAuthTokenValue(String token) {
		Connection connection = null;
		AuthToken result = null;
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from authToken WHERE value = '" + token + "';");
			
			String userID = rs.getString(1);
			int min = rs.getInt(2);
			int hour = rs.getInt(3);
			int day = rs.getInt(4);
			int month = rs.getInt(5);
			int year = rs.getInt(6);
			String value = rs.getString(7);
			result = new AuthToken(userID, hour, min, day, month, year);
			result.setToken(value);
			connection.close();
			return result;
		}
		catch (Exception e) {
			return null;
		}
	}
}
