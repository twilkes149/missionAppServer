package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import serverModel.Event;
import serverModel.Person;
import serverModel.User;

/**
 * A class that will establish a connection to a database and return the connection
 * @author Tucker
 *
 */
public class Setup {
	public static final String EVENT_COLUMN = "eventCount";
	public static final String PERSON_COLUMN = "personCount";
	public static final String USER_COLUMN = "userCount";
	
	
	public Setup()
	{
		try {
			Class.forName("org.sqlite.JDBC");
		}
		catch (ClassNotFoundException e) {
			System.err.println("class not found");
			System.err.print(e.getMessage());
		}
	}
	
	/**
	 * Returns a connection to the database
	 * @param database the name of the database to connect to
	 * @return a connection to the database
	 * @throws Exception throws an exception if an error occurred 
	 */
	public static Connection initialize(String database) throws Exception {
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
		return connection;
	}
	
	public static void initCounts() {
		Connection connection;
		try {
			connection = Setup.initialize("familyHistory");
			Statement stat = connection.createStatement();
			
			//there should only ever be one row in this table
			ResultSet rs = stat.executeQuery("Select * from countTable");
			int eventCount=0;
			int personCount=0;
			int userCount=0;
			if (rs.next()) {
				eventCount = rs.getInt(1);
				personCount = rs.getInt(2);
				userCount =rs.getInt(3);
			}
			//the min IDs users, events and person can have
			Event.setCount(eventCount);
			Person.setCount(personCount);
			User.setCount(userCount);
			stat.close();
			connection.close();
 		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateCount() {
		updateCount(Event.getCount(), EVENT_COLUMN);
		updateCount(User.getCount(), USER_COLUMN);
		updateCount(Person.getCount(), PERSON_COLUMN);
	}
	
	private static void updateCount(int num, String column) {
		Connection connection;
		System.out.println("Updating count table");
		try {
			connection = Setup.initialize("familyHistory");
			Statement stat = connection.createStatement();
			stat.executeUpdate("UPDATE countTable SET " + column + " ='" + num + "';");
			stat.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates all the tables for the database
	 * @param connection a connection to the database
	 */
	public static void createTables(Connection connection) {
		try {
			Statement stat = connection.createStatement();//create the statement
		
			//create the user table
            stat.executeUpdate("create table if not exists user (id KEY TEXT NOT NULL, "
            		+ "username TEXT NOT NULL, password TEXT NOT NULL);");
          
            //create the person table
            stat.executeUpdate("create table if not exists person (id KEY TEXT NULL, "
            		+ "firstName TEXT NOT NULL, lastName TEXT NOT NULL, gender char(1) NOT NULL, "
            		+ "parentLink TEXT, familyID TEXT NOT NULL);");
            
            //create the event table
            stat.executeUpdate("create table if not exists event(id KEY TEXT NULL, "
            		+ "personLink TEXT NOT NULL, userLink TEXT NOT NULL, "
            		+ "type TEXT NOT NULL, year INT NOT NULL, familyID TEXT NOT NULL);");
            
            //create the location table
            stat.executeUpdate("create table if not exists location ("
            		+ "longitude DOUBLE NOT NULL, latitude DOUBLE NOT NULL, country TEXT NOT NULL, "
            		+ "city TEXT NOT NULL, eventLink KEY TEXT NOT NULL);");
            
            //create authToken table
            stat.executeUpdate("create table if not exists authToken ("
            		+ "userLink Key TEXT NOT NULL, timeMin INT NOT NULL, timeHour INT NOT NULL, "
            		+ "dateDay INT NOT NULL, dateMonth INT NOT NULL, dateYear INT NOT NULL, "
            		+ "value KEY TEXT NOT NULL);");
            
            //creating a table to store highest id values
            stat.executeUpdate("create table if not exists countTable(eventCount INT NOT NULL, "
            		+ "personCount INT NOT NULL, userCount INT NOT NULL);");
            
            stat.executeUpdate("create table if not exists families(familyID TEXT NOT NULL, userID TEXT NOT NULL);");
            connection.close();
		}
		catch (SQLException sqlException) {
			System.err.println("Could not initilize tables in database");
			System.err.println(sqlException.getMessage());
		}
		
	}
	
	/**
	 * a test method
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Connection connection = Setup.initialize("familyHistory");
		Setup.createTables(connection);
		System.out.println("done");
		
	}
}
