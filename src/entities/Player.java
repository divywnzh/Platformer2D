package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	
	private BufferedImage[][] animations; 
	private int aniTick, aniIndex, aniSpeed=15; // 120/8 = 30 (8 animations per second)
	
	private int playerAction=IDLE;
	private boolean moving=false, attack=false;
	private boolean left,up,right,down,jump;
	private float playerSpeed=2.0f;
	
	private int[][] lvlData;
	private float xDrawOffset=21*Game.SCALE;//calculated for new hitbox (packs in the player)
	private float yDrawOffset=4*Game.SCALE;
	
	//jumping & gravity
	private float airSpeed=0f;
	private float gravity=0.04f*Game.SCALE;
	private float jumpSpeed=-2.25f*Game.SCALE;
	private float fallSpeedAfterCollision=0.5f*Game.SCALE;
	private boolean inAir=false;
	
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);//calls the super class //think of it as shortcut -> no need to code as much
		loadAnimations();
		initHitbox(x,y,20*Game.SCALE,27*Game.SCALE);
	}
	
	public void update() {
		
		updatePos();
		updateAnimationTick();
		setAnimation();
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
		drawHitbox(g);
	}
	

	public void updateAnimationTick() {
		aniTick++;
		if(aniTick>=aniSpeed) {
			aniTick=0;
			aniIndex++;
			if(aniIndex>=GetSpriteAmount(playerAction)) {
				aniIndex=0;
				attack=false;
			}
		}
	}
	
	private void resetAniTick() {
		aniTick=0;
		aniIndex=0;
	}
	
	private void setAnimation() {
		
		
		int startAni=playerAction;
		
		if(moving) {
			playerAction=RUNNING;
		}
		else {
			playerAction=IDLE;
		}
		
		//animation for in air :)
		if(inAir) {
			if(airSpeed<0)//going up
				playerAction=JUMP;
			else
				playerAction=FALLING;
		}
		
		if(attack) {
			playerAction=ATTACK_1;
		}
		
		//resetting aniIndex,aniTick for switching animations
		if(startAni!=playerAction)
			resetAniTick();
			
		
	}
	
	private void updatePos() {
		
		moving=false;
		if(jump) {
			jump();
		}
		if(!left && !right && !inAir)
			return;
		
		float xSpeed=0;
		
		if(left)  //if either of A or D is pressed
			xSpeed-=playerSpeed;
		if(right)
			xSpeed+=playerSpeed;
		
		if (!inAir) //Checks for inAir without SPACEBAR being pressed
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;
		
		if(inAir) {//if we have space in up and down directions
			if(CanMoveHere(hitbox.x,hitbox.y+airSpeed,hitbox.width,hitbox.height,lvlData)) {
				hitbox.y+=airSpeed;
				airSpeed+=gravity;
				updateXPos(xSpeed);
			}else {//can not move up or down. Either hitting the roof or the floor
				hitbox.y=GetEntityYPosUnderRoofOrAboveFloor(hitbox,airSpeed);
				if(airSpeed>0) 
					resetInAir();
				else 
					airSpeed=fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		}else {
			updateXPos(xSpeed);
		}
		moving=true;

	}
	
	private void jump() {
		if(inAir)
			return;
		inAir=true;
		airSpeed=jumpSpeed;
	}
	private void resetInAir() {
		inAir=false;
		airSpeed=0;
	}
	
	private void updateXPos(float xSpeed) {
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
		hitbox.x+=xSpeed;
		}else { //can't move but still space between player(hitbox) and wall
			hitbox.x=GetEntityXPosNextToWall(hitbox,xSpeed);
		}
	}
	
	
	private void loadAnimations() {
		
		BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
		animations=new BufferedImage[9][6]; //9 rows, 6 columns
		
		for(int j=0;j<animations.length;j++) {
			for(int i=0;i<animations[j].length;i++) {
				animations[j][i]=img.getSubimage(i*64, j*40, 64, 40);
			}
		}

	}
	
	public void loadlvlData(int[][] lvlData) {
		this.lvlData=lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData)) //takes cares for starting position
			inAir = true;
	}
	
	public void resetDirBooleans() {
		left=false;
		up=false;
		right=false;
		down=false;
		
	}
	
	public void setAttack(boolean attack) {
		this.attack=attack;
	}
	
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	

}
