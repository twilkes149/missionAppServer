package Handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Services.LoadService;
import Services.Input.LoadInput;
import Services.Response.LoadResponse;
import database.DatabaseDriver;
import database.Setup;

public class LoadHandler implements HttpHandler {
	private Gson gson;
	private LoadService service;
	private String databaseName;
	
	public LoadHandler(String name) {
		gson = new Gson();
		service = new LoadService();
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		LoadResponse result;
		try {
			InputStreamReader inputStream = new InputStreamReader(arg0.getRequestBody());
			LoadInput input = (LoadInput) gson.fromJson(inputStream, LoadInput.class);
			System.out.println("loading");
			result = (LoadResponse) service.fillService(input);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			result = new LoadResponse("Unknown error occured");
		}
		PrintWriter writer = new PrintWriter(arg0.getResponseBody());
		gson.toJson(result, writer);
		Setup.updateCount();
		writer.close();
		
	}

}
