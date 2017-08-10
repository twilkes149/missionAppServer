package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import serverModel.Event;
import serverModel.Location;
import serverModel.Person;
import serverModel.User;
import java.util.*;
public class EventDAOTest {

	@Before
	public void init() {
		DatabaseDriver driver = new DatabaseDriver("test");
		try {
			driver.clear();
			Setup.initCounts();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSaving() {
		EventDAO eventDriver = new EventDAO("test");
		
		try {
			assertTrue(eventDriver.saveEvent(new Event("birth", null, null, "4.0", "2.0", "Wellington", "USA", 2001)));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test 
	public void testGetting() {
		EventDAO eventDriver = new EventDAO("test");
		
		try {
			//saving the event
			Event event = new Event("birth", null, null, "4.0", "2.0", "Wellington", "USA", 2001);
			System.out.println("Event id**********: " + event.getId());
			assertTrue(eventDriver.saveEvent(event));
			
			//Ids start at 1 and go up
			event = eventDriver.getEvent("1");
			assertTrue(event != null);
			assertEquals("birth", event.getType());
			assertEquals(2001, event.getYear());

			//checking the location
			Location location = event.getLocation();
			assertTrue(location != null);
			assertEquals("4.0", location.getLongitude());
			assertEquals("2.0", location.getLatitude());
			assertEquals("Wellington", location.getCity());
			assertEquals("USA", location.getCountry());
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGettingUserEvents() {
		User user = new User("Tucker", "Wilkes", 'm', "twilkes","password","email.com");
		
		user.addEvent(new Event("birth", null, user, "4.0", "2.0", "Wellington", "USA", 2001));
		
		EventDAO eventDriver = new EventDAO("test");
		UserDAO userDriver = new UserDAO("test");
		
		try {
                        eventDriver.clear();
			userDriver.saveUser(user);
			ArrayList<Event> events = eventDriver.getEventsOfUser(user.getUserId());
			assertTrue(events != null);
			assertEquals(1, events.size());
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGettingPersonEvents() {
		Person person = new Person("Tucker", "Wilkes" ,'m', null, null, null, null);
		Event event = new Event("birth", person, null, "4.0", "2.0", "Wellington", "USA", 2001);
		person.addEvent(event);
		System.out.println("event id: " + event.getOwner().getId());
		PersonDAO personDriver = new PersonDAO("test");
		EventDAO eventDriver = new EventDAO("test");
		
		try {
			assertTrue(personDriver.savePerson(person));
			
			ArrayList<Event> events = eventDriver.getEventsOfPerson(person.getId());
			assertTrue(events != null);
			assertEquals(1, events.size());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
