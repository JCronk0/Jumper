package Entity.Enemies;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Entity.Animation;
import Entity.Enemy;
import Tiles.TileMap;

public class Ghost extends Enemy{
	
	private BufferedImage[] sprites;

	public Ghost(TileMap tm) {
		super(tm);
		
		moveSpeed = 0.4;
		maxSpeed = 0.4;
		fallSpeed = 0.2;
		maxFallSpeed = 10.0;
		
		width = 32;
		height = 32;
		cwidth = 20;
		cheight = 20;
		
		health = maxHealth = 2;
		damage = 1;
		
		//load sprites
		try{
			
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/ghost1.gif"));
			
			sprites = new BufferedImage[4];
			for(int i=0;i<sprites.length;i++){
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(300);
		
		right = true;
		facingRight = true;
		
	}
	
	private void getNextPosition(){
		// movement
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
	}
	
	public void update(){
		
		//update postion
		getNextPosition();
		checkTileMapCollision();
		
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		
		setPosition(xtemp, ytemp);
		
		//check flinching
		if(flinching){
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if(elapsed > 400){
				flinching = false;
			}
		}
		
		//if hits wall, go other directions
//		if(right && dx == 0){
//			right = false;
//			left = true;
//			facingRight = false;
//		} else if (left && dx == 0){
//			right = true;
//			left = false;
//			facingRight = true;
//		}
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		//update animation
		animation.update();
		
	}
	
	public void draw(Graphics2D g){
		
//		if(notOnScreen()) return;
		
		setMapPosition();
		
		super.draw(g);
		
	}
	
}
