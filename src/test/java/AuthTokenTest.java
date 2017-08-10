package serverModel;

import static org.junit.Assert.*;
import org.junit.*;

import org.junit.Test;


public class AuthTokenTest {

	@Test
	public void test() {
		AuthToken token = new AuthToken("45");
		
		//making sure token is valid
		assertTrue(token.isValid());
		assertEquals("45", token.getUserID());
		
		//creating an expired token to check that it returns invalid
		token = new AuthToken("tu78d", 8, 0, 1, 1, 1970);
		assertFalse(token.isValid());
		
		assertEquals("tu78d", token.getUserID());
	}

}
