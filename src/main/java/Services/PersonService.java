package Services;
import java.util.ArrayList;
import java.util.TreeMap;

import Services.Input.*;
import Services.Response.*;
import database.EventDAO;
import database.FamilyDAO;
import database.FamilyIDDAO;
import database.PersonDAO;
import serverModel.Person;
/**
 * A class that implements the person service. If an ID number for a person is supplied, that person's
 * information is returned. Otherwise a list of persons belonging to the current user's family is returned
 */
public class PersonService implements Service {
	private PersonDAO personDriver;
	private FamilyIDDAO familyIDDriver;
	private String databaseName;
	private FamilyDAO familyDriver;
	
	public PersonService(String name) {
		this.databaseName = name;
		personDriver = new PersonDAO(databaseName);
		familyIDDriver = new FamilyIDDAO(databaseName);
		familyDriver = new FamilyDAO(databaseName);
	}
	
	/**
	 * This method checks if a person id number was supplied. If so, it returns that person's information
	 * Otherwise it returns a list of all the person's (and all info belonging to them) in the current 
	 * user's family
	 * @return a list of persons - list may contain one element which is the current user
	 */
	public Response fillService(Input data) {
		PersonInput input = null;
		PersonResponse response = new PersonResponse();
		if (data instanceof PersonInput)
			input = (PersonInput) data;
		
		if (input == null)
			return new PersonResponse("Input data was invalid");
		
		String userID = input.getUserID();		
		System.out.println("userID: " + userID);
		ArrayList<String> families = familyIDDriver.getFamilies(userID);//getting a list of families the user belongs to
		System.out.println("families:" + families.toString());
				
		TreeMap<String, ArrayList<PResponse>> listOfFamilies = new TreeMap<String, ArrayList<PResponse>>();
		
		for (int i = 0; i < families.size(); i++) {
			
			String familyName = familyDriver.getFamily(families.get(i)).getFamilyName();
			
			listOfFamilies.put(familyName, personDriver.getPersonsFromFamily(families.get(i)));
		}
		//System.out.println("family list:" + listOfFamilies.toString());
		
		response.setList(listOfFamilies);
		
		return response;
	}
	
	/**
	 * A simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		PersonService service = new PersonService("testDatabase");
		
		PersonResponse result = (PersonResponse) service.fillService(new PersonInput("1","8"));
		System.out.print(result);
	}

}
