package homelet.visual.interfaces;

public abstract class AbstractHoverable implements Hoverable{
	
	private boolean hovering;
	
	@Override
	public void setHovering(boolean hovering){
		this.hovering = hovering;
	}
	
	@Override
	public boolean isHovering(){
		return hovering;
	}
}
