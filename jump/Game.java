package jump;

//Java premade methods
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

//Main method
public class Game implements ActionListener, KeyListener{

	//Game variables
	static Game jumper;
	private Render renderObject;
	private Rectangle hero;
	private ArrayList<Rectangle> columns;
	private Random rand;	
	private final int WIDTH = 800, HEIGHT = 800;
	private int jump, score;
	private boolean gameOver, started;
	

	//Game video window and objects
	public Game(){
		JFrame jframe = new JFrame();
		Timer timer = new Timer(16, this);
		
		renderObject = new Render();
		rand = new Random();
		hero = new Rectangle(WIDTH - 750, HEIGHT - 140, 20, 20);
		columns = new ArrayList<Rectangle>();

		jframe.add(renderObject);
		jframe.setTitle("    JUMPER      ");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setLocationRelativeTo(null);
		jframe.addKeyListener(this);
		jframe.setResizable(false);
		jframe.setVisible(true);

		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);		

		timer.start();
	}

	// Column creation and randomization
	private void addColumn(boolean start){
		int space = 800;
		int width = 12 + rand.nextInt(150);
		int height = 50 + rand.nextInt(100);

		if (start){
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		}
		else
		{
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	//Sets column color
	private void paintColumn(Graphics g, Rectangle column){
		g.setColor(Color.red);
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	//Character jumping and game reset
	private void jump(){
		if (gameOver){
			hero = new Rectangle(WIDTH - 750, HEIGHT - 140, 20, 20);
			columns.clear();
			jump = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}
		if (!started){
			started = true;
		}
		else if (!gameOver && hero.y == HEIGHT - 140)
		{
			if (jump > 0){
				jump = 0;
			}

			jump -= 50;
		}    
	}

	//Character, column movement, and scoring
	public void actionPerformed(ActionEvent e){
		int speed = 10;

		if (started){
			for (int i = 0; i < columns.size(); i++){
				Rectangle column = columns.get(i);

				column.x -= speed;
			}

			if (jump < 15){
				jump += 4;
			}

			for (int i = 0; i < columns.size(); i++){
				Rectangle column = columns.get(i);

				if (column.x + column.width < 0){
					columns.remove(column);

					if (column.y == 0){
						addColumn(false);
					}
				}
			}

			hero.y += jump;

			for (Rectangle column : columns){
				if (column.y == 0 && hero.x + hero.width / 2 > column.x + column.width / 2 - 1 &&
					hero.x + hero.width / 2 < column.x + column.width / 2 + 10){
					score++;
				}

				if (column.intersects(hero)){
					gameOver = true;
					started = false;

					if (hero.y <= column.x){
						hero.y = column.x - hero.width;

					}
					else
					{
						if (column.y != 0){
							hero.y = column.y - hero.height;
						}
						else if (hero.y < column.height){
							hero.y = column.height;
						}
					}
				}
			
			}

			if (hero.y + jump >= HEIGHT - 120){
				hero.y = HEIGHT - 120 - hero.height;
				
			}
		}

		renderObject.repaint();
	}

	//Sets colors for rectangles
	public void repaint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.gray);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.yellow);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		g.setColor(Color.blue);
		g.fillRect(0, HEIGHT - 800, WIDTH, 65);

		g.setColor(Color.green);
		g.fillRect(hero.x, hero.y, hero.width, hero.height);

		for (Rectangle column : columns){
			paintColumn(g, column);
		}

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 40));

		if (!started && !gameOver){
			g.drawString("Press Space Bar to Jump Over Obstacles!", 0, HEIGHT / 2 - 50);
		}

		if (gameOver){
			g.drawString("Game Over!", 300, HEIGHT / 2 - 50);
			g.drawString("Press Space Bar to Retry", 170, HEIGHT / 2 - 5);
		}

		if (!gameOver && started){
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}
	
	//Main statement to tell computer to begin
	public static void main(String[] args){
		jumper = new Game();
	}

	//Game input recognizing
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			jump();
		}
	}

	public void keyTyped(KeyEvent e){

	}

	public void keyPressed(KeyEvent e){

	}

}