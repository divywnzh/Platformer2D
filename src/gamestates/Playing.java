package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

public class Playing extends State implements Statemethods{//playing scene
	
	private Player player;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused=false;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		levelManager=new LevelManager(game);
		player = new Player(100, 100, (int)(64*Game.SCALE), (int)(40*Game.SCALE)); 
		player.loadlvlData(levelManager.getCurrentLevel().getLevelData());
		
		pauseOverlay = new PauseOverlay(this);
	}
	
	@Override
	public void update() {
		
		if(!paused) {
			levelManager.update();
			player.update();
		} else {
			pauseOverlay.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);	
		
		if(paused)
			pauseOverlay.draw(g);;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		if(paused)
			pauseOverlay.mousePressed(e);
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		if(paused)
			pauseOverlay.mouseReleased(e);
			
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(paused)
			pauseOverlay.mouseMoved(e);
			
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
			paused=!paused;
			break;
			
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
	
	public void unpauseGame() {
		paused=false;
		
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}


}
