package Services.Response;
/**
 * A class that holds the response message for the clear service
 * Inherits from Response
 * Fields: message and an error flag
 * @author Tucker
 *
 */
public class ClearResponse extends Response{
	private String message;
	private boolean error;
	
	public boolean getError() {
		return error;
	}

	public void setError() {
		this.error = true;
	}

	public ClearResponse() {
		message = null;
	}
	
	public String toString() {
		return this.getMessage();
	}
	
	/** 
	 * @param mess the message of the response
	 */
	public ClearResponse(String mess) {
		message = mess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
