package homelet.visual.ContentDrawer;

import java.awt.*;
import java.awt.geom.Dimension2D;

public abstract class Content{
	
	abstract void renderNext(Graphics2D g, ContentInfo);
	
	abstract Dimension2D nextBound(Graphics2D g);
}
