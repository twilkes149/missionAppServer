package Services.Input;

/**
 * A string representation of a person
 * @author Tucker
 *
 */
public class LoadPerson {
	
	protected String firstName;
	protected String lastName;
	protected char gender;
	protected String mother;
	protected String father;
	protected String spouse;
	protected String descendant;
	protected String personID;

	public LoadPerson(String firstName, String lastName, char gender, String mother, String father, String spouse, String descendant) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.mother = mother;
		this.father = father;
		this.spouse = spouse;
		this.descendant = descendant;
	}
	
	public LoadPerson(String id, String firstName, String lastName, char gender, String mother, String father, String spouse, String descendant) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.mother = mother;
		this.father = father;
		this.spouse = spouse;
		this.descendant = descendant;
		this.personID = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getMother() {
		return mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public String getDescendant() {
		return descendant;
	}

	public void setDescendant(String descendant) {
		this.descendant = descendant;
	}

	public String getId() {
		return personID;
	}

	public void setId(String id) {
		this.personID = id;
	}
}
