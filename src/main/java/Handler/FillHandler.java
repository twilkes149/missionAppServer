package Handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.net.URI;

import Services.FillService;
import Services.Input.FillInput;
import Services.Response.FillResponse;
import database.AuthTokenDAO;
import database.DatabaseDriver;
import database.Setup;
import database.UserDAO;
import serverModel.AuthToken;
import serverModel.Person;
import serverModel.User;
import sun.net.www.protocol.http.HttpURLConnection;

public class FillHandler implements HttpHandler{
	private UserDAO userDriver;
	private Gson gson;
	
	private String databaseName;
	
	public FillHandler(String name) {
		gson = new Gson();
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}
	
	public FillResponse fill(FillInput input) {
		FillService service = new FillService();
		
		FillResponse result = (FillResponse) service.fillService(input);
		Setup.updateCount();
		return result;
	}
	
	
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		AuthTokenDAO authTokenDriver = new AuthTokenDAO(databaseName);
		FillResponse result = new FillResponse();
		PrintWriter writer = new PrintWriter(arg0.getResponseBody());
		try {
			arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			Headers head = arg0.getRequestHeaders();
			String code = head.getFirst("authorization");

			
			AuthToken authToken = authTokenDriver.getAuthToken(code);
			
			//making sure this person has access
			//if (authToken != null && authToken.isValid()) {
				//getting the username
				URI uri = arg0.getRequestURI();
				String path = uri.getPath();
				String[] parts = path.split("/");
				System.out.println("Username:" + parts[2]);
				FillInput input = new FillInput(parts[2]);
				System.out.println("UserName1: " + input.getUsername());
				if (parts.length >= 4) {
					input.setGenerations(Integer.parseInt(parts[3]));
					System.out.println("Filling generations: " + parts[3]);
				}
				
				//calling the service
				result = this.fill(input);
			//}
			/*else {
				System.out.println("invalid token");
				result.setMessage("Invalid authToken");
				result.setFail();
			}*/
			//writing the response
			
		}
		catch (IOException e) {
			System.err.println("Error filling user");
			e.printStackTrace();
			result = new FillResponse("Unknown error occured");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			result = new FillResponse("Unknown error occured");
		}
		gson.toJson(result, writer);
		writer.close();
	}

}
