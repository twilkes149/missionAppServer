package Services;
import java.util.ArrayList;

import Services.Input.*;
import Services.Response.*;
import database.EventDAO;
import database.PersonDAO;
import serverModel.Person;
/**
 * A class that implements the person service. If an ID number for a person is supplied, that person's
 * information is returned. Otherwise a list of persons belonging to the current user's family is returned
 */
public class PersonService implements Service {
	private PersonDAO personDriver;
	
	public PersonService() {
		personDriver = new PersonDAO("familyHistory");
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
		
		ArrayList<PResponse> list = null;
		list = personDriver.getPersonsFromUser(input.getUserName());
		
		if (input.getPersonID() != null)
			for (int i = 0; i < list.size(); i++)
				if (!list.get(i).getPersonId().equals(input.getPersonID())) {
					list.remove(i);
					i--;
				}
		if (list.size() <= 0)
			response = new PersonResponse("Error getting persons: there were no persons for that user");
		
		response.setList(list);
		return response;
	}
	
	/**
	 * A simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		PersonService service = new PersonService();
		
		PersonResponse result = (PersonResponse) service.fillService(new PersonInput("twilkes","8"));
		System.out.print(result);
	}

}
