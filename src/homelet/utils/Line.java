package homelet.utils;

import java.awt.*;

public abstract class Line{
	
	protected int   thickness;
	protected Color color;
	
	public Line(int thickness, Color color){
		this.thickness = thickness;
		this.color = color;
	}
	
	public int getThickness(){
		return thickness;
	}
	
	public void setThickness(int thickness){
		this.thickness = thickness;
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	/**
	 * X, Y are the center of posit
	 * 	 * @param g
	 * @param x
	 * @param y
	 * @param length
	 */
	public abstract void draw(Graphics2D g, int x, int y, int length);
	
	public static class Solid extends Line{
		
		public Solid(int thickness, Color color){
			super(thickness, color);
		}
		
		@Override
		public void draw(Graphics2D g, int x, int y, int length){
			g.setColor(color);
			g.draw(ShapeUtil.rectangle(x, y, length, thickness));
		}
	}
	
	public static class DoubleColor extends Line{
		
		public Solid(int thickness, Color color){
			super(thickness, color);
		}
		
		@Override
		public void draw(Graphics2D g, int x, int y, int length){
			g.setColor(color);
			g.draw(ShapeUtil.rectangle(x, y, length, thickness));
		}
	}
}
