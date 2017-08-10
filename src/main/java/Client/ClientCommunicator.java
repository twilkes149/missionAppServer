package Client;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import Services.Response.ClearResponse;
import serverModel.Server;

public class ClientCommunicator {
	
	//what handler to use
	public static final String COMMAND_CLEAR = "/clear";
	public static final String COMMAND_REGISTER = "/user/register";
	public static final String COMMAND_LOGIN = "/user/login";
	public static final String COMMAND_FILL = "/fill/";
	public static final String COMMAND_LOAD = "/load";
	public static final String COMMAND_EVENT = "/event";
	public static final String COMMAND_PERSON = "/person";
	
	private static String SERVER_HOST = "localhost";
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + Server.PORT_NUMBER;
	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	public static String PORT = "8080";
	
	public static ClientCommunicator SINGLETON = new ClientCommunicator();

	public ClientCommunicator() {
            //URL_PREFIX = "http://" + SERVER_HOST + ":" + PORT;
	}
	
	public static void setPort(String port) {
            if (port != null)
                PORT = port;
        }
        
        public static void setHost(String host) {
            if (host != null)
                SERVER_HOST = host;
        }
        
		
	public Object send(String designator, String authCode, String method, Class<?> klass) {
		Object response = null;
		
		HttpURLConnection connection =
			openConnection(designator, authCode, method, false);
		
		response = getResult(connection, klass);

		return response;
	}
	
	/**
	 * Sends an object to the server
	 * @param designator the service to use 
	 * @param authCode an authCode to send
	 * @param method the http post or get
	 * @param klass the klass of the expected result
	 * @param objectToSend the object to send to the server
	 * @return
	 */
	public Object send(String designator, String authCode, String method, Class<?> klass, Object objectToSend) {
		Object response = null;
		HttpURLConnection connection =
			openConnection(designator, authCode, method, true);
		
		sendToServer(connection, objectToSend);
		
		response = getResult(connection, klass);

		return response;
	}
	
	private Object getResult(HttpURLConnection connection, Class<?> klass) {
		Object result = new String("Unknown error occured");
		Gson gson = new Gson();
		InputStreamReader reader = null;
		try {
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				
				//0 means the body response length == 0 -> it was empty
				System.out.println("Get content length: " + connection.getContentLength());
				
				if(connection.getContentLength() == 0) {
					System.out.println("Response body was empty");
				} 
				else if(connection.getContentLength() == -1) {
					System.out.println("got something");
					reader = new InputStreamReader(connection.getInputStream());
					
					result = gson.fromJson(reader, klass);
					reader.close();
				}
				else
					result = connection.getContent();//my addition
			} 
			else {
				throw new Exception(String.format("http code %d",	connection.getResponseCode()));
			}
		} catch (JsonSyntaxException | JsonIOException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private HttpURLConnection openConnection(String contextIdentifier, String authCode, String method,
			                                 boolean sendingSomthingToServer)
	{
		HttpURLConnection result = null;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + PORT;
		try {
                    System.out.println("Connecting to server on: " + URL_PREFIX);
                    
			URL url = new URL(URL_PREFIX + contextIdentifier);
			result = (HttpURLConnection) url.openConnection();
			
			if (method.equals(HTTP_POST) || method.equals(HTTP_GET))
				result.setRequestMethod(method);
			result.setDoOutput(sendingSomthingToServer);
			result.setRequestProperty("authorization", authCode);
			result.connect();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		//returns the connection to teh server
		return result;
	}
	
	private void sendToServer(HttpURLConnection connection, Object objectToSend) {
		Gson gson = new Gson();
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(connection.getOutputStream());
			//Encoding in JSON
			gson.toJson(objectToSend, printWriter);
			printWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(printWriter != null) {
				printWriter.close();//sends the object to the server
				System.out.println("Sent object to server");
			}
		}
	}
	
	public static void main(String[] args) {
//		System.out.println(ClientCommunicator.SINGLETON.send().toString());
	}

}
