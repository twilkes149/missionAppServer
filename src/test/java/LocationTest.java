package serverModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {

	@Test
	public void test() {
		Location location = new Location("1", "3.3", "6.7", "Wichita", "USA");
		
		assertEquals("1", location.getEventID());
		assertEquals("3.3", location.getLongitude());
		assertEquals("6.7", location.getLatitude());
		assertEquals("Wichita", location.getCity());
		assertEquals("USA", location.getCountry());
		
		location.setCity("Las Vegas");
		location.setCountry("Germany");
		location.setLongitude("5");
		location.setLatitude("89");
		location.setEventID("903");
		
		//chaning fields
		assertEquals("903", location.getEventID());
		assertEquals("5", location.getLongitude());
		assertEquals("89", location.getLatitude());
		assertEquals("Las Vegas", location.getCity());
		assertEquals("Germany", location.getCountry());
	}

}
