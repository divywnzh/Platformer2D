package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static utilz.Constants.Directions.*;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {
	
	private GamePanel gamePanel;
	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel=gamePanel;
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {//bring to prev state
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			gamePanel.getGame().getPlayer().setUp(false);
			break;
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(false);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(false);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(false);
			break;
				
//		//actions	
//		case KeyEvent.VK_K:
//			gamePanel.getGame().getPlayer().setAttack(false);
//			break;
			
//not using this -> if 'k' pressed quickly the attack animation won't even cover all 3 frames
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		
		//directions
		case KeyEvent.VK_W:
			gamePanel.getGame().getPlayer().setUp(true);
			break;
		case KeyEvent.VK_A:
			gamePanel.getGame().getPlayer().setLeft(true);
			break;
		case KeyEvent.VK_S:
			gamePanel.getGame().getPlayer().setDown(true);
			break;
		case KeyEvent.VK_D:
			gamePanel.getGame().getPlayer().setRight(true);
			break;
			
		//actions	
		case KeyEvent.VK_K:
			gamePanel.getGame().getPlayer().setAttack(true);
			break;
			
		}
		
			
	}

}
