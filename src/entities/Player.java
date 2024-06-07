package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.ATTACK_1;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Player extends Entity {
	
	private BufferedImage[][] animations; 
	private int aniTick, aniIndex, aniSpeed=15; // 120/8 = 30 (8 animations per second)
	private int playerAction=IDLE;
	private int playerDir=-1;
	private boolean moving=false, attack=false;
	
	private boolean left,up,right,down;
	private float playerSpeed=2.0f;
	
	public Player(float x, float y) {
		super(x, y);//calls the super class //think of it as shortcut -> no need to code as much
		loadAnimations();
	}
	
	public void update() {
		
		updatePos();
		updateAnimationTick();
		setAnimation();
		
	}
	
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 256, 160, null);
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
		
		if(left && !right) { //if either of A or D is pressed
			x-=playerSpeed;
			moving=true;
		}else if (right && !left){
			x+=playerSpeed;
			moving=true;
		}
		
		if(up && !down) { //if either of W or S is pressed
			y-=playerSpeed;
			moving=true;
		}else if (down && !up){
			y+=playerSpeed;
			moving=true;
		}
	
	}
	
	private void loadAnimations() {
		
		InputStream is = getClass().getResourceAsStream("/player_sprites.png");
		
		try {
			BufferedImage img=ImageIO.read(is);
			
			animations=new BufferedImage[9][6]; //9 rows, 6 columns
			
			for(int j=0;j<animations.length;j++) {
				for(int i=0;i<animations[j].length;i++) {
					animations[j][i]=img.getSubimage(i*64, j*40, 64, 40);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
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
