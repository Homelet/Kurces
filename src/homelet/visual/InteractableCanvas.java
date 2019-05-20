package homelet.visual;

import homelet.visual.interfaces.Interactable;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public final class InteractableCanvas extends Canvas implements Interactable, Runnable{
	
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
	
	private       int           FPS_target;
	private final RenderManager manager;
	// multi threading
	private       Thread        renderThread;
	private       int           FPS_measurement;
	private       boolean       running = false;
	
	InteractableCanvas(RenderManager manager, int FPS_target){
		this.manager = manager;
		this.FPS_target = FPS_target;
	}
	
	@Override
	public void run(){
		int    FPSBuffer   = 0;
		double timePerTick = 1000000000.0 / FPS_target;
		double delta       = 0;
		long   now;
		long   lastTime;
		long   timer       = 0;
		lastTime = System.nanoTime();
		this.createBufferStrategy(3);
		BufferStrategy bs = getBufferStrategy();
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			if(FPS_target <= 0 || delta >= 1){
				FPSBuffer++;
				delta = 0;
				render(bs);
			}
			if(timer >= 1000000000){
				this.FPS_measurement = FPSBuffer;
				if(manager.isPrintingUpdate())
					System.out.println(manager.name() + " : FPS | " + this.FPS_measurement);
				FPSBuffer = 0;
				timer = 0;
			}
		}
	}
	
	private void render(BufferStrategy bs){
		Graphics2D g         = (Graphics2D) bs.getDrawGraphics();
		Dimension  dimension = this.getSize();
		if(manager.isClearingScreen())
			g.clearRect(0, 0, dimension.width, dimension.height);
		/////////////////////-draw Start-/////////////////////
		manager.render((Graphics2D) g.create(0, 0, dimension.width, dimension.height));
		/////////////////////-draw End-//////////////////////
		g.dispose();
		bs.show();
	}
	
	public int FPS_measurement(){
		return FPS_measurement;
	}
	
	@Override
	public synchronized void startRendering(){
		if(running)
			return;
		running = true;
		renderThread = new Thread(null, this, manager.name());
		renderThread.setPriority(Thread.NORM_PRIORITY);
		renderThread.start();
		if(manager.isPrintingUpdate())
			System.out.println(manager.name() + " Started!");
	}
	
	@Override
	public synchronized void stopRendering(){
		if(!running)
			return;
		running = false;
		try{
			renderThread.join();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		if(manager.isPrintingUpdate())
			System.out.println(manager.name() + " Stopped!");
	}
	
	@Override
	public void refresh(){}
	
	@Override
	public void FPS(int FPS){
		this.FPS_target = FPS;
	}
	
	@Override
	public int FPS(){
		return FPS_target;
	}
}
