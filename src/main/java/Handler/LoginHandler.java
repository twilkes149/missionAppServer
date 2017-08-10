package Handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Services.LoginService;
import Services.Input.LoginInput;
import Services.Response.LoginResponse;
import database.DatabaseDriver;
import database.Setup;

public class LoginHandler implements HttpHandler{

	private String databaseName;
	
	public LoginHandler(String name) {
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}
	
	public LoginResponse login(LoginInput input) throws Exception{
		LoginService service = new LoginService(databaseName);

		LoginResponse result = (LoginResponse) service.fillService(input);
		Setup.updateCount();
		return result;
	}
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Gson gson = new Gson();
		LoginResponse result = new LoginResponse();
		PrintWriter writer = new PrintWriter(arg0.getResponseBody());
		try {
			arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
			InputStreamReader reader = new InputStreamReader(arg0.getRequestBody());
			LoginInput input = gson.fromJson(reader, LoginInput.class);
			reader.close();
			
			result = this.login(input);//logs the user in
			
			
		}
		catch (Exception e) {
			result.setError();
			result.setMessage("Unknown error occured");
		}
		gson.toJson(result, writer);
		writer.close();
		
	}
	
	public static void main(String[] args) {
		LoginHandler val = new LoginHandler("testDatabase");
		try {
			LoginResponse result = val.login(new LoginInput("twilkes", "password"));
			System.out.println(result);
		}
		catch (Exception e) {
			System.err.println("Error loging in");
			System.err.println(e.getMessage());
		}
	}

}
