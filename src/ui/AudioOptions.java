package ui;

import static utilz.Constants.UI.PauseButtons.SOUND_SIZE;
import static utilz.Constants.UI.VolumeButtons.SLIDER_WIDTH;
import static utilz.Constants.UI.VolumeButtons.VOLUME_HEIGHT;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import gamestates.Gamestate;
import main.Game;

public class AudioOptions {
	private VolumeButton volumeButton;
	private SoundButton musicButton, sfxButton;

	public AudioOptions() {
		createSoundButtons();
		createVolumeButton();
	}
	
	private void createVolumeButton() {
		int vX=(int)(309*Game.SCALE);
		int vY=(int)(278*Game.SCALE);
		volumeButton=new VolumeButton(vX,vY,SLIDER_WIDTH,VOLUME_HEIGHT);
	}
	
	private void createSoundButtons() {
		
		int soundX=(int)(450*Game.SCALE);
		int musicY=(int)(140*Game.SCALE);
		int sfxY=(int)(186*Game.SCALE);
		musicButton=new SoundButton(soundX,musicY,SOUND_SIZE,SOUND_SIZE);
		sfxButton=new SoundButton(soundX,sfxY,SOUND_SIZE,SOUND_SIZE);
		
	}
	
	public void update() {
		musicButton.update();
		sfxButton.update();
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		//Sound Buttons
		musicButton.draw(g);
		sfxButton.draw(g);
				
		//volume slider
		volumeButton.draw(g);
	}
	
	public void mouseDragged(MouseEvent e) {//volume slider
		//no need to check if inside the button
		//if once pressed (inside the button) we can move the slider anywhere on the screen
		if(volumeButton.isMousePressed())
			volumeButton.changeX(e.getX());
	}
	
	public void mousePressed(MouseEvent e) {
		
		if(isInButton(e,musicButton))
			musicButton.setMousePressed(true);
		else if(isInButton(e,sfxButton))
			sfxButton.setMousePressed(true);
		//slider
		else if(isInButton(e,volumeButton))
			volumeButton.setMousePressed(true);//no need to release
			
	}

	public void mouseReleased(MouseEvent e) {
		
		if(isInButton(e,musicButton)) {
			if(musicButton.isMousePressed())
				musicButton.setMuted(!musicButton.isMuted()); // if muted then unmute - if unmuted then mute
		
		} else if (isInButton(e,sfxButton)) {
			if(sfxButton.isMousePressed())
				sfxButton.setMuted(!sfxButton.isMuted());
		}
		
		musicButton.resetBools();
		sfxButton.resetBools();	
		volumeButton.resetBools();
	}
	
	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		volumeButton.setMouseOver(false);
		
		if(isInButton(e,musicButton))
			musicButton.setMouseOver(true);
		else if(isInButton(e,sfxButton))
			sfxButton.setMouseOver(true);
		else if(isInButton(e,volumeButton))
			volumeButton.setMouseOver(true);

	}
	
	private boolean isInButton(MouseEvent e, PauseButton b) {
		return b.getBounds().contains(e.getX(),e.getY());
	}

}

