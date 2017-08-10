package serverProxy;
import java.util.ArrayList;

import Client.ClientCommunicator;
import Services.*;
import Services.Input.*;
import Services.Response.*;
import serverModel.Person;
import serverModel.User;
/**
 * A class that will run on the client to get information needed to perform services. Then it will send the appropriate information to the server and wait for a response
 * @author Tucker
 *
 */
public class ServerProxy {

	
	/**
	 * The register API: Accepts data necessary to register a user for an account and sends its information to the client communicator<br>
	 * returns a RegisterResponse object, which contains the data from a response<br>
	 * @param userName the userName for the user<br>
	 * @param password the password for the user<br>
	 * @param email the email for the user<br>
	 * @param firstName the first name for the user<br>
	 * @param lastName the last name for the user<br>
	 * @param gender the gender for the person<br>
	 * @return
	 */
	public RegisterResponse register(String userName, String password) {
		
		RegisterInput input = new RegisterInput(userName, password);
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_REGISTER, "", 
				ClientCommunicator.HTTP_POST, RegisterResponse.class, input);
		
		if (result instanceof RegisterResponse)
			return (RegisterResponse) result;
		else 
			return null;
	}

	/**
	 * The login API: Accepts data necessary to log a user into the service. Sends information to the client communicator and returns a LoginResponse object<br>
	 * containing the authToken, userName, and id<br>
	 * @param userName the userName for the user<br>
	 * @param password the password for the user<br>
	 * @return returns an authToken, userName and id<br>
	 */
	public LoginResponse login(String userName, String password) {
		LoginInput input = new LoginInput(userName, password);
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_LOGIN, "", 
				ClientCommunicator.HTTP_POST, LoginResponse.class, input);
		if (result instanceof LoginResponse)
			return (LoginResponse) result;
		else
			return null;
		
	}
	
	/**
	 * The API for the clear command, returns a ClearResponse object which details what was cleared and how many<br>
	 * @return ClearResponse object (how many of what was cleared)<br>
	 */
	public ClearResponse clear() {
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_CLEAR, "", "", ClearResponse.class);
		if (result instanceof ClearResponse)
			return (ClearResponse) result;
		return null;
	}
	
	/**
	 * The Fill API for creating a specific amount of generations of persons for a specific user<br>
	 * @param userName the userName of the user to fill<br>
	 * @param generations how many generations to fill<br>
	 * @return FillResponse (how many people and events were created)<br>
	 */
	public FillResponse fill(String userName, int generations) {
		FillInput input = new FillInput(userName);
		input.setGenerations(generations);
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_FILL + userName + "/" + generations, "", 
				ClientCommunicator.HTTP_POST, FillResponse.class, input);
		if (result instanceof FillResponse)
			return (FillResponse) result;
		else
			return null;
	}
	
	/**
	 * The Fill API - Fills the user with 4 generations, which will result in 31 new people being created<br>
	 * @param userName the userName of the person to fill<br>
	 * @return FillResponse (how many people and events were created)<br>
	 */
	public FillResponse fill(String userName) {
		FillInput input = new FillInput(userName);

		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_FILL + userName, "", 
				ClientCommunicator.HTTP_POST, FillResponse.class, input);
		if (result instanceof FillResponse)
			return (FillResponse) result;
		else
			return null;
	}
	
	/**
	 * The person API for getting a specific person from the server<br>
	 * @param personID the id of the person to get<br>
	 * @param authToken an authToken asserting that the person has registered and logged in<br>
	 * @return PersonResponse - an object representing person<br>
	 */
	public PersonResponse person(String authToken, String personID) {
		PersonInput input = new PersonInput(personID, authToken);
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_PERSON + "/" + personID, authToken, ClientCommunicator.HTTP_GET, PersonResponse.class, input);
		if (result instanceof PersonResponse)
			return (PersonResponse) result;
		else
			return null;
	}
	
	/**
	 * The person API for getting all persons belonging to a specific user<br>
	 * @param authToken an authToken asserting that the person has registered and logged in<br>
	 * @return PersonResponse - an object representing person<br>
	 */
	public PersonResponse person(String authToken) {
		PersonInput input = new PersonInput(authToken);
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_PERSON, authToken, 
				ClientCommunicator.HTTP_GET, PersonResponse.class, input);
		if (result instanceof PersonResponse)
			return (PersonResponse) result;
		else
			return null;
	}
	
	/**
	 * The person API for getting a specific event from the server<br>
	 * @param eventID the id of the event to get<br>
	 * @param authToken an authToken asserting that the person has registered and logged in<br>
	 * @return EventResponse - an object representing an event(s)<br>
	 */
	public EventResponse event(String authToken, String eventID) {
		EventInput input = new EventInput(eventID);
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_EVENT + "/" + eventID, authToken,
				ClientCommunicator.HTTP_GET, EventResponse.class, input);
		if (result instanceof EventResponse)
			return (EventResponse) result;
		else
			return null;
	}
	
	/**
	 * The person API for getting all events belonging to a specific user<br>
	 * @param authToken an authToken asserting that the person has registered and logged in<br>
	 * @return PersonResponse - an object representing an event(s)<br>
	 */
	public EventResponse event(String authToken) {
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_EVENT, authToken,
				ClientCommunicator.HTTP_GET, EventResponse.class);
		if (result instanceof EventResponse)
			return (EventResponse) result;
		else
			return null;
	}
	
	/**
	 * The load API for loading a list of events, persons, and users to the database
	 * @param events list of events
	 * 
	 * @return
	 */
	public LoadResponse load(ArrayList<LoadEvent> events, ArrayList<LoadPerson> persons, 
			ArrayList<User> users) {
		
		LoadInput input = new LoadInput();
		input.setEvents(events);
		input.setUsers(users);
		input.setPersons(persons);
		
		Object result = ClientCommunicator.SINGLETON.send(ClientCommunicator.COMMAND_LOAD,"", 
				ClientCommunicator.HTTP_POST, LoadResponse.class, input);
		
		if (result instanceof LoadResponse)
			return (LoadResponse) result;
		else
			return null;
	}
	
	public static void setPort(String port) {
            ClientCommunicator.setPort(port);
	}
	
	public static void setHost(String host) {
            ClientCommunicator.setHost(host);
	}
	
	public static void main(String[] args) {
                String port = "8080";
                String host = "localhost";
                if (args.length > 1) {
                    port = args[1];
                    host = args[0];
                }
		
		ClientCommunicator.setHost(host);
		ClientCommunicator.setPort(port);
		
		ServerProxy test = new ServerProxy();
		
		ClearResponse result = test.clear();
		System.out.println(result);
		/*
		RegisterResponse registerResult = test.register("twilkes", "password123", "email.com", "Tucker", "Wilkes", 'm');
		
		System.out.println(registerResult);
		
		LoginResponse loginResult = test.login("twilkes", "password123");
		System.out.println(loginResult);
		
		FillResponse fillResult = test.fill("twilkes",2);
		System.out.println(fillResult);
		*/
		
		
		//PersonResponse personResult = test.person("839", "10");
		//System.out.println("Person result: " + personResult);
		//EventResponse eventResult = test.event("839","4");
		//System.out.println(eventResult);
	}
}
