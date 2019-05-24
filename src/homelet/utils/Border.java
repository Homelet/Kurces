package homelet.utils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

public abstract class Border{
	
	protected BorderPosition position;
	protected double         thick;
	protected Color          color;
	
	public Border(BorderPosition position, Color color, double thick){
		position(position);
		color(color);
		thick(thick);
	}
	
	public abstract Rectangle2D draw(Graphics2D g, Rectangle2D bound);
	
	public BorderPosition position(){
		return position;
	}
	
	public void position(BorderPosition position){
		this.position = Objects.requireNonNull(position);
	}
	
	public Color color(){
		return color;
	}
	
	public void color(Color color){
		this.color = color;
	}
	
	public double thick(){
		return thick;
	}
	
	public void thick(double thick){
		if(thick < 0)
			throw new IllegalArgumentException("Thickness can not be negative");
		this.thick = thick;
	}
	
	public static class Solid extends Border{
		
		public Solid(BorderPosition position, Color color, double thick){
			super(position, color, thick);
		}
		
		@Override
		public Rectangle2D draw(Graphics2D g, Rectangle2D bound){
			g.setColor(color);
			switch(position){
				default:
				case INSIDE:
					return ShapeUtil.rectangle(draw_inside(g, bound, thick));
				case OUTSIDE:
					return draw_outside(g, bound);
				case INSIDE_OUTSIDE:
					return draw_inside_outside(g, bound);
			}
		}
		
		private Rectangle2D draw_inside(Graphics2D g, Rectangle2D bound, double thick){
			// top
			g.fill(ShapeUtil.rectangle(bound.getX(), bound.getY(), bound.getWidth(), thick));
			// left
			g.fill(ShapeUtil.rectangle(bound.getX(), bound.getY() + thick, thick, bound.getHeight() - 2 * thick));
			// right
			g.fill(ShapeUtil.rectangle(bound.getX() + bound.getWidth() - thick, bound.getY() + thick, thick, bound.getHeight() - 2 * thick));
			// bottom
			g.fill(ShapeUtil.rectangle(bound.getX(), bound.getY() + bound.getHeight() - thick, bound.getWidth(), thick));
			return bound;
		}
		
		private Rectangle2D draw_outside(Graphics2D g, Rectangle2D bound){
			return draw_inside(g, ShapeUtil.rectangle_expand(bound, thick, thick, thick, thick), thick);
		}
		
		private Rectangle2D draw_inside_outside(Graphics2D g, Rectangle2D bound){
			double thickness_out = thick / 2;
			return draw_inside(g, ShapeUtil.rectangle_expand(bound, thickness_out, thickness_out, thickness_out, thickness_out), thick);
		}
	}
	
	public static class DoubleColorDashed extends Border{
		
		private double width;
		private Color  color2;
		
		public DoubleColorDashed(BorderPosition position, Color color, Color color2, double thick, double width){
			super(position, color, thick);
			width(width);
			color2(color2);
		}
		
		public double width(){
			return width;
		}
		
		public void width(double width){
			if(thick < 0)
				throw new IllegalArgumentException("Thickness can not be negative");
			this.width = width;
		}
		
		public Color color2(){
			return color2;
		}
		
		public void color2(Color color2){
			this.color2 = color2;
		}
		
		@Override
		public Rectangle2D draw(Graphics2D g, Rectangle2D bound){
			switch(position){
				case INSIDE_OUTSIDE:
					return draw_inside_outside(g, bound);
				default:
				case INSIDE:
					return ShapeUtil.rectangle(draw_inside(g, bound, thick, width));
				case OUTSIDE:
					return draw_outside(g, bound);
			}
		}
		
		private Rectangle2D draw_inside(Graphics2D g, Rectangle2D bound, double thick, double step){
			// top
			boolean flip = true;
			boolean c    = true;
			for(double x = bound.getX(), y = bound.getY(); x < bound.getX() + bound.getWidth() - thick; x += step, flip = !flip){
				if(flip){
					if(c)
						g.setColor(color);
					else
						g.setColor(color2);
					g.draw(ShapeUtil.rectangle(x, y, step, thick));
					c = !c;
				}
			}
			// right
			flip = true;
			for(double x = bound.getX() + bound.getWidth() - thick, y = bound.getY(); y < bound.getY() + bound.getHeight() - thick; y += step, flip = !flip){
				if(flip){
					if(c)
						g.setColor(color);
					else
						g.setColor(color2);
					g.draw(ShapeUtil.rectangle(x, y, thick, step));
					c = !c;
				}
			}
			// bottom
			flip = true;
			for(double x = bound.getX() + bound.getWidth() - thick, y = bound.getY() + bound.getHeight() - thick; x > bound.getX(); x -= step, flip = !flip){
				if(flip){
					if(c)
						g.setColor(color);
					else
						g.setColor(color2);
					g.draw(ShapeUtil.rectangle(x, y, step, thick));
					c = !c;
				}
			}
			// bottom
			flip = true;
			for(double x = bound.getX(), y = bound.getY() + bound.getHeight() - thick; y > bound.getY(); y -= step, flip = !flip){
				if(flip){
					if(c)
						g.setColor(color);
					else
						g.setColor(color2);
					g.draw(ShapeUtil.rectangle(x, y, thick, step));
					c = !c;
				}
			}
			return bound;
		}
		
		private Rectangle2D draw_outside(Graphics2D g, Rectangle2D bound){
			return draw_inside(g, ShapeUtil.rectangle_expand(bound, thick, thick, thick, thick), thick, width);
		}
		
		private Rectangle2D draw_inside_outside(Graphics2D g, Rectangle2D bound){
			double thickness_out = thick / 2;
			return draw_inside(g, ShapeUtil.rectangle_expand(bound, thickness_out, thickness_out, thickness_out, thickness_out), thick, width);
		}
	}
	
	public static class Dashed extends DoubleColorDashed{
		
		public Dashed(BorderPosition position, Color color, double thick, double width){
			super(position, color, color, thick, width);
		}
	}
	
	public static class DoubleColorDotted extends DoubleColorDashed{
		
		public DoubleColorDotted(BorderPosition position, Color color, Color color2, double thick){
			super(position, color, color2, thick, thick);
		}
	}
	
	public static class Dotted extends DoubleColorDotted{
		
		public Dotted(BorderPosition position, Color color, double thick){
			super(position, color, color, thick);
		}
	}
	
	public enum BorderPosition{
		/**
		 * The boarder is positioned inside the bound
		 */
		INSIDE,
		/**
		 * The boarder is positioned outside the bound
		 */
		OUTSIDE,
		/**
		 * The boarder is positioned half inside and half outside the bound
		 */
		INSIDE_OUTSIDE;
	}
}
