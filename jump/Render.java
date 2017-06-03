package jump;

import java.awt.Graphics;

import javax.swing.JPanel;

//JPanel utilization class
class Render extends JPanel{
	
	private static final long serialVersionUID = 45345L;

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Game.jumper.repaint(g);
	}
	
}