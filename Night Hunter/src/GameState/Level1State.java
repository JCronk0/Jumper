package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Enemies.Ghost;
import Main.GamePanel;
import Tiles.Background;
import Tiles.TileMap;

public class Level1State extends GameState{
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	
	private AudioPlayer bgMusic, sfx;
	
	public Level1State(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/nightCity.gif");
		tileMap.loadMap("/Maps/nightCity.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/building.gif", 0.1, 0);
		
		player = new Player(tileMap);
		player.setPosition(100,300);
		
		populateEnemies();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		bgMusic = new AudioPlayer("/Music/castlecorridor.mp3");
		bgMusic.loop();
	}
	
	private void populateEnemies(){
		enemies = new ArrayList<Enemy>();
		Ghost s;
		Point[] points= new Point[]{
			new Point(300, 200),	
			new Point(322, 200),
			new Point(750, 200),
			new Point(1075, 200),
			new Point(1440, 200),
			new Point(2185, 200),
			new Point(2100, 200)
		};
		for(int i=0;i<points.length;i++){
			s = new Ghost(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
	}

	public void update() {
		
		//update player
		player.update();
		tileMap.setPosition(GamePanel.WIDTH/2-player.getx(), GamePanel.HEIGHT/2-player.gety());
		
		//set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		//attack enemies
		player.checkAttack(enemies);
		
		//update all enemies
		for(int i=0;i<enemies.size();i++){
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()){
				sfx = new AudioPlayer("/Sound/death.wav");
				sfx.play();
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(e.getx(), e.gety()));
			}
		}
		
		//update explosion
		for(int i=0;i<explosions.size();i++){
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()){
				explosions.remove(i);
				i--;
			}
		}
		
		bossRoom();
		
	}
	
	private void bossRoom(){
		if(player.getx() > 3000) {
			tileMap.loadMap("/Maps/Level1Boss.map");
		}
	}

	public void draw(Graphics2D g) {
		
		//draw background
		bg.draw(g);
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		//draw tilemap
		tileMap.draw(g);
		
		//draw player
		player.draw(g);
		
		//draw enemies
		for(int i=0;i<enemies.size();i++){
			enemies.get(i).draw(g);
		}
		
		//draw explosion
		for(int i=0;i<explosions.size();i++){
			explosions.get(i).setMapPosition((int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		//draw hud
		hud.draw(g);
		
	}

	public void keyPressed(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_F) player.setJumping(true);
		if(k == KeyEvent.VK_J) player.setGliding(true);
		if(k == KeyEvent.VK_G) player.setScratching();
		if(k == KeyEvent.VK_H) player.setFiring();
	}

	public void keyReleased(int k) {
		if(k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_W || k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_SPACE || k == KeyEvent.VK_F) player.setJumping(false);
		if(k == KeyEvent.VK_J) player.setGliding(false);
	}
	
}
