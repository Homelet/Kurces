package homelet.utils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public enum Alignment{
	NORTH_WEST,
	TOP_LEFT,
	NORTH,
	TOP,
	NORTH_EAST,
	TOP_RIGHT,
	WEST,
	LEFT,
	ORIGIN,
	CENTER,
	EAST,
	RIGHT,
	SOUTH_WEST,
	BOTTOM_LEFT,
	SOUTH,
	BOTTOM,
	SOUTH_EAST,
	BOTTOM_RIGHT,
	KEEP_X_ON_LEFT,
	KEEP_X_ON_WEST,
	KEEP_X_ON_CENTER,
	KEEP_X_ON_ORIGIN,
	KEEP_X_ON_RIGHT,
	KEEP_X_ON_EAST,
	KEEP_Y_ON_TOP,
	KEEP_Y_ON_NORTH,
	KEEP_Y_ON_CENTER,
	KEEP_Y_ON_ORIGIN,
	KEEP_Y_ON_BOTTOM,
	KEEP_Y_ON_SOUTH,
	FREE;
	
	/**
	 * Get vertex point 2 d.
	 *
	 * @param container the container
	 * @param object    the object
	 * @return the point 2 d
	 */
	public Point2D getVertex(Rectangle2D container, Rectangle2D object){
		return Alignment.getVertex(this, container.getX(), container.getY(), container.getWidth(), container.getHeight(), object.getX(), object.getY(), object.getWidth(), object.getHeight());
	}
	
	/**
	 * Get vertex point 2 d.
	 *
	 * @param container the container
	 * @param object    the object
	 * @return the point 2 d
	 */
	public Point getVertex(Rectangle container, Rectangle object){
		Point2D point2D = Alignment.getVertex(this, container.getX(), container.getY(), container.getWidth(), container.getHeight(), object.getX(), object.getY(), object.getWidth(), object.getHeight());
		return new Point((int) point2D.getX(), (int) point2D.getY());
	}
	
	/**
	 * Design for giving accurate position
	 *
	 * @param alignment        the alignment mode
	 * @param container_x      the container x
	 * @param container_y      the container y
	 * @param container_width  the container width
	 * @param container_height the container height
	 * @param object_x         the object x
	 * @param object_y         the object y
	 * @param object_width     the object width
	 * @param object_height    the object height
	 * @return a point that meet the desire
	 * @author HomeletWei
	 * @see Alignment
	 */
	public static Point2D getVertex(Alignment alignment, double container_x, double container_y, double container_width, double container_height, double object_x, double object_y, double object_width, double object_height){
		switch(alignment){
			case NORTH_WEST:
			case TOP_LEFT:
				return GH.point(usingFloat, container_x, container_y);
			case NORTH:
			case TOP:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y);
			case NORTH_EAST:
			case TOP_RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y);
			case WEST:
			case LEFT:
				return GH.point(usingFloat, container_x, container_y + (container_height - object_height) / 2);
			case ORIGIN:
			case CENTER:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + (container_height - object_height) / 2);
			case EAST:
			case RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y + (container_height - object_height) / 2);
			case SOUTH_WEST:
			case BOTTOM_LEFT:
				return GH.point(usingFloat, container_x, container_y + (container_height - object_height));
			case SOUTH:
			case BOTTOM:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + (container_height - object_height));
			case SOUTH_EAST:
			case BOTTOM_RIGHT:
				return GH.point(usingFloat, container_x + (container_width - object_width), container_y + (container_height - object_height));
			case KEEP_X_ON_WEST:
			case KEEP_X_ON_LEFT:
				return GH.point(usingFloat, container_x, container_y + object_y);
			case KEEP_X_ON_ORIGIN:
			case KEEP_X_ON_CENTER:
				return GH.point(usingFloat, container_x + (container_width - object_width) / 2, container_y + object_y);
			case KEEP_X_ON_EAST:
			case KEEP_X_ON_RIGHT:
				return GH.point(usingFloat, container_x + container_width - object_width, container_y + object_y);
			case KEEP_Y_ON_NORTH:
			case KEEP_Y_ON_TOP:
				return GH.point(usingFloat, container_x + object_x, container_y);
			case KEEP_Y_ON_ORIGIN:
			case KEEP_Y_ON_CENTER:
				return GH.point(usingFloat, container_x + object_x, container_y + (container_height - object_height) / 2);
			case KEEP_Y_ON_SOUTH:
			case KEEP_Y_ON_BOTTOM:
				return GH.point(usingFloat, container_x + object_x, container_y + container_height - object_height);
			case FREE:
			default:
				return GH.point(usingFloat, container_x + object_x, container_y + object_y);
		}
	}
}