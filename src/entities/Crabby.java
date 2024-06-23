package entities;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;
import static utilz.HelpMethods.IsFloor;

import main.Game;

public class Crabby extends Enemy {
	
//only the x and y will be different for each crabby. The width and height remains the same
//	public Crabby(float x, float y, int width, int height, int enemyType) {
	public Crabby(float x, float y) {
		
		super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
		initHitbox(x,y,(int)(22*Game.SCALE),(int)(19*Game.SCALE));

	}
	
	public void update(int[][] lvlData, Player player) {
		updateMove(lvlData, player);
		updateAnimationTick();
	}
	
	
	private void updateMove(int[][] lvlData, Player player) {
		if(firstUpdate) {
			firstUpdateCheck(lvlData);//Method in Enemy
		}
		if(inAir) {
			updateInAir(lvlData);//Method in Enemy
		}else {
			switch(enemyState) {
			case IDLE:
				newState(RUNNING);
				break;
			case RUNNING:
				
				if(canSeePlayer(lvlData, player))
					turnTowardsPlayer(player);
				if(isPlayerCloseForAttack(player))
					newState(ATTACK);
				move(lvlData);//Method in Enemy
				break;
			}
		}
			
	}
	
	
	
}
