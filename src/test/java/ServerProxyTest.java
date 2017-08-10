package serverProxy;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Services.Input.LoadEvent;
import Services.Input.LoadPerson;
import Services.Response.ClearResponse;
import Services.Response.EResponse;
import Services.Response.EventResponse;
import Services.Response.FillResponse;
import Services.Response.LoadResponse;
import Services.Response.LoginResponse;
import Services.Response.PResponse;
import Services.Response.PersonResponse;
import Services.Response.RegisterResponse;
import serverModel.User;

public class ServerProxyTest {

	
	@Before
	public void init() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ClearResponse result = proxy.clear();
		assertTrue(result != null);
		
		
	}
	
	@Test
	public void testClear() {
                
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ClearResponse result = proxy.clear();
		System.out.println(result);
		assertTrue(result != null);
		assertEquals("Clear succceeded", result.toString());
	}
	
	@Test
	public void testRegister() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		RegisterResponse result = proxy.register("twilkes", "password", "email.com", "Tucker", "Wilkes", 'm');
		
		assertTrue(result != null);
		assertFalse(result.getError());
		assertEquals("twilkes", result.getUserName());
		assertEquals("1",result.getID());
	}
	
	@Test
	public void testLogin() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		proxy.register("twilkes", "password", "email.com", "Tucker", "Wilkes", 'm');
		LoginResponse result = proxy.login("twilkes", "password");
		assertTrue(result != null);
		assertFalse(result.isError());
		assertEquals("twilkes", result.getUserName());
	}
	
	@Test
	public void testDefaultFill() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		
		proxy.register("twilkes", "password", "email.com", "Tucker", "Wilkes", 'm');
		
		FillResponse result = proxy.fill("twilkes");
		assertTrue(result != null);
		
		//result = proxy.fill("twilkes", 2);
		//assertTrue(result != null);
	}
	
	@Test
	public void testFill() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		
		proxy.register("twilkes", "password", "email.com", "Tucker", "Wilkes", 'm');
		
		FillResponse result = proxy.fill("twilkes", 2);
		assertTrue(result != null);
	}
	
	@Test
	public void testLoad() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ArrayList<LoadEvent> events = new ArrayList<LoadEvent>();
		ArrayList<LoadPerson> persons = new ArrayList<LoadPerson>();
		ArrayList<User> users = new ArrayList<User>();
		
		events.add(new LoadEvent("Koray", "1", "1", "3.4", "7.65", "USA", "Provo", 2017, "birth"));
		persons.add(new LoadPerson("Tucker", "Wilkes", 'm',"","","","Koray"));
		users.add(new User("Koray", "Wilkes", 'm', "kwilkes", "password", "email.com"));
		
		LoadResponse result = proxy.load(events, persons, users);
		assertTrue(result != null);
		assertEquals("Successfully added 1 users, 1 persons, and 1 events to the database.", result.toString());
		assertFalse(result.getError());
	}
	
	@Test
	public void testEvent() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ArrayList<LoadEvent> events = new ArrayList<LoadEvent>();
		ArrayList<User> users = new ArrayList<User>();
		
		events.add(new LoadEvent("Koray", "1", "1", "3.4", "7.65", "USA", "Provo", 2017, "birth"));
		events.add(new LoadEvent("Koray", "2", "1", "5.4", "-93", "Germany", "Berlin", 1991, "death"));
		events.add(new LoadEvent("Koray", "3", "1", "2.34", "234", "Russia", "Moskow", 1223, "death"));
		users.add(new User("Koray", "Wilkes", 'm', "kwilkes", "password", "email.com"));
		User user = users.get(0);
		
		String authToken = user.getAuthToken().getToken();
		LoadResponse loadResult = proxy.load(events, null, users);
		
		EventResponse eventResult = proxy.event(authToken);
		assertTrue(eventResult != null);
		ArrayList<EResponse> list = eventResult.getList();
		assertTrue(list != null);
		assertEquals(3, list.size());
	}
	
	@Test
	public void testEventByID() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ArrayList<LoadEvent> events = new ArrayList<LoadEvent>();
		ArrayList<User> users = new ArrayList<User>();
		
		events.add(new LoadEvent("Koray", "1", "1", "3.4", "7.65", "USA", "Provo", 2017, "birth"));
		events.add(new LoadEvent("Koray", "2", "1", "5.4", "-93", "Germany", "Berlin", 1991, "death"));
		events.add(new LoadEvent("Koray", "3", "1", "2.34", "234", "Russia", "Moskow", 1223, "death"));
		users.add(new User("Koray", "Wilkes", 'm', "kwilkes", "password", "email.com"));
		User user = users.get(0);
		
		String authToken = user.getAuthToken().getToken();
		LoadResponse loadResult = proxy.load(events, null, users);
		
		EventResponse eventResult = proxy.event(authToken,"2");
		assertTrue(eventResult != null);
		ArrayList<EResponse> list = eventResult.getList();
		assertTrue(list != null);
		assertEquals(1, list.size());
		assertEquals("2", list.get(0).getId());
	}
	
	@Test
	public void testPerson() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ArrayList<LoadPerson> persons = new ArrayList<LoadPerson>();
		ArrayList<User> users = new ArrayList<User>();
		
		persons.add(new LoadPerson("Tucker", "Wilkes", 'm',"","","","Koray"));
		persons.add(new LoadPerson("Porter", "Wilkes", 'm',"","","","Koray"));
		persons.add(new LoadPerson("Christy", "Wilkes", 'm',"","","","Koray"));
		users.add(new User("Koray", "Wilkes", 'm', "kwilkes", "password", "email.com"));
		
		User user = users.get(0);
		String authToken = user.getAuthToken().getToken();
		
		proxy.load(null, persons, users);
		
		PersonResponse personResult = proxy.person(authToken);
		assertTrue(personResult != null);
		ArrayList<PResponse> list = personResult.getList();
		assertEquals(3, list.size());
	}
	
	@Test
	public void testPersonByID() {
		ServerProxy proxy = new ServerProxy();
		ServerProxy.setHost(System.getProperty("host"));
		ServerProxy.setPort(System.getProperty("port"));
		
		ArrayList<LoadPerson> persons = new ArrayList<LoadPerson>();
		ArrayList<User> users = new ArrayList<User>();
		
		persons.add(new LoadPerson("1", "Tucker", "Wilkes", 'm',"","","","Koray"));
		persons.add(new LoadPerson("2", "Porter", "Wilkes", 'm',"","","","Koray"));
		persons.add(new LoadPerson("3", "Christy", "Wilkes", 'm',"","","","Koray"));
		users.add(new User("Koray", "Wilkes", 'm', "kwilkes", "password", "email.com"));
		
		User user = users.get(0);
		String authToken = user.getAuthToken().getToken();
		
		proxy.load(null, persons, users);
		
		PersonResponse personResult = proxy.person(authToken,"2");
		assertTrue(personResult != null);
		ArrayList<PResponse> list = personResult.getList();
		assertEquals(1, list.size());
		assertEquals("2", list.get(0).getPersonId());
	}

}
