package Services.ReadJson;

import serverModel.Location;

public class Locations {
	private Location data[];
	
	public Location get(int index) {
		data[index].setEventID("" + index);
		return data[index];
	}
	
	public int size() {
		return data.length;
	}
	
	public String toString() {
		String result = "null";
		for (int i = 0; i < data.length; i++)
			result += this.get(i).toString() + "\n";
		return result;
	}
}
