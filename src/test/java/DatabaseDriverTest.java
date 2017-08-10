package database;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseDriverTest {

	@Test
	public void test() {
		DatabaseDriver driver = new DatabaseDriver("test");
		
		//creating the tables
		assertTrue(driver.initilize());
		try {
			assertTrue(driver.clear());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
