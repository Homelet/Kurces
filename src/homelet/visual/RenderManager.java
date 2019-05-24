package homelet.visual;

import homelet.visual.interfaces.Interactable;
import homelet.visual.interfaces.Locatable;
import homelet.visual.interfaces.Renderable;
import homelet.visual.interfaces.Tickable;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public final class RenderManager implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener{
	
	public static RenderManager createPassiveStyleRenderManager(String name, int FPS){
		return createPassiveStyleRenderManager(name, FPS, false, true);
	}
	
	/**
	 * Passive style, also called FPS style, it passively call Render and refresh the screen a few times per second,
	 * this is great for games
	 */
	public static RenderManager createPassiveStyleRenderManager(String name, int FPS, boolean printFlag, boolean clearScreenFlag){
		RenderManager manager = new RenderManager(name, printFlag, clearScreenFlag);
		manager.canvas = new InteractableCanvas(manager, FPS);
		return manager;
	}
	
	public static RenderManager createActiveStyleRenderManager(String name){
		return createActiveStyleRenderManager(name, false, true);
	}
	
	/**
	 * Active style, it call Render and refresh the screen on demand, great for applications which don't need to
	 * refresh every second
	 */
	public static RenderManager createActiveStyleRenderManager(String name, boolean printFlag, boolean clearScreenFlag){
		RenderManager manager = new RenderManager(name, printFlag, clearScreenFlag);
		manager.canvas = new InteractableJPanel(manager);
		return manager;
	}
	
	public void setPrintFlag(boolean printFlag){
		this.printFlag = printFlag;
	}
	
	public void setClearScreenFlag(boolean clearScreenFlag){
		this.clearScreenFlag = clearScreenFlag;
	}
	
	public boolean isClearingScreen(){
		return clearScreenFlag;
	}
	
	public boolean isPrintingUpdate(){
		return printFlag;
	}
	
	public String name(){
		return name;
	}
	
	public void startRendering(){
		canvas.startRendering();
	}
	
	public void stopRendering(){
		canvas.stopRendering();
	}
	
	public void refresh(){
		canvas.refresh();
	}
	
	public void FPS(int FPS){
		canvas.FPS(FPS);
	}
	
	public int FPS(){
		return canvas.FPS();
	}
	
	private       boolean             clearScreenFlag;
	private       boolean             printFlag;
	private       Interactable        canvas;
	private final WeighedList<Object> list;
	private final String              name;
	
	private RenderManager(String name, boolean printFlag, boolean clearScreenFlag){
		this.list = new WeighedList<>();
		this.name = name;
		this.printFlag = printFlag;
		this.clearScreenFlag = clearScreenFlag;
		addInteraction(this);
	}
	
	/**
	 * if the object is instance of renderable it will be add to the list
	 *
	 * @param renderable the object that need to be added
	 * @param weight     the weight of the group
	 * @return if true if object is instance of {@link Renderable} false otherwise
	 */
	public boolean add(Object renderable, double weight){
		if(!(renderable instanceof Renderable))
			return false;
		list.add(renderable, weight);
		return true;
	}
	
	public void add(RenderGroup group, double weight){
		list.add(group, weight);
	}
	
	public void remove(Renderable renderable){
		list.remove(renderable);
	}
	
	public void remove(RenderGroup group){
		list.remove(group);
	}
	
	public Object[] removeAll(double weight){
		return list.removeAll(weight);
	}
	
	private void addInteraction(Object renderable){
		canvas.addKeyInteraction(renderable);
		canvas.addMouseInteraction(renderable);
	}
	
	private void removeInteraction(Object renderable){
		canvas.removeKeyInteraction(renderable);
		canvas.removeMouseInteraction(renderable);
	}
	
	void tick(){
		forEach(list, (e)->{
			if(e instanceof Tickable)
				tick((Tickable) e);
		});
	}
	
	private void tick(Tickable tickable){
		if(tickable.isTicking())
			tickable.tick();
	}
	
	void render(Graphics2D g){
		final Rectangle clipBounds = g.getClipBounds();
		forEach(list, (e)->{
			render(g, clipBounds, (Renderable) e);
		});
	}
	
	private void render(Graphics2D g, Rectangle clipBounds, Renderable renderable){
		if(!renderable.isRendering())
			return;
		Rectangle bound = renderable instanceof Locatable ? clipBounds.intersection(((Locatable) renderable).getBounds().getBounds()) : clipBounds;
		renderable.render((Graphics2D) g.create(bound.x, bound.y, bound.width, bound.height));
	}
	
	private void forEach(Iterable iterable, Action action){
		for(Object obj : iterable){
			if(obj instanceof Iterable)
				forEach((Iterable) obj, action);
			action.call(obj);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e){
		forEach(list, (item)->{
			if(item instanceof KeyListener)
				((KeyListener) item).keyTyped(e);
		});
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		forEach(list, (item)->{
			if(item instanceof KeyListener)
				((KeyListener) item).keyPressed(e);
		});
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		forEach(list, (item)->{
			if(item instanceof KeyListener)
				((KeyListener) item).keyReleased(e);
		});
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(e.getPoint()))
						return;
				}
				((MouseListener) item).mouseClicked(e);
			}
		});
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(e.getPoint()))
						return;
				}
				((MouseListener) item).mousePressed(e);
			}
		});
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(e.getPoint()))
						return;
				}
				((MouseListener) item).mouseReleased(e);
			}
		});
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseListener && !(item instanceof Locatable)){
				((MouseListener) item).mouseEntered(e);
			}
		});
	}
	
	@Override
	public void mouseExited(MouseEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseListener && !(item instanceof Locatable)){
				((MouseListener) item).mouseExited(e);
			}
		});
	}
	
	private final Point mouseHistory = new Point();
	
	@Override
	public void mouseDragged(MouseEvent e){
		Point mouse = e.getPoint();
		forEach(list, (item)->{
			if(item instanceof MouseMotionListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(mouse))
						return;
				}
				((MouseMotionListener) item).mouseDragged(e);
			}
		});
		updateMouse(e, mouse);
	}
	
	@Override
	public void mouseMoved(MouseEvent e){
		Point mouse = e.getPoint();
		forEach(list, (item)->{
			if(item instanceof MouseMotionListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(mouse))
						return;
				}
				((MouseMotionListener) item).mouseMoved(e);
			}
		});
		updateMouse(e, mouse);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e){
		forEach(list, (item)->{
			if(item instanceof MouseWheelListener){
				if(item instanceof Locatable){
					Rectangle2D bound = ((Locatable) item).getBounds();
					if(!bound.contains(e.getPoint()))
						return;
				}
				((MouseWheelListener) item).mouseWheelMoved(e);
			}
		});
	}
	
	private synchronized void updateMouse(MouseEvent e, Point mouseCurrent){
		callEnter(e, mouseHistory, mouseCurrent);
		mouseHistory.setLocation(mouseCurrent);
	}
	
	private void callEnter(MouseEvent e, Point mouseHistory, Point mouseCurrent){
		forEach(list, (item)->{
			if(item instanceof MouseListener && item instanceof Locatable){
				Rectangle2D bound = ((Locatable) item).getBounds();
				if(bound.contains(mouseHistory)){
					if(!bound.contains(mouseCurrent)){
						((MouseListener) item).mouseExited(e);
					}
				}else{
					if(bound.contains(mouseCurrent)){
						((MouseListener) item).mouseEntered(e);
					}
				}
			}
		});
	}
	
	interface Action{
		
		void call(Object o);
	}
}
