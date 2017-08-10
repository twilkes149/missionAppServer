package Handler;
import com.google.gson.Gson;

import Services.Input.*;
public interface ObjectEncodeDecode {
	public Input decode(/*GSON object*/);
	public Gson encode(Input data);

}
