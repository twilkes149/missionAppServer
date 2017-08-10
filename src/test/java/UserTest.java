package serverModel;

import static org.junit.Assert.*;

import org.junit.*;

public class UserTest {

	@Test
	public void testConstructor() {
		User user = new User("Tucker", "Wilkes", 'm', "twilkes", "password123", "twilkes@gmail.com");
		
		//testing the constructor
		assertEquals("Tucker", user.getFirstName());
		assertEquals("Wilkes", user.getLastName());
		assertEquals("twilkes", user.getUserName());
		assertEquals("password123", user.getPassword());
		assertEquals("twilkes@gmail.com", user.getEmail());
		assertEquals('m', user.getGender());
		assertTrue(user.getUserId() != null);
		
	}
	
	@Test
	public void testEvent() {
		User user = new User("Tucker", "Wilkes", 'm', "twilkes", "password123", "twilkes@gmail.com");
		
		//testing adding events
		user.addEvent(new Event("birth", null, user, "987.33", "-21.2", "Houston", "USA",2000));
		assertTrue(user.eventListSize() == 1);
		assertEquals("Houston", user.getEvent(0).getLocation().getCity());
		assertEquals("birth", user.getEvent(0).getType());
		assertTrue(user.getEvents() != null);
		
		user.setEventList(null);
		assertEquals(0, user.eventListSize());
	}

}
