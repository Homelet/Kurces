package homelet.utils;

import homelet.utils.Border.*;

import java.awt.*;

public class BorderFactory{
	
	public static Border createSolidBorder(BorderPosition position, Color color, double thickness){
		return new Solid(position, color, thickness);
	}
	
	public static Border createDoubleColorDashedBorder(BorderPosition position, Color color, Color color2, double thickness, double width){
		return new DoubleColorDashed(position, color, color2, thickness, width);
	}
	
	public static Border createDashedBorder(BorderPosition position, Color color, double thickness, double width){
		return new Dashed(position, color, thickness, width);
	}
	
	public static Border createDoubleColorDottedBorder(BorderPosition position, Color color, Color color2, double thickness){
		return new DoubleColorDotted(position, color, color2, thickness);
	}
	
	public static Border createDottedBorder(BorderPosition position, Color color, double thickness){
		return new Dotted(position, color, thickness);
	}
}
