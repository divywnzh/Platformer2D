package utilz;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import main.Game;

public class HelpMethods {
	
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		
		if(!IsSolid(x,y,lvlData))
			if(!IsSolid(x+width,y+height,lvlData))
				if(!IsSolid(x+width,y,lvlData))
					if(!IsSolid(x,y+height,lvlData))
						return true;
		
		
		return false;
		
	}
	
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		
		int maxWidth=lvlData[0].length*Game.TILES_SIZE;
		//before we were using x>=Game.GAME_WIDTH
		
		if(x<0 || x>=maxWidth)
			return true;
		if(y<0 || y>=Game.GAME_HEIGHT)
			return true;
		
		float xIndex=x/Game.TILES_SIZE;
		float yIndex=y/Game.TILES_SIZE;
		
		return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
	}
	
	public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
		int value=lvlData[(int)yTile][(int)xTile];
		
		if(value  == LoadSave.TILE_SOLID || value<0 || value!=11) 
			return true; //IsSolid  ✅ 
		
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile=(int)(hitbox.x/Game.TILES_SIZE);
		
		int tileXPos=currentTile*Game.TILES_SIZE;
		int xOffset=(int)(Game.TILES_SIZE-hitbox.width);
		
		if(xSpeed>0) {//Right
			return tileXPos+xOffset-1;
		}else {//Left
			return tileXPos;
		}
	}
	
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile=(int)(hitbox.y/Game.TILES_SIZE);
		
		int tileYPos=currentTile*Game.TILES_SIZE;
		int yOffset=(int)(Game.TILES_SIZE - hitbox.height);
		
		if(airSpeed>0){//Falling - Touching Floor
			return tileYPos+yOffset-1;
		}else {//JUMPING
			return tileYPos;
		}
	}
	
	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
				return false;
		return true;
	}
	
	public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
		return IsSolid(hitbox.x+xSpeed, hitbox.y+hitbox.height+1, lvlData);
	}
	
	public static boolean IsAllTilesWalkable(int xEnemyTile, int xPlayeTile, int y, int[][] lvlData) {
	    
		int xStart = Math.min(xEnemyTile, xPlayeTile);
	    int xEnd = Math.max(xEnemyTile, xPlayeTile);
	    
	    for (int i = xStart; i <= xEnd; i++) {
	        if (IsTileSolid(i, y, lvlData)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float EnemyHitbox, Rectangle2D.Float PlayeHitbox, int yTile) {
	    int EnemyXTile = (int) (EnemyHitbox.x / Game.TILES_SIZE);
	    int PlayerXTile = (int) (PlayeHitbox.x / Game.TILES_SIZE);

	    return IsAllTilesWalkable(EnemyXTile, PlayerXTile, yTile, lvlData);
	}


}
