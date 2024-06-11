package gamestates;

import main.Game;

public class State {//super class for all our game states
	
	protected Game game;//for childrens to excess
	
	public State(Game game) {
		this.game=game;
	}
	
	public Game getGame() {
		return game;
	}

}
