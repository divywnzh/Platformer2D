package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Statemethods {//again using interfaces insure that both the Playing and the Menu implements all the correct/required functionalities
	
	//ups fps
	public void update();
	public void draw(Graphics g);
	
	//inputs
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	void mouseDragged(MouseEvent e);
	
	
}
