package Handler;

import java.io.IOException;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import Services.ClearService;
import Services.Response.ClearResponse;
import database.DatabaseDriver;
import database.Setup;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

public class ClearHandler implements HttpHandler{
	private String databaseName;
	public ClearHandler(String name) {
		databaseName = name;
		DatabaseDriver.databaseName = name;
	}
	
	Gson gson = new Gson();
	/**
	 * Drops all tables and recreates them
	 * @return a message detailing a success or failure
	 */
	public ClearResponse clear() {
		ClearService service = new ClearService();
		
		ClearResponse result = (ClearResponse) service.fillService(null);
		System.out.println("Clearing database");
		try {
			Setup.createTables(Setup.initialize(databaseName));
		}
		catch (Exception e){
			result.setMessage(result.getMessage() + "\n" + e.getMessage());
		}
		return result;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		ClearResponse result = this.clear();
		arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		
		// 0 means the response body has an unknown amount of stuff in it
		PrintWriter printWriter = new PrintWriter(arg0.getResponseBody());
		
		gson.toJson(result, printWriter);

		printWriter.close();
	}
}
