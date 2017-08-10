package Services;
import Services.Input.*;
import Services.Response.*;
import database.DatabaseDriver;

/**
 * A class that performs a clear operation on the database - deletes all tables
 * @author Tucker
 */
public class ClearService implements Service{
	private DatabaseDriver database;
	
	public ClearService() {
		database = new DatabaseDriver(DatabaseDriver.databaseName);
	}
	
	/**
	 * Deletes all the tables from the database
	 */
	public Response fillService(Input data) {//don't need any input data
		ClearResponse result = new ClearResponse("Clear succceeded");
		try {
			database.clear();
			database.initilize();
		}
		catch (Exception e) {
			result = new ClearResponse(e.getMessage());
		}
		return result;
	}
	
	/**a test method
	 * @param args
	 */
	public static void main(String[] args) {
		ClearService test = new ClearService();
		ClearResponse val = (ClearResponse) test.fillService(null);
		System.out.print(val.getMessage());
	}

}
