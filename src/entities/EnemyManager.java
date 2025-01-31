package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsEntityOnFloor;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;

public class EnemyManager {
	
	private Playing playing;
	private BufferedImage[][] crabbyArr;//animations
	private ArrayList<Crabby> crabbies=new ArrayList<>();
	
	public EnemyManager(Playing playing) {
		this.playing=playing;
		loadEnemyImgs();
//		addEnemies(); -> is not LoadEnemies (public) called from outside
	}
	
	public void LoadEnemies(Level level) {
		crabbies=level.getCrabs();
		System.out.println("NO OF CRABS : "+crabbies.size());
		
	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive=false;
		for (Crabby c : crabbies)
			if(c.isActive()) {
				c.update(lvlData, player);
				isAnyActive=true;
			}
		if(!isAnyActive)
			playing.setLevelCompleted(true);
	}
	public void draw(Graphics g, int xLvlOffset) {
		drawCrabs(g, xLvlOffset);
	}

	private void drawCrabs(Graphics g, int xLvlOffset) {
		for (Crabby c : crabbies) 
//			System.out.println(c.getEnemyState());
			if(c.isActive()) {
				g.drawImage(crabbyArr[c.getState()][c.getAniIndex()], (int)(c.getHitbox().x - xLvlOffset - CRABBY_DRAWOFFSET_X + c.flipX()), (int)(c.getHitbox().y - CRABBY_DRAWOFFSET_Y), CRABBY_WIDTH*c.flipW(), CRABBY_HEIGHT, null);
//				c.drawHitbox(g, xLvlOffset);
//				c.drawAttackBox(g, xLvlOffset);
			}
		
	}
	
	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for(Crabby c : crabbies)
			if(c.isActive()) 
				if(attackBox.intersects(c.getHitbox())) {
					c.hurt(10);
					return;
				}	
	}

	private void loadEnemyImgs() {
		crabbyArr=new BufferedImage[5][9];
		BufferedImage temp=LoadSave.GetSpriteAtlas(LoadSave.CRABBY_SPRITE);
		for(int j=0;j<crabbyArr.length;j++)
			for(int i=0;i<crabbyArr[j].length;i++)
				crabbyArr[j][i]=temp.getSubimage(i*CRABBY_WIDTH_DEFAULT, j*CRABBY_HEIGHT_DEFAULT, CRABBY_WIDTH_DEFAULT, CRABBY_HEIGHT_DEFAULT);
	}
	
	public void resetAllEnemies() {
		for(Crabby c: crabbies)
			c.resetEnemy();
	}
	
}
