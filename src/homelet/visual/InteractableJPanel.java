package homelet.visual;

import homelet.visual.interfaces.Interactable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public final class InteractableJPanel extends JPanel implements Interactable{
	
	@Override
	public void addKeyInteraction(Object o){
		if(o instanceof KeyListener)
			this.addKeyListener((KeyListener) o);
	}
	
	@Override
	public void removeKeyInteraction(Object o){
		if(o instanceof KeyListener)
			this.removeKeyListener((KeyListener) o);
	}
	
	@Override
	public void addMouseInteraction(Object o){
		if(o instanceof MouseListener)
			this.addMouseListener((MouseListener) o);
		if(o instanceof MouseMotionListener)
			this.addMouseMotionListener((MouseMotionListener) o);
		if(o instanceof MouseWheelListener)
			this.addMouseWheelListener((MouseWheelListener) o);
	}
	
	@Override
	public void removeMouseInteraction(Object o){
		if(o instanceof MouseListener)
			this.removeMouseListener((MouseListener) o);
		if(o instanceof MouseMotionListener)
			this.removeMouseMotionListener((MouseMotionListener) o);
		if(o instanceof MouseWheelListener)
			this.removeMouseWheelListener((MouseWheelListener) o);
	}
	
	private final RenderManager manager;
	
	InteractableJPanel(RenderManager manager){
		this.manager = manager;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2        = (Graphics2D) g;
		Dimension  dimension = this.getSize();
		if(manager.isClearingScreen())
			g2.clearRect(0, 0, dimension.width, dimension.height);
		/////////////////////-draw Start-/////////////////////
		manager.render((Graphics2D) g.create(0, 0, dimension.width, dimension.height));
		/////////////////////-draw End-//////////////////////
		g.dispose();
		if(manager.isPrintingUpdate())
			System.out.println(manager.name() + " : Paint Finnish!");
	}
	
	@Override
	public void refresh(){
		this.repaint();
	}
	
	@Override
	public void startRendering(){}
	
	@Override
	public void stopRendering(){}
	
	@Override
	public void FPS(int FPS){}
	
	@Override
	public int FPS(){
		return -1;
	}
}
