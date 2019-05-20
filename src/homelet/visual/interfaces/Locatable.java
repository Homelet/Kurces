package homelet.visual.interfaces;

import java.awt.geom.Rectangle2D;

public interface Locatable{
	
	/**
	 * Renderable implements this class will be given a clip of {@link java.awt.Graphics2D} when they are
	 * rendered,
	 * Note : Please consider return a value, instead of creating a new object every time, as it will be called every
	 * time when rendered
	 */
	Rectangle2D getBounds();
}
