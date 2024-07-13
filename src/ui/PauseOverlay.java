package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;

public class PauseOverlay {
	
	private Playing playing;
	private BufferedImage backgroundImg;
	private int bgX,bgY,bgW,bgH;
	
	private AudioOptions audioOptions;
	
	private UrmButton menuB, replayB, unpauseB;
	public PauseOverlay(Playing playing) {
		this.playing=playing;
		loadBackground();
		audioOptions=playing.getGame().getAudioOptions();
		createUrmButtons();	
	}

	private void createUrmButtons() {
		int menuX=(int)(313*Game.SCALE);
		int replayX=(int)(387*Game.SCALE);
		int unpauseX=(int)(462*Game.SCALE);
		int bY=(int)(325*Game.SCALE);
		
		menuB = new UrmButton(menuX, bY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, bY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, bY, URM_SIZE, URM_SIZE, 0);
	}
	
	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)(backgroundImg.getWidth()*Game.SCALE);
		bgH = (int)(backgroundImg.getHeight()*Game.SCALE);
		bgX=Game.GAME_WIDTH/2 - bgW/2;
		bgY=(int)(25*Game.SCALE);
	}
	
	public void update() {
		menuB.update();
		replayB.update();
		unpauseB.update();
		audioOptions.update();
	}
	
	public void draw(Graphics g) {
		//Background
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
		
		//Urm Buttons
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		
		audioOptions.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {//volume slider
		//no need to check if inside the button
		//if once pressed (inside the button) we can move the slider anywhere on the screen
		
		audioOptions.mouseDragged(e); //moved to audio options class
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(isInButton(e,menuB))
			menuB.setMousePressed(true);
		else if(isInButton(e,replayB))
			replayB.setMousePressed(true);
		else if(isInButton(e,unpauseB))
			unpauseB.setMousePressed(true);
		//audio options
		else
			audioOptions.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		
		if (isInButton(e,menuB)) {
			if(menuB.isMousePressed())
				Gamestate.state=Gamestate.MENU;
				playing.unpauseGame();
		}else if (isInButton(e,replayB)) {
			if(replayB.isMousePressed())
				//System.out.println(" replay level ");
				playing.resetAll();
				playing.unpauseGame();
		}else if (isInButton(e,unpauseB)) {
			if(unpauseB.isMousePressed())
//				Gamestate.state=Gamestate.PLAYING; // not this we also would like to update the pause/unpaused status in the playing class 
				playing.unpauseGame();		
		}
		else
			audioOptions.mouseReleased(e);

		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		
	}
	
	public void mouseMoved(MouseEvent e) {
	
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		
		if(isInButton(e,menuB))
			menuB.setMouseOver(true);
		else if(isInButton(e,replayB))
			replayB.setMouseOver(true);
		else if(isInButton(e,unpauseB))
			unpauseB.setMouseOver(true);
		else
			audioOptions.mouseMoved(e);
	}
	
	private boolean isInButton(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(),e.getY());
	}

}
