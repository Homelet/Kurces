package homelet.visual.interfaces;

import homelet.visual.RenderManager;

import java.awt.*;
import java.awt.RenderingHints.Key;

public interface Interactable{
	
	void addKeyInteraction(Object o);
	
	void removeKeyInteraction(Object o);
	
	void addMouseInteraction(Object o);
	
	void removeMouseInteraction(Object o);
	
	void refresh();
	
	void startRendering();
	
	void stopRendering();
	
	void FPS(int FPS);
	
	int FPS();
}
