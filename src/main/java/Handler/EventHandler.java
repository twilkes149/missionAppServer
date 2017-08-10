package Handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Services.EventService;
import Services.Input.EventInput;
import Services.Response.EventResponse;
import Services.Response.PersonResponse;
import database.AuthTokenDAO;
import database.DatabaseDriver;
import database.Setup;
import database.UserDAO;
import serverModel.AuthToken;
import serverModel.Event;
import serverModel.User;

public class EventHandler implements HttpHandler {
	
	private String databaseName;
	public EventHandler(String name) {
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}

	private EventResponse getEvents(EventInput input) {
		EventService service = new EventService();
		EventResponse result = (EventResponse) service.fillService(input);
		Setup.updateCount();
		return result;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Gson gson = new Gson();
		UserDAO userDriver = new UserDAO(databaseName);
		AuthTokenDAO authTokenDriver = new AuthTokenDAO(databaseName);
		PrintWriter writer = new PrintWriter(arg0.getResponseBody());
		
		EventResponse result = null;
		try {
			arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			Headers head = arg0.getRequestHeaders();
			String code = head.getFirst("authorization");
			
			System.out.println("AuthCode: " + code);
			AuthToken authToken = authTokenDriver.getAuthTokenValue(code);
			
			if (authToken != null /*&& authToken.isValid()*/) {
				//getting the user
				User user = userDriver.getUserID(authToken.getUserID());
				
				String userName = user.getUserName();
				URI uri = arg0.getRequestURI();
				
				//getting a possible personID
				String path = uri.getPath();
				String parts[] = path.split("/");
				EventInput input = null;
				
				System.out.println("parts length: " + parts.length);
				if (parts.length >= 3) {
					input = new EventInput(userName, parts[2]);
					System.out.println("getting event id: " + parts[2]);
				}
				else
					input = new EventInput(userName);
				
				result = this.getEvents(input);
			}
			else {
				result = new EventResponse("Invalid authToken");
				System.out.println("Invalid authToken");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			result = new EventResponse("Unknown error occured");
		}
		gson.toJson(result, writer);
		writer.close();
	}

}
