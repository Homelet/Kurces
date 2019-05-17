package homelet.visual.interfaces;

import java.awt.*;

public interface Renderable{
	
	void render(Graphics2D g);
	
	default boolean isRendering(){
		return true;
	}
}
