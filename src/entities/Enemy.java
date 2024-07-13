package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.GRAVITY;
import static utilz.Constants.ANI_SPEED;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity { //abstract class Enemy ensures that we don't create Enemies just straight out of enemy -> it has to be extending enemy
	
	protected int enemyType;//protected for excess for classes extending enemy
	
	protected boolean firstUpdate=true;

	protected int walkDir=LEFT;
	
	protected int tileY;
	protected float attackDistance=1*Game.TILES_SIZE;

	protected boolean active=true;
	protected boolean attackChecked;
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType=enemyType;

		maxHealth=GetMaxHealth(enemyType);
		currentHealth=maxHealth;
		walkSpeed=0.35f*Game.SCALE;
	}
	
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if(!IsEntityOnFloor(hitbox,lvlData)) {//if not on the floor
			inAir=true;
		}
		firstUpdate=false;
	}
	
	protected void updateInAir(int[][] lvlData) {
		if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y+=airSpeed;
			airSpeed+=GRAVITY;
		}else {
			inAir=false;
			hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			tileY=(int)(hitbox.y/Game.TILES_SIZE); //tileY for enemy
		}
	}
	
	protected void move(int[][] lvlData) {
		float xSpeed=0;
		
		if(walkDir==LEFT)
			xSpeed =- walkSpeed;
		else 
			xSpeed = walkSpeed;
		
		if(CanMoveHere(hitbox.x+xSpeed,hitbox.y, hitbox.width, hitbox.height, lvlData))
			if(IsFloor(hitbox, xSpeed, lvlData)) { 
				hitbox.x+=xSpeed;
				return;
			}
		
		changeWalkDir();
				
	}
	
	protected void turnTowardsPlayer(Player player) {
		
		if(player.hitbox.x>hitbox.x)
			walkDir=RIGHT;
		else
			walkDir=LEFT;
		
	}
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		
		int playerTileY=(int)(player.getHitbox().y/Game.TILES_SIZE);//takes current y pos of the player and turns it into current tile pos for the same
		if(playerTileY==tileY)//enemy only sees the player if there are on the same horizontal plane (same Y co-ordinate)
			if(isPlayerInRange(player))
				if(IsSightClear(lvlData,hitbox,player.hitbox,tileY))//check for no obstacles or no pits in the line of sight
					return true;
		return false;
		
	}
	

	private boolean isPlayerInRange(Player player) {
		
		int absValue = (int)Math.abs(player.hitbox.x-hitbox.x);
		return absValue <= attackDistance*5; //returns true if the condition satisfies else false
	
	}
	
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int)Math.abs(player.hitbox.x - hitbox.x);
		return absValue<=attackDistance;
		
	}
	
	protected void newState(int enemyState) {
		this.state=enemyState;
		aniTick=0;
		aniIndex=0;
	}
	
	public void hurt(int amount) {
		currentHealth-=amount;
		if(currentHealth<=0)
			newState(DEAD);
		else
			newState(HIT);
		
	}
	
	protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player) {
		if(attackBox.intersects(player.hitbox))
			player.changeHealth(-GetEnemyDmg(enemyType));//negative to decrease
		attackChecked=true;
	}
	
	protected void updateAnimationTick() {
		
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(enemyType, state)) {//One complete animation 
				aniIndex = 0;
				
				switch(state) {
				case ATTACK, HIT -> state=IDLE; // switch back to idle right after one attack completes 
				case DEAD -> active=false;
				}
				
			}
		}
	}
	
	
	protected void changeWalkDir() {
		if(walkDir==LEFT)
			walkDir=RIGHT;
		else
			walkDir=LEFT;		
	}
	
	public void resetEnemy() {
		hitbox.x=x;
		hitbox.y=y;
		firstUpdate=true;
		currentHealth=maxHealth;
		newState(IDLE);
		active=true;
		airSpeed=0;
	}
	
	public boolean isActive() {
		return active;
	}
}
