package serverModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventTest {

	@Test
	public void testConstructor() {
		Event event = new Event("death", null, null, "-23.2123", "212.3423", "Wichita", "USA", 1992);
		
		assertEquals("death", event.getType());
		assertTrue(event.getLocation() != null);
		assertTrue(event.getId() != null);
	}
	
	@Test
	public void testGettingLocation() {
		Event event = new Event("death", new Person(), new User(), "-23.2123", "212.3423", "Wichita", "USA", 1992);
		
		Location location = event.getLocation();
		
		assertTrue(location != null);
		assertEquals("-23.2123", location.getLongitude());
		assertEquals("212.3423", location.getLatitude());
		assertEquals("Wichita", location.getCity());
		assertEquals("USA", location.getCountry());
		
		//asserting that the location is linked to the event 
		assertEquals(event.getId(), location.getEventID());
		
		assertTrue(event.getOwner() != null);
		assertTrue(event.getDescendant() != null);
	}

}
