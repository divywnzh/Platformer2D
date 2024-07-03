package entities;


import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	
	private BufferedImage[][] animations;
	Image statusBarImg; 
	private int aniTick, aniIndex, aniSpeed=25; // 120/8 = 30 (8 animations per second)
	
	private int playerAction=IDLE;
	private boolean moving=false, attack=false;
	private boolean left,up,right,down,jump;
	private float playerSpeed=1.0f*Game.SCALE;
	
	private int[][] lvlData;
	private float xDrawOffset=21*Game.SCALE;//calculated for new hitbox (packs in the player)
	private float yDrawOffset=4*Game.SCALE;
	
	//jumping & gravity
	private float airSpeed=0f;
	private float gravity=0.04f*Game.SCALE;
	private float jumpSpeed=-2.25f*Game.SCALE;
	private float fallSpeedAfterCollision=0.5f*Game.SCALE;
	private boolean inAir=false;
	
	//StatusBarUI
	private int statusBarWidth=(int)(192*Game.SCALE);
	private int statusBarHeight=(int)(58*Game.SCALE);
	private int statusBarX=(int)(10*Game.SCALE);
	private int statusBarY=(int)(10*Game.SCALE);
	
	private int healthBarWidth=(int)(150*Game.SCALE);
	private int healthBarHeight=(int)(4*Game.SCALE);
	private int healthBarXStart=(int)(34*Game.SCALE);
	private int healthBarYStart=(int)(14*Game.SCALE);
	
	private int maxHealth=100;
	private int currentHealth=maxHealth;
	private int healthWidth=healthBarWidth;
	
	//Attack Box
	
	private Rectangle2D.Float attackBox;
	
	private int flipX=0;
	private int flipW=1;
	
	private boolean attackChecked;
	private Playing playing;
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);//calls the super class //think of it as shortcut -> no need to code as much
		this.playing=playing;
		
		loadAnimations();
		initHitbox(x,y,(int)(20*Game.SCALE),(int)(27*Game.SCALE));
		initAttackBox();
	}
	
	private void initAttackBox() {
		attackBox=new Rectangle2D.Float(x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
		
	}

	public void update() {
		
		updateHealthBar();
		if(currentHealth<=0) {
			playing.setGameOver(true);
			return;
		}
		
		updateAttackBox();
		
		updatePos();
		if(attack)
			checkAttack();
		updateAnimationTick();
		setAnimation();
		
	}
	
	private void checkAttack() {
		if(attackChecked || aniIndex!=1)
			return;
		attackChecked=true;
		playing.checkEnemyHit(attackBox);
		
	}

	private void updateAttackBox() {
		if(right) {
			attackBox.x=hitbox.x + hitbox.width + (int)(Game.SCALE*10);
		} else if(left) {
			attackBox.x=hitbox.x - hitbox.width - (int)(Game.SCALE*10);
		}
		
		attackBox.y=hitbox.y+(Game.SCALE*10);
	}
	
	private void updateHealthBar() {
		healthWidth=(int)((currentHealth/(float)maxHealth)*healthBarWidth);
		
	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset) - lvlOffset + flipX, (int)(hitbox.y-yDrawOffset), width*flipW, height, null);
		drawHitbox(g,lvlOffset);
		
		drawAttackBox(g,lvlOffset);
		drawUI(g);
	}
	

	private void drawAttackBox(Graphics g, int lvlOffsetX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBox.x - lvlOffsetX, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);	
	}

	private void drawUI(Graphics g) {
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(Color.red);
		g.fillRect(healthBarXStart+statusBarX, healthBarYStart+statusBarY, healthWidth, healthBarHeight);
	}

	public void updateAnimationTick() {
		aniTick++;
		if(aniTick>=aniSpeed) {
			aniTick=0;
			aniIndex++;
			if(aniIndex>=GetSpriteAmount(playerAction)) {
				aniIndex=0;
				attack=false;
				attackChecked=false;
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
			playerAction=ATTACK;
			if(startAni != ATTACK) {
				aniIndex=1;//faster attack -> start from sprite at index 1
				aniTick=0;
				return;
			}
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
		
		if(left)  //if either of A or D is pressed{
		{
			xSpeed-=playerSpeed;
			flipX=width;
			flipW=-1;
		}
		
		if(right) {
			xSpeed+=playerSpeed;
			flipX=0;
			flipW=1;
		}

		if (!inAir) //Checks for inAir without SPACEBAR being pressed
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		
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
		}else 
			updateXPos(xSpeed);
		
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
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) 
			hitbox.x+=xSpeed;
		else  //can't move but still space between player(hitbox) and wall
			hitbox.x=GetEntityXPosNextToWall(hitbox,xSpeed);
		
	}
	
	public void changeHealth(int value) {
		
		currentHealth+=value;
		if(currentHealth<=0) 
			currentHealth=0;
		else if(currentHealth>=maxHealth) 
			currentHealth=maxHealth;
		
		
	}
	
	private void loadAnimations() {
		
		BufferedImage img=LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			
		animations=new BufferedImage[7][8]; //7 rows, 8 columns
		
		for(int j=0;j<animations.length;j++) {
			for(int i=0;i<animations[j].length;i++) {
				animations[j][i]=img.getSubimage(i*64, j*40, 64, 40);
			}
		}
		
		statusBarImg=LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);

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

	public void resetAll() {
		
		resetDirBooleans();
		inAir=false;
		attack=false;
		moving=false;
		playerAction=IDLE;
		currentHealth=maxHealth;
		
		hitbox.x=x;
		hitbox.y=y;
		
		if (!IsEntityOnFloor(hitbox, lvlData)) //takes cares for starting position
			inAir = true;
		
	}

	

}
