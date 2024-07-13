package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class Crabby extends Enemy {
	

	//AttackBox
	private int attackBoxOffsetX;
	
	//only the x and y will be different for each crabby. The width and height remains the same
//	public Crabby(float x, float y, int width, int height, int enemyType) {
	public Crabby(float x, float y) {
		
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(22,19);
		initAttackBox();

	}
	
	private void initAttackBox() {
		attackBox=new Rectangle2D.Float(x,y,(int)(82*Game.SCALE),(int)(19*Game.SCALE));
		attackBoxOffsetX=(int)(Game.SCALE*30);
	}
	
	public void update(int[][] lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	
	private void updateAttackBox() {
		attackBox.x=hitbox.x - attackBoxOffsetX;
		attackBox.y=hitbox.y;
	}

	private void updateBehavior(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);//Method in Enemy
		}
		if(inAir) {
			updateInAir(lvlData);//Method in Enemy
		}else {
			switch(state) {
			case IDLE:
				newState(RUNNING);
				break;
				
			case RUNNING:
				if(canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
					if(isPlayerCloseForAttack(player))
						newState(ATTACK);
				}
				move(lvlData);//Method in Enemy
				break;
				
			case ATTACK:
				if(aniIndex==0)
					attackChecked=false;
				
				if(aniIndex==3 && !attackChecked)//checking if we are at the point of hurting the player
					checkEnemyHit(attackBox,player);
				break;
				
			case HIT:
				break;
			}
		}
			
	}
	
	public int flipX() {
		if(walkDir==RIGHT)
			return width;
		else return 0;
		
	}
	
	public int flipW() {
		if(walkDir==RIGHT)
			return -1;
		else
			return 1;
	}
	
	
}
