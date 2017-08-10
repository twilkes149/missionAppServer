package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Services.Response.PResponse;
import serverModel.Person;
import serverModel.User;

public class PersonDAOTest {
	PersonDAO personDriver = new PersonDAO("test");
	
	@Before
	public void init() {
		DatabaseDriver database = new DatabaseDriver("test");
		try {
			database.clear();
			Setup.initialize("test");
			//personDriver.clear();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSaving() {
		
		try {
			assertTrue(personDriver.savePerson(new Person("Tucker", "Wilkes", 'm', null, null, null, null)));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetting() {
		
		try {
			//saving the person
			assertTrue(personDriver.savePerson(new Person("Tucker", "Wilkes", 'm', null, null, null, null)));
			
			Person person = personDriver.getPerson("1");
			assertTrue(person != null);
			assertEquals("Tucker", person.getFirstName());
			assertEquals("Wilkes", person.getLastName());
			assertEquals('m', person.getGender());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGettingUserPersons() {
		UserDAO userDriver = new UserDAO("test");
		//building the service
		User user = new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com");
		user.setFather(new Person("Koray", "Wilkes", 'm', null, null, null, user));
		user.setMother(new Person("Christy", "Wilkes", 'm', null, null, null, user));
		
		
		try {
			assertTrue(userDriver.saveUser(user));
			//getting the user's persons
			ArrayList<PResponse> list = personDriver.getPersonsFromUser(user.getUserName());
			assertTrue(list != null);
			assertEquals(2, list.size());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleting() {
		try {
			//saving an event
			assertTrue(personDriver.savePerson(new Person("Tucker", "Wilkes", 'm', null, null, null, null)));
			//deleting the above event
			assertTrue(personDriver.deletePerson("1"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
