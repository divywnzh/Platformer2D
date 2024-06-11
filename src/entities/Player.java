package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.LoadSave.GetSpriteAtlas;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	
	private BufferedImage[][] animations; 
	private int aniTick, aniIndex, aniSpeed=15; // 120/8 = 30 (8 animations per second)
	private int playerAction=IDLE;
	private int playerDir=-1;
	private boolean moving=false, attack=false;
	
	private boolean left,up,right,down;
	private float playerSpeed=2.0f;
	private int[][] lvlData;
	private float xDrawOffset=21*Game.SCALE;//calculated for new hitbox (packs in the player)
	private float yDrawOffset=4*Game.SCALE;
	
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);//calls the super class //think of it as shortcut -> no need to code as much
		loadAnimations();
		initHitbox(x,y,20*Game.SCALE,28*Game.SCALE);
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
		
		if(attack) {
			playerAction=ATTACK_1;
		}
		
		//resetting aniIndex,aniTick for switching animations
		if(startAni!=playerAction)
			resetAniTick();
			
		
	}
	
	private void updatePos() {
		
		moving=false;
		
		if(!left && !right && !up && !down)
			return;
		
		float xSpeed=0,ySpeed=0;
		
		if(left && !right)  //if either of A or D is pressed
			xSpeed=-playerSpeed;
		else if (right && !left)
			xSpeed=playerSpeed;
		
		
		if(up && !down) //if either of W or S is pressed
			ySpeed=-playerSpeed;
		else if (down && !up)
			ySpeed+=playerSpeed;
		
//		if(CanMoveHere(x+xSpeed, y+ySpeed, width, height, lvlData)) {
//			this.x+=xSpeed;
//			this.y+=ySpeed;
//			moving=true;
//		}
		
		if(CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x+=xSpeed;
			hitbox.y+=ySpeed;
			moving=true;
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

	

}
