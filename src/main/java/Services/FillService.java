package Services;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Random;

import com.google.gson.Gson;

import Services.Input.*;
import Services.ReadJson.Locations;
import Services.ReadJson.Names;
import Services.Response.*;
import database.DatabaseDriver;
import database.UserDAO;
import serverModel.Event;
import serverModel.Location;
import serverModel.Person;
import serverModel.User;

/**
 * A class that builds a users family history, populating the database with newly created persons and events
 * @author Tucker
 * 
 */
public class FillService implements Service{
	private UserDAO userDriver;
	private Names maleFirstNames;
	private Names femaleFirstNames;
	private Names lastNames;
	private Locations locations;
	
	private static final char MALE = 'm';
	private static final char FEMALE = 'f';
	private static final char LAST = 'l';
	public static int peopleCreated = 0;
	public static int eventsCreated = 0;
	public static int peopleGenerated;
	
	private User currUser;
	
	/**
	 * Loads all of the json files into lists
	 */
	public FillService() {
		maleFirstNames = this.getNames("mnames.json");
		femaleFirstNames = this.getNames("fnames.json");
		lastNames = this.getNames("snames.json");
		
		locations = this.getLocations();
		peopleGenerated = 0;
		currUser = null;
	}
	
	/**
	 * Gets the location data from json file
	 * @return a list of locations
	 */
	public Locations getLocations() {
		Gson gson = new Gson();
		File file = new File("src/data/json/locations.json");
		
		Locations locationList;
		
		InputStreamReader is;
		try {
			is = new InputStreamReader(new FileInputStream(file));
			locationList = gson.fromJson(is, Locations.class);
			return locationList;
		}
		catch (FileNotFoundException e) {
			System.err.println("Error reading locations");
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Populates the server's database with generated data for the specified user name. The<br>
		optional "generations" parameter lets the caller specify the number of generations of ancestors<br>
		to be generated, and must be a non-negative integer (the default is 4, which results in 31 new<br>
		persons each with associated events).<br>
	 * @param data the Input data for a fill operation (user name, and optionally a number of generations)
	 */
	public Response fillService(Input data) {
		FillInput input = null;
		Random rand = new Random();
		if (data instanceof FillInput) {
			input = (FillInput) data;
			//System.out.println("got here");
		}
		if (input == null)
			return null;
		userDriver = new UserDAO(DatabaseDriver.databaseName);
		FillResponse result = new FillResponse();
		
		String userName = input.getUsername();
		int generations = input.getGenerations();//default of 4 handled on the client side, during creation of FillInput object
		//getting the user from the database
		System.out.println("Filling user: " + userName);
		User user = userDriver.getUser(userName);
		currUser = user;
		
		//deleting the user's current info
		userDriver.deleteUser(user.getUserName());
		user.setEventList(null);//clears the event list
		user.setFather(null);
		user.setMother(null);
		user.setSpouse(null);
		
		//creating the user's new information
		user.addEvent(this.generateEvent(2017 - rand.nextInt(60), "birth"));
		
		//create family tree
		user.setFather(this.fillPerson(new Person(), generations-1, 2017-35, MALE));
		user.setMother(this.fillPerson(new Person(), generations-1, 2017-35, FEMALE));
		user.getFather().setSpouse(user.getMother());
		user.getMother().setSpouse(user.getFather());
		
		//saving the user back to the database
		try {
			userDriver.saveUser(user);
		}
		catch (Exception e) {
			System.err.println("Error filling a user");
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
		result.setEvents(eventsCreated);
		result.setPersons(peopleCreated);
		eventsCreated = 0;
		peopleCreated = 0;
		return result;
	}
	
	/**
	 * Generates a person to add to the database
	 * @param yearRange the year that this person should be close to being born in
	 * @return the information of the person generated
	 */
	public Person generatePerson(char gender, int yearRange) {
		peopleCreated++;
		Person person = new Person();
		Random rand = new Random();
		//setting the person's names
		switch (gender) {
			case MALE:
				person.setFirstName(getName(MALE));
				person.setGender(MALE);
			break;
			
			case FEMALE:
				person.setFirstName(getName(FEMALE));
				person.setGender(FEMALE);
			break;
		}
		person.setLastName(getName(LAST));
		
		//generating events for the person
		person.addEvent(this.generateEvent(yearRange, "birth"));//generates a realistic birth year
		person.addEvent(this.generateEvent(yearRange + 40 + rand.nextInt(60), "death"));//generating a realistic death year
		
		//setting the person's owner to this person
		person.getEvent(0).setOwner(person);
		person.getEvent(1).setOwner(person);
		
		person.setDescendant(currUser);
		
		return person;
	}
	
	/**
	 * A function to get the list of names from the gson files
	 * @param fName the json file name
	 * @return a list of names
	 */
	public Names getNames(String fName) {
		Gson gson = new Gson();
		File file = new File("src/data/json/" + fName);
		
		Names nameList = null;
		InputStreamReader is;
		
		try {
			is = new InputStreamReader(new FileInputStream(file));
			nameList = gson.fromJson(is, Names.class);
			
		}
		catch (FileNotFoundException e) {
			System.err.println("Error reading file");
			System.err.println(e.getMessage());
			return null;
		}
		return nameList;
	}
	
	/**
	 * Returns a random location
	 * @return a location
	 */
	public Location getLocation() {
		Random rand = new Random();
		return locations.get(rand.nextInt(locations.size()));
	}
	
	/**
	 * Returns a male or female first name or a last name depending on type
	 * @param type type of name
	 * @return a name
	 */
	public String getName(char type) {
		Random rand = new Random();
		int index = 0;
		String result = "name";
		switch (type) {
			case MALE:
				index = rand.nextInt(maleFirstNames.size());
				result =  maleFirstNames.get(index);
			break;
			
			case FEMALE:
				index = rand.nextInt(femaleFirstNames.size());
				result =  femaleFirstNames.get(index);
			break;
			
			case LAST:
				index = rand.nextInt(lastNames.size());
				result =  lastNames.get(index);
			break;
		}
		return result;
	}
	
	/**
	 * Generates an event to add to the database
	 * @param year the year for this event to be close to
	 * @return the information of the newly created event
	 */
	public Event generateEvent(int year, String type) {
		eventsCreated++;
		Random rand = new Random();
		Event event = new Event();
		event.setType(type);
		event.setLocation(this.getLocation());
		event.getLocation().setEventID(event.getId());
		event.setYear(year + rand.nextInt(15));
		event.setDescendant(currUser);
		
		return event;
	}
	
	/**
	 * Generates an event to add to the database
	 * @param startYear the minimum year this event can have
	 * @param endYear the maximum year this event can have
	 * @return the information of the newly created event
	 */
	public Event generateEvent(int startYear, int endYear, String type) {
		Random rand = new Random();
		Event event = generateEvent(startYear, type);
		event.getLocation().setEventID(event.getId());
		event.setYear(startYear + rand.nextInt(startYear - endYear));
		event.setDescendant(currUser);
		return event;
	}
	
	/**
	 * A recursive method to generate n generations of ancestors
	 * @param person the current person
	 * @param generationsRemaining how many generations are remaining
	 * @param yearRange the year this person should be close to being born in
	 * @param gender the gender of the person
	 * @return the created person
	 */
	private Person fillPerson(Person person, int generationsRemaining, int yearRange, char gender) {
		peopleGenerated++;
		person = this.generatePerson(gender, yearRange);
		if (person.getFather() != null)
			person.getFather().setLastName(person.getLastName());
		if (generationsRemaining <= 0)
			return person;
		
		person.setFather(this.fillPerson(person.getFather(), generationsRemaining - 1, yearRange - 35, MALE)); //father is at least 20 years older than this person, at most 35
		person.setLastName(person.getFather().getLastName());
		person.setMother(this.fillPerson(person.getMother(), generationsRemaining - 1, yearRange - 35, FEMALE));
		//setting the spouse
		person.getFather().setSpouse(person.getMother());
		person.getMother().setSpouse(person.getFather());
		
		return person;
		
	}
	//TODO when deleting a user, delete the events
	public static void main(String[] args) {
		FillService val = new FillService();
		RegisterService register = new RegisterService();
		DatabaseDriver init = new DatabaseDriver("familyHistory");
		
		try {			
			//userDriver.deleteUser("twilkes");
			init.clear();
			init.initilize();
			register.fillService(new RegisterInput("Tucker", "Wilkes", 'm', "twilkes", "password123", "twilkes@gmail.com"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FillInput input = new FillInput("twilkes");
		input.setGenerations(2);
		val.fillService(input);
		//val.fillService(new FillInput("twilkes"));
	}
}
