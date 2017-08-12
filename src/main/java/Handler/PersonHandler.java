package Handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import com.google.gson.Gson;
import com.sun.jndi.toolkit.url.Uri;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Services.PersonService;
import Services.Input.PersonInput;
import Services.Response.PersonResponse;
import database.AuthTokenDAO;
import database.DatabaseDriver;
import database.Setup;
import database.UserDAO;
import serverModel.AuthToken;
import serverModel.User;
import sun.net.www.protocol.http.HttpURLConnection;

public class PersonHandler implements HttpHandler {

	private String databaseName;
	public PersonHandler(String name) {
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}
	
	private PersonResponse getPersons(PersonInput input) {
		PersonService service = new PersonService(databaseName);
		 
		PersonResponse result = (PersonResponse) service.fillService(input); 
		Setup.updateCount();
		return result;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Gson gson = new Gson();
		UserDAO userDriver = new UserDAO(databaseName);
		AuthTokenDAO authTokenDriver = new AuthTokenDAO(databaseName);
		PrintWriter writer = new PrintWriter(arg0.getResponseBody());
		
		System.out.println("Getting persons");
		PersonResponse result = null;
		try {
			arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			
			Headers head = arg0.getRequestHeaders();
			
			
			String code = head.getFirst("authorization");
			System.out.println("AuthCode: " + code);
			AuthToken authToken = authTokenDriver.getAuthTokenValue(code);
			
			//need to uncomment following line to check authToken validity
			if (authToken != null /*&& authToken.isValid()*/) {
				//getting the user
				User user = userDriver.getUserID(authToken.getUserID());
				String userName = user.getUserName();
				String userID = authToken.getUserID();
				
				URI uri = arg0.getRequestURI();
				String path = uri.getPath();
				String[] parts = path.split("/");
				PersonInput input;
				System.out.println("UserName: " + userName);
				
				System.out.println(parts.length);
				if (parts.length >= 3)
					input = new PersonInput(userID, parts[2]);
				else {
					input = new PersonInput(userID);
					System.out.println("got here");
				}
				result = this.getPersons(input);//getting the persons
			}
			else {
				result = new PersonResponse("Invalid authToken");
				System.out.println("Invalid authToken");
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.err.println("Error getting persons");
			result = new PersonResponse("Unknown error occured");
		}
		writer.write(gson.toJson(result));
		writer.close();
		
		
		//gson.toJson(result, writer);
		//writer.close();
	}

}
