package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods{
	
	private MenuButton[] buttons=new MenuButton[3];
	private BufferedImage backgroundImg, backgroundImgBg;
	private int menuX,menuY,menuWidth,menuHeight;
	
	public Menu(Game game) {
		super(game);
		loadBackground();
		loadButtons();
	}

	private void loadBackground() {
		
		backgroundImgBg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BG_IMG);

		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
		menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
		menuX=Game.GAME_WIDTH/2 - menuWidth/2;
		menuY=(int) (45*Game.SCALE);//change to +45 if menu_background.jpg used
		
		
	}

	private void loadButtons() {
		buttons[0]=new MenuButton(Game.GAME_WIDTH/2,(int)(150*Game.SCALE),0,Gamestate.PLAYING);//150 is more centered
		buttons[1]=new MenuButton(Game.GAME_WIDTH/2,(int)(220*Game.SCALE),1,Gamestate.OPTIONS);
		buttons[2]=new MenuButton(Game.GAME_WIDTH/2,(int)(290*Game.SCALE),2,Gamestate.QUIT);


	}

	@Override
	public void update() {
		for(MenuButton mb: buttons)
			mb.update();
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.drawImage(backgroundImgBg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.setColor(new Color(0, 0, 0,100)); //translucent bg
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
//		g.setColor(Color.black);
//		g.drawString("Menu",Game.GAME_WIDTH/2, 200);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb: buttons)
			mb.draw(g);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb: buttons) {
			if(IsInButton(e,mb))//check we are inside the button when we press
				mb.setMousePressed(true);
				
		}
		
	}
	

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb: buttons) {
			if(IsInButton(e, mb) && mb.isMousePressed()){// check we are inside the button when we press
				// && makes sure not just hovering but if we actually pressed 
				mb.applyGamestate();
				System.out.println("working");
			}
				
		}
		
		resetButtons();
		
	}

	private void resetButtons() {
		for(MenuButton mb: buttons) { //reset bools for each and every button
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb: buttons)
			mb.setMouseOver(false);
		
		for(MenuButton mb: buttons)
			if(IsInButton(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			Gamestate.state=Gamestate.PLAYING;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	
}
