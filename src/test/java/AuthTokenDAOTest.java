package database;

import static org.junit.Assert.*;

import org.junit.Test;

import serverModel.AuthToken;

public class AuthTokenDAOTest {
	
	@Test
	public void testSaving() {
		AuthTokenDAO tokenDriver = new AuthTokenDAO("test");
		try {
			//saving an authToken
			assertTrue(tokenDriver.saveAuthToken(new AuthToken("34jl5")));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGettingToken() {
		AuthTokenDAO tokenDriver = new AuthTokenDAO("test");
		
		try {
			//saving an authToken
			tokenDriver.saveAuthToken(new AuthToken("34jl5"));
			
			//getting the token back
			AuthToken token = tokenDriver.getAuthToken("34jl5");
			assertTrue(token != null);
			assertEquals("34jl5",token.getUserID());
			assertTrue(token.isValid());
			String code = token.getToken();
			
			//getting it back a different way
			token = tokenDriver.getAuthTokenValue(code);
			assertTrue(token != null);
			assertEquals("34jl5",token.getUserID());
			assertTrue(token.isValid());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDeleting() {
		AuthTokenDAO tokenDriver = new AuthTokenDAO("test");
		
		try {
			//saving an authToken
			tokenDriver.saveAuthToken(new AuthToken("34jl5"));
			
			assertTrue(tokenDriver.deleteAuthToken("34jl5"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
