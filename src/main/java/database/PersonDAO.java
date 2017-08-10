package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import Services.FillService;
import Services.Input.LoadPerson;
import Services.Response.PResponse;
import serverModel.Event;
import serverModel.Person;
import serverModel.User;

/**
 * The DAO for a Person<br>
 * Fields: firstName, lastName, gender, mother, father, spouse, descendant, id<br>
 * @author Tucker
 * 
 */
public class PersonDAO extends DatabaseDriver{
	EventDAO eventDriver;
	UserDAO userDriver;
	public PersonDAO(String name) {
		super(name);
	}
	
	/**
	 * Gets all the perosn's that belong to a specific user
	 * @param userName the userName of the user
	 * @return a list of persons
	 */
	public ArrayList<PResponse> getPersonsFromFamily(String familyID) {
		userDriver = new UserDAO(DatabaseDriver.databaseName);
		ArrayList<PResponse> result = new ArrayList<PResponse>();
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();			
			
			ResultSet rs = stat.executeQuery("SELECT * from person WHERE familyID = '" + familyID + "';");
			
			//getting all of the persons
			while (rs.next()) {
				PResponse curr;
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				char gender = rs.getString(4).charAt(0);
				String parentLink = rs.getString(5);				
				
				curr = new PResponse(firstName, lastName, gender, parentLink, familyID);				
				result.add(curr);
			}
			rs.close();
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	/**
	 * Saves a person to the database
	 * @param person the string representation of the person
	 * @return true if successful 
	 *//*
	public boolean savePerson(Person person) {
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();
			//ResultSet rs = stat.executeQuery("SELECT * FROM user WHERE firstName = '" + person.getFirstName() + "';");
			//String id="0";
			//if (rs.next())
			//	id = rs.getString(1);
			//rs.close();
			
			PreparedStatement prepStat = connection.prepareStatement("INSERT into person values(?,?,?,?,?,?,?,?);");
			prepStat.setString(1, "" + person.getId());
			prepStat.setString(2, person.getFirstName());
			prepStat.setString(3, person.getLastName());
			prepStat.setString(4, "" + person.getGender());
			prepStat.setString(5, person.getParent());
			prepStat.addBatch();
			
			connection.setAutoCommit(false);
			prepStat.executeBatch();
			connection.setAutoCommit(true);
			
			stat.close();
			connection.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
	/**
	 * Deletes a person from the database
	 * @param id the id of the person
	 * @return returns true if successful
	 */
	public boolean deletePerson(String id) {
		Connection connection;
		try {
			connection = Setup.initialize(DatabaseDriver.databaseName);
			Statement stat = connection.createStatement();
			
			Person thisPerson = this.getPerson(id);
			if (thisPerson == null)
				return false;
			
			//deleting the person
			stat.executeUpdate("DELETE FROM person WHERE id = '" + id + "'; ");
			//deleting the events of the person
			stat.executeUpdate("DELETE FROM event WHERE personLink = '" + id + "';"); 
			stat.close();
			connection.close();			
		}
		catch (Exception e) {
			System.err.println("Error deleting person");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** Gets information about a person from the database
	 * @param id the ID of the person
	 * @return returns the person if it exists, otherwise returns null
	 */
	private Person getPerson(String id) {
		eventDriver = new EventDAO(DatabaseDriver.databaseName);
		if (id == null || id.equals(Person.INVALID_ID))
			return null;//invalid id for a person
		
		//TODO add person's events
		Connection connection = null;
		//creating the personDAO to return
		Person result = new Person();
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			
			//getting the person from the database
			ResultSet rs = stat.executeQuery("SELECT * from person WHERE id = '" + id + "';");
			
			if (rs.next()) {
				//setting the data to query result
				result.setFirstName(rs.getString(2));
				result.setLastName(rs.getString(3));
				result.setGender(rs.getString(4).charAt(0));	
				result.setParentID(rs.getString(5));
				result.setFamilyID(rs.getString(6));
				result.setEventList(eventDriver.getEventsOfPerson(id));//something happened here
			}
			else {
				return null;
			}
			rs.close();
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			//System.err.println("Error getting person");
			//System.err.println(e.getMessage());
			//e.printStackTrace();
			return null;
		}
		result.setId(id);
		return result;
	}
	
	/**  adds a person to the database
	 * @param person the person to save to the database
	 * @return returns true if successful 
	 */
	public boolean savePerson(Person person) throws Exception{
		//check to see if a person with this id is already in the database
		if (this.getPerson(person.getId()) != null)
			throw new Exception("This id already exists");
		Connection connection = Setup.initialize(databaseName);
		PreparedStatement stat = connection.prepareStatement("insert into person values (?,?,?,?,?,?);");
		stat.setString(1, "" + person.getId());
		stat.setString(2, person.getFirstName());
		stat.setString(3, person.getLastName());
		stat.setString(4, "" + person.getGender());
		stat.setString(5,  person.getParentID());
		stat.setString(6,  person.getFamilyID());
		stat.addBatch();
		
		connection.setAutoCommit(false);//prepares a transaction
        stat.executeBatch();//executes the command
        connection.setAutoCommit(true);//if execute batch failed, it doesn't die
        stat.close();
		connection.close();

		//adding the user's events to the database
        for (int i = 0; i < person.eventListSize(); i++)
        	if (eventDriver.getEvent(person.getEvent(i).getId() ) == null)//if the even hasn't already been saved in the database
        		eventDriver.saveEvent(person.getEvent(i));               
		return true;
	}
	
	/**
	 * a simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		PersonDAO personDriver = new PersonDAO("testDatabase");
		try {			
			personDriver.clear();
			personDriver.initilize();
			personDriver.savePerson(new Person("Tucker", "Wilkes", 'm', null, "dkd8ds9dssd66"));
			
			Person person = personDriver.getPerson("1");
			System.out.println(person.toString());
		}
		catch (Exception e) {
			System.err.println("Error with person: " + e.getMessage() + "\n");
		}
	}
}
