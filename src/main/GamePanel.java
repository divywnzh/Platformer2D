package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {
	
	private MouseInputs mouseInputs;
	private Game game;

	public GamePanel(Game game) {
		
		mouseInputs = new MouseInputs(this);
		this.game=game;
		
		setPanelSize();
		
		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	
	private void setPanelSize() {
		Dimension size=new Dimension(1280,800); // 1280/32 = 40 images wide, 800/32=25 images wide
		setMinimumSize(size); //takes Dimension object as input
		setPreferredSize(size);
		setMaximumSize(size);
		
	}
	
	public void updateGame() {
		
		
	}
	
	public void paintComponent(Graphics g) {//think of Graphics g as a paintbrush
		//gets called because of the constructor of JPanel
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}

}
	

	

