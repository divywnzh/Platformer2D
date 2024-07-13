package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.Crabby;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Enviornment.*;

public class Playing extends State implements Statemethods{//playing scene
	
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private boolean paused=false;
	
	private int xLvlOffset;
	private int leftBorder=(int)(0.2*Game.GAME_WIDTH);//the line if the player is beyond we calculate if there is anything to move
	private int rightBorder=(int)(0.8*Game.GAME_WIDTH);
	
//	private int lvlTilesWide=LoadSave.getLevelData()[0].length; // we don't want to move bg any more than we have -> we don't want to show a blank scene
//	
//	private int maxTilesOffset=lvlTilesWide - Game.TILES_IN_WIDTH; // remaining space 
//	private int maxLvlOffsetX=maxTilesOffset*Game.TILES_SIZE;
	private int maxLvlOffsetX; //calculated in Level class now
	
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPos;
	private Random rnd=new Random();
	
	private boolean gameOver;
	private boolean lvlCompleted;
	private boolean playerDying=false;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		
		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud=LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud=LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		
		smallCloudsPos=new int[8];
		for(int i=0; i<smallCloudsPos.length;i++)
			smallCloudsPos[i]=(int)(90*Game.SCALE)+rnd.nextInt((int)(100*Game.SCALE));
	
		calcLvlOffset();
		loadStartLevel();
	}
	
	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}
	
	private void loadStartLevel() {
		enemyManager.LoadEnemies(levelManager.getCurrentLevel());
		objectManager.loadObjects(levelManager.getCurrentLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX=levelManager.getCurrentLevel().getLvlOffset();
		
	}

	private void initClasses() {
		levelManager=new LevelManager(game);
		enemyManager=new EnemyManager(this);
		objectManager=new ObjectManager(this);
		
		player = new Player(200, 200, (int)(64*Game.SCALE), (int)(40*Game.SCALE),this); 
		player.loadlvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay=new GameOverOverlay(this);
		levelCompletedOverlay=new LevelCompletedOverlay(this);
	}
	
	@Override
	public void update() {
		
		if(paused) {
			pauseOverlay.update();
		}
		else if(lvlCompleted) {
			levelCompletedOverlay.update();
		} else if(gameOver) {
			gameOverOverlay.update();
		}
		else if(playerDying) {
			player.update();
		}
		else { //if(!gameOver)
			levelManager.update();
			objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			checkCloseToBorder();
		} 
		
	}
	
	private void checkCloseToBorder() {
		
		int playerX=(int)player.getHitbox().x;
		int diff=playerX-xLvlOffset;
		
		if(diff>rightBorder)
			xLvlOffset+=diff-rightBorder;
		else if(diff<leftBorder)
			xLvlOffset+=diff-leftBorder;
		
		if(xLvlOffset>maxLvlOffsetX)
			xLvlOffset=maxLvlOffsetX;
		else if(xLvlOffset<0)
			xLvlOffset=0;
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawClouds(g);
		
		levelManager.draw(g,xLvlOffset);
		player.render(g,xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);
		
		if(paused) {
			g.setColor(new Color(0,0,0,150)); //translucent bg
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}else if(gameOver)
			gameOverOverlay.draw(g);
		else if(lvlCompleted) {
			levelCompletedOverlay.draw(g);
		}
		
	}

	private void drawClouds(Graphics g) {

		for(int i=0;i<3;i++)
			g.drawImage(bigCloud, 0+i*BIG_CLOUD_WIDTH-(int)(xLvlOffset*0.3), (int)(205*Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for(int i=0;i<smallCloudsPos.length;i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH*4*i-(int)(xLvlOffset*0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		
	}
	
	public void resetAll() {
		// TODO: reset playing, enemy, level etc.
		gameOver=false;
		paused=false;
		lvlCompleted=false;
		playerDying=false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObjects();
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver=gameOver;
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
	}		

	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
	}
	
	public void checkSpikesTouched(Player p) {
		objectManager.checkSpikesTouched(p);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(!gameOver)
			if(paused)
				pauseOverlay.mouseDragged(e);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}else {
			gameOverOverlay.mousePressed(e);
		}
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}else {
			gameOverOverlay.mouseReleased(e);
		}
			
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(!gameOver) {
			if(paused)
				pauseOverlay.mouseMoved(e);
			else if(lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}else {
			gameOverOverlay.mouseMoved(e);
		}
			
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(gameOver)
			gameOverOverlay.keyPressed(e);
		else
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
		if(!gameOver)
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
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted=levelCompleted;
	}
	
	public void setMaxLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX=lvlOffset;
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
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public void setPlayerDying(boolean playerDying) {
		this.playerDying=playerDying;
		
	}
	
}
