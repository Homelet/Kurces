package homelet.visual.interfaces;

public interface Tickable{
	
	void tick();
	
	default boolean isTicking(){
		return true;
	}
}
