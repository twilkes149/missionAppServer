package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import serverModel.Location;

/**
 * A class to represent a location, which each event has\n
 * Fields: longitude, latitude, country, city, year
 * @author Tucker
 *
 */
public class LocationDAO extends DatabaseDriver{
	
	public LocationDAO(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns a location
	 * @param eventID the id of the event
	 * @return a list of locations
	 */
	public Location getLocation(String eventID) {
		Connection connection;
		Location location = new Location(eventID);
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from location WHERE eventLink = '" + eventID + "';");
			
			location.setEventID(eventID);
			location.setLongitude(rs.getString(1));
			location.setLatitude(rs.getString(2));
			location.setCountry(rs.getString(3));
			location.setCity(rs.getString(4));
			rs.close();
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			return null;
		}
		return location;		
	}
	
	/**
	 * Saves a location to the database
	 * @param location the location to save
	 * @return true if successful
	 * @throws Exception throws an exception if an error occurred 
	 */
	public boolean saveLocation(Location location) throws Exception {
		if (location.getCity() == null || location.getCountry() == null || location.getLongitude() == null ||
				location.getLatitude() == null)
			return false;
		
		Connection connection = Setup.initialize(databaseName);
		PreparedStatement stat = connection.prepareStatement("insert into location values(?,?,?,?,?);");
		
		stat.setString(1, location.getLongitude());
		stat.setString(2, location.getLatitude());
		stat.setString(3, location.getCountry());
		stat.setString(4, location.getCity());
		stat.setString(5, "" + location.getEventID());
		stat.addBatch();
		
		connection.setAutoCommit(false);//prepares a transaction
        stat.executeBatch();//executes the command
        connection.setAutoCommit(true);//if execute batch failed, it doesn't die
        stat.close();
		connection.close();
		return true;
	}
	
	/**
	 * a simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		LocationDAO val = new LocationDAO("familyHistory");
		try {
			val.clear();//clear the database
			Setup.createTables(Setup.initialize("familyHistory"));
			val.saveLocation(new Location("0", "45.5", "12.365", "wichita", "USA"));
		}
		catch (Exception e) {
			
		}
	}
}
