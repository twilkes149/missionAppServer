package database;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import serverModel.User;

public class UserDAOTest {
	private UserDAO userDriver = new UserDAO("test");
	
	@Before
	public void init() {
		try {
			userDriver.clear();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSaving() {
		try {
			assertTrue(userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com")));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleting() {
		try {
			assertTrue(userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com")));
			assertTrue(userDriver.deleteUser("twilkes"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetUserByID() {
		try {
			assertTrue(userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com")));
			User user = userDriver.getUserID("1");
			assertTrue(user != null);
			assertEquals("Tucker", user.getFirstName());
			assertEquals("Wilkes", user.getLastName());
			assertEquals("password", user.getPassword());
			assertEquals("email.com", user.getEmail());
			assertEquals("twilkes", user.getUserName());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetUserByUsername() {
		try {
			assertTrue(userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com")));
			User user = userDriver.getUser("twilkes");
			assertTrue(user != null);
			assertEquals("Tucker", user.getFirstName());
			assertEquals("Wilkes", user.getLastName());
			assertEquals("password", user.getPassword());
			assertEquals("email.com", user.getEmail());
			assertEquals("twilkes", user.getUserName());
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void addSameUser() {
		try {
			assertTrue(userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com")));
			try {
				userDriver.saveUser(new User("Tucker", "Wilkes", 'm', "twilkes", "password", "email.com"));
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
	public void deleteNonExistantUser() {
		try {
			assertFalse(userDriver.deleteUser("twilkes"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
