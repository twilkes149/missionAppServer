package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Services.FillService;
import Services.Input.LoadEvent;
import Services.Response.EResponse;
import serverModel.Event;
import serverModel.Location;
import serverModel.User;

/**
 * The DAO for Events
 * @author Tucker
 *
 */
public class EventDAO extends DatabaseDriver{
	LocationDAO databaseLocation;
	public EventDAO(String name) {
		super(name);
		databaseLocation = new LocationDAO(DatabaseDriver.databaseName);
	}
	
	/** 
	 * Saves an event to the database
	 * @param event a string representation of the event 
	 * @return true if save was successful
	 */
	public boolean saveEvent(LoadEvent event) {
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();
			//getting the user id
			//ResultSet rs = stat.executeQuery("SELECT * FROM user WHERE firstName = '" + event.getDescendant() + "';");
			//String id = "0";
			//if (rs.next())
				//id = rs.getString(1);
			Location location  = new Location(event.getEventId(), event.getLongitude(), event.getLatitude(), event.getCity(), event.getCountry());
			//rs.close();
			
			PreparedStatement prepStat = connection.prepareStatement("INSERT into event values(?,?,?,?,?);");
			prepStat.setString(1, event.getEventId());
			prepStat.setString(2, event.getPersonID());
			prepStat.setString(3, event.getDescendant());
			prepStat.setString(4, event.getType());
			prepStat.setString(5, "" + event.getYear());
			prepStat.addBatch();
			
			connection.setAutoCommit(false);
			prepStat.executeBatch();
			connection.setAutoCommit(true);
			stat.close();
			connection.close();
			databaseLocation.saveLocation(location);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Returns an array of events that belong to a user
	 * @param userID the id of the user
	 * @return list of events
	 */
	public ArrayList<Event> getEventsOfUser(String userID) {
		Connection connection = null;
		PersonDAO personDriver = new PersonDAO(DatabaseDriver.databaseName);
		UserDAO userDriver = new UserDAO(DatabaseDriver.databaseName);
		ArrayList<Event> result = new ArrayList<Event>();
		
		
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			//User user = userDriver.getUserID(userID);
			ResultSet rs = stat.executeQuery("SELECT * from event WHERE userLink = '" + userID + "';");
	
			//loop for all of the results
			while (rs.next()) {
				//getting the event's info
				Event curr = new Event();
				curr.setId(rs.getString(1));
				curr.setOwner(personDriver.getPerson(rs.getString(2)));
				curr.setType(rs.getString(4));
				curr.setYear(rs.getInt(5));
				
				Location location = databaseLocation.getLocation(curr.getId());
				curr.setLocation(location);
				
				/*curr.setLongitude(location.getLongitude());
				curr.setLatitude(location.getLatitude());
				curr.setCity(location.getCity());
				curr.setCountry(location.getCountry());
				curr.setUser(user.getFirstName());
				*/
				result.add(curr);
			}
			return result;
		}
		catch (Exception e) {
			//System.err.println("Error getting user's events: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Returns an array of events that belong to a user
	 * @param userName the userName of the user
	 * @return list of events
	 */
	public ArrayList<EResponse> getEventsOfUserName(String userName) {
		Connection connection = null;
		UserDAO userDriver = new UserDAO(DatabaseDriver.databaseName);
		ArrayList<EResponse> result = new ArrayList<EResponse>();
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			User user = userDriver.getUser(userName);
			System.out.println("Users's id: " + user.getUserId());
			ResultSet rs = stat.executeQuery("SELECT * from event WHERE userLink = '" + user.getUserId() + "';");
	
			//loop for all of the results
			while (rs.next()) {
				//getting the event's info
				EResponse curr = new EResponse();
				curr.setId(rs.getString(1));
				curr.setPersonID(rs.getString(2));
				curr.setType(rs.getString(4));
				curr.setYear(rs.getString(5));
				Location location = databaseLocation.getLocation(curr.getId());
				curr.setLongitude(location.getLongitude());
				curr.setLatitude(location.getLatitude());
				curr.setCity(location.getCity());
				curr.setCountry(location.getCountry());				
				
				result.add(curr);
			}
			System.out.println("Event size: " + result.size());
			return result;
		}
		catch (Exception e) {
			System.err.println("Error getting user's events: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Deletes an event from the database
	 * @param id id of the event
	 * @return true if successful 
	 */
	public boolean deleteEvent(String id) {
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();
			stat.executeUpdate("DELETE FROM event WHERE id = '" + id + "';");
			stat.executeUpdate("DELETE FROM location WHERE eventLink = '" + id + "';");
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns an array of events that belong to a person
	 * @param personID the id of the person
	 * @return list of events
	 */
	public ArrayList<Event> getEventsOfPerson(String personID) {
		Connection connection = null;
		ArrayList<Event> result = new ArrayList<Event>();
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from event WHERE personLink = '" + personID + "';");
	
			//loop for all of the results
			while (rs.next()) {
				//getting the event's info
				Event curr = new Event();
				curr.setId(rs.getString(1));
				//TODO get owner
				curr.setType(rs.getString(4));
				curr.setYear(rs.getInt(5));
				curr.setLocation(databaseLocation.getLocation(curr.getId()));
				result.add(curr);
			}
			return result;
		}
		catch (Exception e) {
			System.err.println("Error getting person's events: " + e.getMessage());
			return null;
		}
	}

	/** Gets information about an event from the database
	 * @param id the ID of the event
	 * @return returns the event if it exists, otherwise returns null
	 */
	public Event getEvent (String id) {
		Connection connection = null;
		Event result = new Event();
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from event WHERE id = '" + id + "';");
			
			if (rs.next()) {
				result.setId(rs.getString(1));
				result.setType(rs.getString(4));
				result.setYear(rs.getInt(5));
			}
			else {
				rs.close();
				connection.close();
				return null;
			}
			rs.close();
			connection.close();
			result.setLocation(databaseLocation.getLocation(result.getId()));
			
			return result;
		}
		catch (Exception e) {//failed getting event probably because it's not in the database
			//System.err.println("Errorr getting event");
			//System.err.println(e.getMessage());
			//e.printStackTrace();
			return null;
		}
	}
	
	/** adds an event to the database
	 * @param event the even to save to the database
	 * @return returns true if successful
	 */
	public boolean saveEvent(Event event) throws Exception{
		Connection connection = Setup.initialize(databaseName);
		PreparedStatement stat = connection.prepareStatement("insert into location values(?,?,?,?,?);");
		stat.setString(1, "" + event.getLocation().getLongitude());
		stat.setString(2, "" + event.getLocation().getLatitude());
		stat.setString(3, "" + event.getLocation().getCountry());
		stat.setString(4, "" + event.getLocation().getCity());
		stat.setString(5, "" + event.getId());
		//stat.setString(6, "" + event.getId());//link to the person this event belongs to
		stat.addBatch();
		
		connection.setAutoCommit(false);
		stat.executeBatch();
		connection.setAutoCommit(true);
		
		PreparedStatement stat1 = connection.prepareStatement("insert into event values(?,?,?,?,?);");
		stat1.setString(1, "" + event.getId());
		if (event.getOwner() != null)
			stat1.setString(2, "" + event.getOwner().getId());
		else
			stat1.setString(2, "");
		if (event.getDescendant() != null)
			stat1.setString(3, "" + event.getDescendant().getUserId());
		else
			stat1.setString(3, "");
		stat1.setString(4, event.getType());
		stat1.setString(5, "" + event.getYear());
		stat1.addBatch();
		
		connection.setAutoCommit(false);
		stat1.executeBatch();
		connection.setAutoCommit(true);
		connection.close();
		return true;
	}
	
	/**
	 * a simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		EventDAO eventDriver = new EventDAO("familyHistory");
		try {
			/*
			eventDriver.clear();
			eventDriver.initilize();
			eventDriver.saveEvent(new Event("birth", null, null, "23.454", "90.88", "wichita", "USA", 1994));
			
			Event myEvent = eventDriver.getEvent(0);
			System.out.println(myEvent.toString());*/
			
			eventDriver.saveEvent(new LoadEvent("Tucker", "8", "1", "12.33", "98.0", "USA", "Wichita", 1994, "birth"));
		
		}
		catch (Exception e) {
			System.err.print("Error saving event: "+ e.getMessage());
		}
		
	}
}
