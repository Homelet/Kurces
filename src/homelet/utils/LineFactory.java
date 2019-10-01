package homelet.utils;

import homelet.utils.Border.BorderPosition;
import homelet.utils.Line.Solid;

import java.awt.*;

public class LineFactory{
	
	public static Line createSolidLine(int thickness, Color color){
		return new Solid(thickness, color);
	}
}
