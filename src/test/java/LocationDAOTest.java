package database;

import static org.junit.Assert.*;

import org.junit.Test;

import serverModel.Location;

public class LocationDAOTest {

	@Test
	public void testSave() {
		LocationDAO locationDriver = new LocationDAO("test");
		try {
			assertTrue(locationDriver.saveLocation(new Location("e45th","4", "6.4","Wichita", "USA")));
			try {
				//false because location data is missing
				assertFalse(locationDriver.saveLocation(new Location("44")));
			}
			catch (Exception e) {
				assertTrue(true);
			}
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetting() {
		LocationDAO locationDriver = new LocationDAO("test");
		try {
			//saving
			assertTrue(locationDriver.saveLocation(new Location("e45th","4.0", "6.4","Wichita", "USA")));
			
			Location location = locationDriver.getLocation("e45th");
			assertTrue(location != null);
			assertEquals("4.0", location.getLongitude());
			assertEquals("6.4", location.getLatitude());
			assertEquals("Wichita", location.getCity());
			assertEquals("USA", location.getCountry());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
