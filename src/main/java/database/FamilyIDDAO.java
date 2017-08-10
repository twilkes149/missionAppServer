package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FamilyIDDAO extends DatabaseDriver{

	public FamilyIDDAO(String name) {
		super(name);
	}
	
	public boolean saveFamilyID(String id, String userID) throws Exception{
		Connection connection = null;
		try {
			connection = Setup.initialize(databaseName);
			
			//checking to see if this tuple already exists in the database
			Statement test = connection.createStatement();
			ResultSet testSet = test.executeQuery("SELECT * FROM families WHERE familyID ='" + id + "' AND "
			+ "userID = '" + userID + "';");
			
			if (testSet.next())
				throw new Exception("Tuple already exists");
			
			PreparedStatement stat = connection.prepareStatement("insert into families values(?,?);");
			stat.setString(1, id);
			stat.setString(2, userID);
			stat.addBatch();
			
			connection.setAutoCommit(false);//prepares a transaction
	        stat.executeBatch();//executes the command
	        connection.setAutoCommit(true);//if execute batch failed, it doesn't die
	        stat.close();
			connection.close();
			
			return true;
		} catch (SQLException e) {		
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * Gets a list of families the user belongs to
	 * @param userID id of the user
	 * @return a list of family ids
	 */
	public ArrayList<String> getFamilies(String userID) {
		ArrayList<String> result = null;
		Connection connection;
		try {
			connection = Setup.initialize(databaseName);
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM families WHERE userID = '" + userID + "';");
			
			
			result = new ArrayList<String>();
			while (rs.next()) {
				result.add(rs.getString(1));				
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public static void main(String args[]) {
		FamilyIDDAO driver = new FamilyIDDAO("testDatabase");
		
		try {
		
			driver.saveFamilyID("1234", "1");
			driver.saveFamilyID("5678", "1");
			driver.saveFamilyID("9999", "1");
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		ArrayList<String> result = driver.getFamilies("1");
		for (int i = 0; i < result.size(); i++)
			System.out.println(result.get(i));
		
	}

}
