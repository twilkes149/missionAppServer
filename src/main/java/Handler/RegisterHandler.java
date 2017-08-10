package Handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.Headers;
import Services.RegisterService;
import Services.Input.RegisterInput;
import Services.Response.RegisterResponse;
import database.DatabaseDriver;
import database.Setup;

public class RegisterHandler implements HttpHandler{
	private Gson gson = new Gson();
	
	private String databaseName;
	
	public RegisterHandler(String name) {
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}
	
	public RegisterResponse register(RegisterInput input) throws Exception{
		RegisterService service = new RegisterService(databaseName);
		System.out.println("Registering user" + input);
		RegisterResponse result = 
				(RegisterResponse) service.fillService(input);
		Setup.updateCount();
		return result;
	}
	

	@Override
	public void handle(HttpExchange arg0) {
		//Headers headers = arg0.getRequestHeaders();
		PrintWriter printWriter = new PrintWriter(arg0.getResponseBody());
		RegisterResponse result = null;
		try {
			//telling the client that there is stuff to read
			arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	
			//getting the input for the register
			InputStreamReader inputReader = new InputStreamReader(arg0.getRequestBody());
			RegisterInput input = (RegisterInput) gson.fromJson(inputReader, RegisterInput.class);
			inputReader.close();
			
			System.out.println("User: " + input);
			
			result = this.register(input);//registers the user
		}
		catch (IOException e) {
			result = new RegisterResponse("Unknown error occured: " + e.getMessage());
		}
		catch (Exception e) {
			System.err.println("Error registering user");
			System.err.println(e.getMessage());
			result = new RegisterResponse("Error: " + e.getMessage());
			e.printStackTrace();
		}
		gson.toJson(result, printWriter);
		printWriter.close();
		
	}
	
	public static void main(String[] args) {
		RegisterHandler val = new RegisterHandler("familyHistory");
		try {
			
			val.register(new RegisterInput("twilkes", "password"));
			val.register(new RegisterInput("jhwood", "password123"));
			
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
