package homelet.visual.interfaces;

/**
 * this class HAS TO BE properly implemented in order to provide the functionally to work,
 * please refer to {@link AbstractHoverable}
 * <br>
 * Example:
 * <code><pre>
 *    private boolean hovering;
 *
 *    public void setHovering(boolean hovering){
 * 		this.hovering = hovering;
 *    }
 *
 *    public boolean isHovering(){
 * 		return hovering;
 *    }
 * </pre></code>
 */
public interface Hoverable{
	
	void setHovering(boolean hovering);
	
	boolean isHovering();
}
