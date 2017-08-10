package database;

import java.sql.Statement;

import serverModel.Event;
import serverModel.Person;
import serverModel.User;

import java.sql.Connection;

/**
 * A class that will perform basic operations on the database<br>
 * @author Tucker
 *
 */
//TODO create table that keeps track of highest index for each table, so reruning the program wont create duplicate ids
public class DatabaseDriver {
	public static String databaseName;
	
	/** Sets up the database, creates tables
	 * @param name The name of the database
	 */
	public DatabaseDriver(String name) {
		databaseName = name;
		this.initilize();
	}
	
	/** Erases one table from the database
	 * @param tableName the name of the table
	 * @return returns true if successful
	 */
	private boolean eraseTable(String tableName) throws Exception{
		Connection connection;
		connection = Setup.initialize(databaseName);
		Statement stat = connection.createStatement();
		
		stat.executeUpdate("drop table if exists " + tableName + ";");
		stat.close();
		connection.close();
		return true;
	}
	
	/** Erases all tables from the database
	 * @return returns true if successful
	 */
	public boolean clear() throws Exception {
		//a list of all the tables
		String[] tableList = {"person", "user", "event", "location", "authToken", "countTable"};
		//erasing all of the tables
		for (int i = 0; i < 6; i++)
			this.eraseTable(tableList[i]);
		
		Connection connection;
		try {
			connection = Setup.initialize(databaseName);
			Setup.createTables(Setup.initialize(databaseName));
			Statement stat = connection.createStatement();
			
			stat.executeUpdate("insert into countTable values(1,1,1);");
			stat.close();
			connection.close();
			Event.setCount(1);
			Person.setCount(1);
			User.setCount(1);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Creates all of the tables if they don't exist
	 */
	public boolean initilize() {
		try {
			Setup.createTables(Setup.initialize(databaseName));
			return true;
		}
		catch (Exception e) {
			System.err.println("Failed creating tables: ");
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public static void main(String[] args) {
		DatabaseDriver val = new DatabaseDriver("familyHistory");
		try {
			val.clear();
			val.initilize();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
