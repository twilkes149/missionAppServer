package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import serverModel.FamilyID;

public class FamilyDAO extends DatabaseDriver {

	public FamilyDAO(String name) {
		super(name);		
	}
	
	public boolean saveFamily(String id, String name) {
		Connection connection;
		
		try {
			connection = Setup.initialize(databaseName);
			PreparedStatement stat = connection.prepareStatement("insert into families values(?,?)");
			
			stat.setString(1, id);
			stat.setString(2, name);			
			stat.addBatch();
			
			connection.setAutoCommit(false);//prepares a transaction
	        stat.executeBatch();//executes the command
	        connection.setAutoCommit(true);//if execute batch failed, it doesn't die
	        stat.close();
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public FamilyID getFamily(String id) {
		FamilyID result = null;
		
		Connection connection;
		
		try {
			connection = Setup.initialize(databaseName);
			
			Statement stat = connection.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * from families WHERE id ='" + id + "';");
			if (rs.next()) {
				result = new FamilyID(rs.getString(1), rs.getString(2));
			}
			rs.close();
			stat.close();
			connection.close();
			return result;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main (String args[]) {
		FamilyDAO driver = new FamilyDAO("testDatabase");
		
		try {
			driver.clear();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.initilize();
		FamilyID id = new FamilyID("wilkes");
		driver.saveFamily(id.getId(), id.getFamilyName());
		FamilyID id1 = driver.getFamily(id.getId());
		System.out.println(id.getFamilyName());
	}
	

}
