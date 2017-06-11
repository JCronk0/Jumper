/**
 * 
 */
package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Audio.AudioPlayer;
import Tiles.Background;

public class Help extends GameState {
	
	private Background bg;
	private boolean choice = false;
	private String back = "Back";
	private Color mColor;
	private Font mFont;
	
	private Font font;
	
	private AudioPlayer sfx;

	public Help(GameStateManager gsm){
		this.gsm = gsm;
		
		try{
			
			bg = new Background("/Backgrounds/building.gif", 1);
			bg.setVector(-0.1, 0);
			
			mColor = new Color(128,0,0);
			mFont = new Font("Serif", Font.PLAIN, 12);
			
			font = new Font("Arial", Font.PLAIN, 12);
			
			sfx = new AudioPlayer("/Sound/enter.wav");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	public void init() {

	}

	public void update() {
		bg.update();
	}

	public void draw(Graphics2D g) {
		//draw bg
		bg.draw(g);
		
		//draw title
		g.setColor(mColor);
		g.setFont(mFont);
		g.drawString("A = left      D = right", 110, 70);
		g.drawString("SPACE / F = jump      J = glide", 90, 100);
		g.drawString("G = melee      H = fireball", 100, 130);
		
		//draw menu options
		g.setFont(font);
		if(choice=true)
				g.setColor(Color.BLACK);
			
			g.drawString(back, 145, 200);
		}

	private void select(){
		choice = true;
		if(choice==true){
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void keyPressed(int k) {
		if(k==KeyEvent.VK_ENTER){
			sfx.play();
			select();
		}
	}

	public void keyReleased(int k) {}

}
