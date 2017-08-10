package Services.Input;
/**
 * A class that contains all of the information needed for an event input<br>
 * Fields: authToken, eventID, userName
 * @author Tucker
 *
 */

public class EventInput extends Input{
	private String authToken;
	private String eventID;
	private String userName;
	
	public EventInput() {
		authToken = null;
		eventID = null;
	}
	
	public EventInput(String userName) {
		this.userName = userName;
		authToken = null;
		eventID = null;
	}
	
	public EventInput(String userName, String eventID) {
		this.userName = userName;
		this.eventID = eventID;
		authToken = null;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getEventID() {
		return eventID;
	}
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
}
