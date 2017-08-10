package Services.ReadJson;

public class Names {
	private String[] data;
	
	public String[] getData() {
		return data;
	}
	
	public String toString() {
		String result = null;
		for (int i = 0; i < data.length; i++)
			result += data[i] + "\n";
		return result;
	}
	
	public int size() {
		return data.length;
	}
	
	public String get(int index) {
		return data[index];
	}
}
