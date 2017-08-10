package Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class DefaultHandler implements HttpHandler {
	private static final String PATH = "src/data/HTML";
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		
		URI uri = arg0.getRequestURI();
		String path = uri.getPath();
		
		byte[] result = new byte[0];
		//String[] parts = path.split("/");
		System.out.println("Path: " + path);
		
		switch (path) {
			case "/":
				result = getBytesFromFile("/index.html");
			break;
			
			case "/css/main.css":
				result = getBytesFromFile("/css/main.css");
			break;
			
			case "/favicon.ico":
				result = getBytesFromFile("/favicon.ico");
			break;
			
			default:
				result = getBytesFromFile("/404.html");
			break;
		}
		
		arg0.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		OutputStream os = arg0.getResponseBody();
		os.write(result);
		os.close();
	}
	
	private byte[] getBytesFromFile(String fileName) {
		Path path = Paths.get(PATH + fileName);
		try {
			return Files.readAllBytes(path);
		}
		catch (IOException e) {
			System.err.println("Error reading the file");
			System.err.println(e.getMessage());
			return null;
		}
	}

}
