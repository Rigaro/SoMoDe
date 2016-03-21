import java.util.HashMap;

public class EffectLibrary {
	private HashMap<String,Effect> EFFECTS;
	
	public void initialise(){
		Effect effect = new GrayScaleEffect();
		registerEffect("grayScale",effect);
	}
	public boolean registerEffect(String name, Effect effect){
		return true;
	}
	public boolean deregisterEffect(String name){
		return true;
	}
	public Effect getEffect(String name){
		return new GrayScaleEffect();
	}
	public String[] availableEffects(){
		String effect[] = new String[1];
		effect[0] = "GrayScale";
		return effect;
	}
}
