package Services.Response;

import java.util.ArrayList;

/**
 * Contains information used in the response of event
 * Fields: error flag, message, and a list of events
 * @author Tucker
 *
 */
public class EventResponse extends EResponse{
	private ArrayList<EResponse> list;
	private boolean error;
	private String message;
	
	public EventResponse() {
		list = new ArrayList<EResponse>();
	}
	
	public void setList(ArrayList<EResponse> val) {
		list = val;
	}
	
	public String toString() {
		return list.toString();
	}
	
	public EventResponse(String message) {
		this.message = message;
		error = true;
	}
	
	public void setError() {
		error = true;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean getError() {
		return error;
	}

	/**
	 * Gets an event
	 * @param index index of the event to get
	 * @return an event
	 */
	public EResponse get(int index) {
		if (index < list.size())
			return list.get(index);
		else
			return null;
	}
	
	/**
	 * Returns how many events are in list
	 * @return an int
	 */
	public int size() {
		return list.size();
	}
	
	public ArrayList<EResponse> getList() {
		return list;
	}
}
