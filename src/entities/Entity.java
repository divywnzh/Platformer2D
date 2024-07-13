package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity { //class that you can not make object of used for extending (abstraction)
	
	protected float x,y;//classes that extend Entity can now use protected terms
	protected int width,height;
	protected Rectangle2D.Float hitbox;
	protected float walkSpeed; //for both player and enemy
	
	protected int aniTick, aniIndex; 
	protected int state; //enemyState / PlayerAction
	protected float airSpeed;
	protected boolean inAir=false;
	
	protected int maxHealth;
	protected int currentHealth;
	
	//AttackBox
	protected Rectangle2D.Float attackBox;
	
	public Entity(float x, float y, int width, int height ) {
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
		
//		initHitbox();

	}
	
	protected void drawHitbox(Graphics g, int xLvlOffset) {
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}

	protected void initHitbox(int width, int height) {//manually passed -> not using constructor's args
		hitbox=new Rectangle2D.Float(x,y,(int)(width*Game.SCALE),(int)(height*Game.SCALE));
		
	}
	
	protected void drawAttackBox(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);	
	}
	
//	public void updateHitbox() {
//		hitbox.x=(int)x;
//		hitbox.y=(int)y;
//	}
	
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public int getState() {
		return state;
	}
	
	public int getAniIndex() {
		return aniIndex;
	}

}
