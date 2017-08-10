package Services.Input;
/**
 * A class that holds the data for the fill service
 * Inherits from Input
 * Fields: generations - the number of generations to fill, default being 4
 * @author Tucker
 *
 */
public class FillInput extends Input{
	private int generations;
	private String username;
	
	public FillInput(String userName) {
		generations = 4;
		username = userName;
	}
	/**
	 * @param gen the number of generations to fill
	 */
	public FillInput(int gen, String userName) {
		generations = gen;
		username = userName;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

}
