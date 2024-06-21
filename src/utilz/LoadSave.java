package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {// this class does not have any objects so we don't need constructor
	
	
	public static final String PLAYER_ATLAS="player_sprites.png";
	public static final String LEVEL_ATLAS="outside_sprites.png";
//public static final String LEVEL_ONE_DATA="level_one_data.png";//
	public static final String LEVEL_ONE_DATA="level_one_data_long.png";

	public static final String MENU_BUTTONS="button_atlas.png";
	public static final String MENU_BACKGROUND="menu_background.png"; //alternative to menu_background.png
	public static final String PAUSE_BACKGROUND="pause_menu.png";
	public static final String SOUND_BUTTONS="sound_button.png";
	public static final String URM_BUTTONS="urm_buttons.png";
	public static final String VOLUME_BUTTONS="volume_buttons.png";
	public static final String MENU_BG_IMG="starter_bg.jpg";
	
	
	public static final int TILE_SOLID = 0;
//	public static final int TILE_SPACE_PRESENT = 1;  

	public static BufferedImage GetSpriteAtlas(String file){ //we only need static methods
		
		BufferedImage img=null;
		InputStream is = LoadSave.class.getResourceAsStream("/"+file);
		
		try {
			img=ImageIO.read(is);
			
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
		return img;
	}
	public static int[][] getLevelData(){
		
		BufferedImage img=GetSpriteAtlas(LEVEL_ONE_DATA);
//		int[][] lvlData=new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH]; //for leve_one_data.png
		int[][] lvlData=new int[img.getHeight()][img.getWidth()]; // leve_one_data_long.png
		
		for(int j=0;j<img.getHeight();j++) {
			for(int i=0;i<img.getWidth();i++) {
				Color color=new Color(img.getRGB(i, j));
				int value=color.getRed();
				if(value>=48)
					value=TILE_SOLID; //0 Value assigned to solid tiles 
				
				lvlData[j][i]=value;
			}	
		}return lvlData;
	}
}
