package homelet.visual;

import homelet.visual.interfaces.Renderable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RenderGroup implements Iterable<Renderable>{
	
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
	
	private static final int                     FLAG_VALUE = 10;
	private final        String                  name;
	private final        WeighedList<Renderable> renders;
	
	private RenderGroup(String name){
		this.name = name;
		this.renders = new WeighedList<>();
	}
	
	public String getName(){
		return name;
	}
	
	public boolean add(Object renderable, int weight){
		if(!(renderable instanceof Renderable))
			return false;
		renders.add((Renderable) renderable, weight);
		return true;
	}
	
	public boolean add(Object renderable){
		return add(renderable, FLAG_VALUE);
	}
	
	public void remove(Renderable renderable){
		renders.remove(renderable);
	}
	
	public void remove(double weight){
		renders.remove(weight);
	}
	
	WeighedList<Renderable> renders(){
		return renders;
	}
	
	@Override
	public Iterator<Renderable> iterator(){
		return renders.iterator();
	}
}
