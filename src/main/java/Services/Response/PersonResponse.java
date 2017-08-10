package Services.Response;
import java.util.ArrayList;
/**
 * A class that contains an array of PResponse objects, which represent persons in the database
 * @author Tucker
 *
 */
public class PersonResponse /*extends PResponse*/ extends Response{
	private ArrayList<PResponse> list;
	private boolean error;
	private String message;
	
	public PersonResponse() {
		list = new ArrayList<PResponse>();
	}
	
	public PersonResponse(String message) {
		this.error = true;
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setError() {
		error = true;
	}

	public boolean getError() {
		return error;
	}

	/**
	 * Returns a string representation of this object, or if an error occured, a description of the error
	 */
	public String toString(){
		if (error)
			return message;
		else
			return list.toString();
	}
	
	/**
	 * @param index the index of the person response to get
	 * @return returns a PResponse object, which contains all the details for a person in the database
	 */
	public PResponse get(int index) {
		if (index < list.size())
			return list.get(index);
		else
			return null;
	}
	
	/**
	 * Returns how many people were contained in its list
	 * @return the amount of person's contained in a list
	 */
	public int size() {
		return list.size();
	}
	
	public void setList(ArrayList<PResponse> val) {
		this.list = val;
	}
	
	public ArrayList<PResponse> getList() {
		return list;
	}

}
