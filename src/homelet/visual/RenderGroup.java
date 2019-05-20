package homelet.visual;

import homelet.visual.interfaces.Renderable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class RenderGroup implements Iterable<Object>{
	
	private static HashMap<String, RenderGroup> renderGroups;
	
	public synchronized static RenderGroup renderGroup(String name){
		if(renderGroups == null)
			renderGroups = new HashMap<>();
		if(renderGroups.containsKey(name))
			return renderGroups.get(name);
		else{
			RenderGroup renderGroup = new RenderGroup(name);
			renderGroups.put(name, renderGroup);
			return renderGroup;
		}
	}
	
	public synchronized static void removeRenderGroup(RenderGroup renderGroup){
		if(renderGroups != null)
			renderGroups.remove(renderGroup.name);
	}
	
	public synchronized static void removeRenderGroup(String name){
		if(renderGroups != null)
			renderGroups.remove(name);
	}
	
	private static final int                 FLAG_VALUE = 10;
	private final        String              name;
	private final        WeighedList<Object> renders;
	
	private RenderGroup(String name){
		this.name = name;
		this.renders = new WeighedList<>();
	}
	
	public String getName(){
		return name;
	}
	
	public void add(Renderable renderable, int weight){
		renders.add(renderable, weight);
	}
	
	public void add(RenderGroup renderGroup, int weight){
		renders.add(renderGroup, weight);
	}
	
	public void add(RenderGroup renderGroup){
		add(renderGroup, FLAG_VALUE);
	}
	
	public void add(Renderable renderable){
		add(renderable, FLAG_VALUE);
	}
	
	public void remove(Renderable renderable){
		renders.remove(renderable);
	}
	
	public void remove(RenderGroup renderGroup){
		renders.remove(renderGroup);
	}
	
	public Object[] remove(double weight){
		return renders.removeAll(weight);
	}
	
	@Override
	public Iterator<Object> iterator(){
		return renders.iterator();
	}
}
