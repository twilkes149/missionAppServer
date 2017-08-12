package serverModel;

import java.util.UUID;

public class FamilyID {
	private String id;
	private String familyName;
	private static int count;
	
	public String toString() {
		return this.id + ", " + this.familyName;
	}
	
	public FamilyID(String name) {
		this.familyName = name;
		this.id = this.generateID();
		count++;
	}
	
	public FamilyID(String id, String name) {
		this.id = id;
		this.familyName = name;
	}
	
	private String generateID() {
		return UUID.randomUUID().toString();
	}
	
	public static void setCount(int num) {
		count = num;
	}
	
	public static int getCount() {
		return count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
}
