package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public class State {//super class for all our game states
	
	protected Game game;//for childrens to excess
	
	public State(Game game) {
		this.game=game;
	}
	
	public boolean IsInButton(MouseEvent e, MenuButton mb) {
		return mb.getBounds().contains(e.getX(),e.getY());
	}
	
	public Game getGame() {
		return game;
	}

}
