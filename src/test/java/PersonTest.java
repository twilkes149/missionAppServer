package serverModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testConstructor() {
		Person person = new Person("Tucker", "Wilkes", 'm', null, null, null, null);
		
		assertEquals("Tucker", person.getFirstName());
		assertEquals("Wilkes", person.getLastName());
		assertEquals('m', person.getGender());
		
		assertEquals(null, person.getMother());
		assertEquals(null, person.getFather());
		assertEquals(null, person.getSpouse());
		assertEquals(null, person.getDescendant());
	}
	
	@Test
	public void testParents() {
		//setting up person and father
		Person person = new Person("Tucker", "Wilkes", 'm', null, null, null, null);
		person.setFather(new Person("Koray", "Wilkes", 'm', null, null, null, null));
		person.setMother(new Person("Christy", "Wilkes", 'f', null, null, null, null));
		
		//testing father
		assertEquals("Koray", person.getFather().getFirstName());
		assertEquals("Wilkes", person.getFather().getLastName());

		//testing mother
		assertEquals("Christy", person.getMother().getFirstName());
		assertEquals("Wilkes", person.getMother().getLastName());
	}
	
	@Test
	public void testEvent() {
		Person person = new Person("Tucker", "Wilkes", 'm', null, null, null, null);
		
		person.addEvent("birth", person, null, "34.3", "67.3", "New York", "USA", 2009);
		
		assertEquals(1, person.eventListSize());
		assertEquals("birth", person.getEvent(0).getType());
		//testing that the event owner id, matches the person
		assertEquals(person.getId(), person.getEvent(0).getOwner().getId());
		
		assertEquals("New York", person.getEvent(0).getLocation().getCity());
		assertEquals("USA", person.getEvent(0).getLocation().getCountry());
		
		//clearing the event list
		person.setEventList(null);
		assertEquals(0, person.eventListSize());
		
	}

}
