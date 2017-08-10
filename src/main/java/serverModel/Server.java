package serverModel;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Handler.ClearHandler;
import Handler.DefaultHandler;
import Handler.EventHandler;
import Handler.FillHandler;
import Handler.LoadHandler;
import Handler.LoginHandler;
import Handler.PersonHandler;
import Handler.RegisterHandler;
import database.Setup;
//TODO change ids to strings
public class Server {
	public static int PORT_NUMBER;
	public static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	
	private void run(int port) {
                PORT_NUMBER = port;
		try {
			server = HttpServer.create(new InetSocketAddress(PORT_NUMBER),
					MAX_WAITING_CONNECTIONS);
			
			setUpContext();//setup all of the handlers
			server.setExecutor(null);//use the default executor
			Setup buf = new Setup();//to setup things using the constructor
			Setup.createTables(Setup.initialize("familyHistory"));
			Setup.initCounts();
			server.start();//starts the server
			System.out.println("Server is listening on port: " + PORT_NUMBER);
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setUpContext() {
		//server.createContext("/clear", new ClearHandler("familyHistory"));
		//server.createContext("/fill", new FillHandler("familyHistory"));
		//server.createContext("/load", new LoadHandler("familyHistory"));
		server.createContext("/user/login", new LoginHandler("testDatabase"));
		server.createContext("/user/register", new RegisterHandler("testDatabase"));
		//server.createContext("/person", new PersonHandler("familyHistory"));
		//server.createContext("/event", new EventHandler("familyHistory"));
		
		server.createContext("/", new DefaultHandler());
	}
	
	public static void main(String[] args) {
            int port=8080;
            if (args.length > 0)
                port = Integer.parseInt(args[0]);
            new Server().run(port);
	}
}
