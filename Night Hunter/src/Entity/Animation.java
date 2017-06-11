package Entity;

import java.awt.image.BufferedImage;

public class Animation {

	private BufferedImage[] frames;
	private int currFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce; //if animation has played already
	
	public Animation(){
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long d) { delay = d; }
	public void setFrame(int i) { currFrame = i; }
	
	public void update(){
		if(delay == -1) return;
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currFrame++;
			startTime = System.nanoTime();
		}
		if(currFrame == frames.length){
			currFrame = 0;
			playedOnce = true;
		}
	}
	
	public int getFrame(){ return currFrame; }
	public BufferedImage getImage() { return frames[currFrame]; }
	public boolean hasPlayedOnce() { return playedOnce; }
	
}
