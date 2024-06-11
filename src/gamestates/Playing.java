package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements Statemethods{//playing scene
	
	private Player player;
	private LevelManager levelManager;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		levelManager=new LevelManager(game);
		player = new Player(100, 100, (int)(64*Game.SCALE), (int)(40*Game.SCALE)); 
		player.loadlvlData(levelManager.getCurrentLevel().getLevelData());
		
	}
	
	@Override
	public void update() {
		levelManager.update();
		player.update();
		
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		
		//directions
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;

		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
			
		//actions	
		case KeyEvent.VK_K:
			player.setAttack(true);
			break;
			
		//switching state
		case KeyEvent.VK_BACK_SPACE:
			Gamestate.state=Gamestate.MENU;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {// bring to previous state
		
		switch(e.getKeyCode()) {

		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		}
		
//		//actions	
//		case KeyEvent.VK_K:
//			gamePanel.getGame().getPlayer().setAttack(false);
//			break;
			
//not using this -> if 'k' pressed quickly the attack animation won't even cover all 3 frames
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

}
