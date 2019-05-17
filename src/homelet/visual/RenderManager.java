package homelet.visual;

import homelet.visual.interfaces.Interactable;
import homelet.visual.interfaces.Renderable;

import java.awt.*;

public class RenderManager{
	
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
		addInteraction(renderable);
		list.add(renderable, weight);
		return true;
	}
	
	public void add(RenderGroup group, double weight){
		for(Renderable render : group){
			addInteraction(render);
		}
		list.add(group, weight);
	}
	
	private void addInteraction(Object renderable){
		canvas.addKeyInteraction(renderable);
		canvas.addMouseInteraction(renderable);
	}
	
	void render(Graphics2D g){
	}
}
