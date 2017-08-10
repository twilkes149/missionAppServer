package serverModel;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

/**
 * Fields: value, expiration date, and the user id this authToken belongs to
 * @author Tucker
 */
public class AuthToken {
	//private LocalTime value;
	private LocalTime exTime;
	private LocalDate now;
	private String userID;
	private String value;
	
	/** 
	 * @param userID the id of the user this token belongs to
	 */
	public AuthToken(String userID) {
		//value = LocalTime.now();
		exTime = LocalTime.now().plusHours(1);
		now = LocalDate.now();
		this.userID = userID;
		value = this.generateToken();
	}
	
	public AuthToken(String userID, int hour, int min, int day, int month, int year) {
		exTime = LocalTime.of(hour, min);
		this.userID = userID;
		now = LocalDate.of(year, month, day);
		value = this.generateToken();
	}
	
	public String getToken() {
		return value;
	}
	
	public void setToken(String token) {
		this.value = token;
	}
	
	public int getMin() {
		return exTime.getMinute();
	}
	
	public int getHour() {
		return exTime.getHour();
	}
	
	public int getDay() {
		return now.getDayOfMonth();
	}
	
	public int getMonth() {
		return now.getMonthValue();
	}
	
	public int getYear() {
		return now.getYear();
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Checks if this is a valid authToken
	 * @return true if valid, false otherwise
	 */
	public boolean isValid() {
		LocalTime now = LocalTime.now();
		LocalDate dateNow = LocalDate.now();
		
		return now.isBefore(exTime) && dateNow.equals(this.now);
	}
	
	/** 
	 * Returns a string representation of this object
	 */
	public String toString() {
		return "Value: " + value;
	}
	
	/**
	 * Generates a random "token" value for this authToken
	 * @return a string of up to 10 random integers
	 */
	private String generateToken() {	
		return UUID.randomUUID().toString();
	}
	
	/** 
	 * A simple test method
	 * @param args
	 */
	public static void main(String[] args) {
		AuthToken val = new AuthToken("0");
		System.out.println(val.toString());
		System.out.println(val.isValid());
	}
}
