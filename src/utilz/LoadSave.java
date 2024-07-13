package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import static utilz.Constants.EnemyConstants.CRABBY;
import main.Game;

public class LoadSave {// this class does not have any objects so we don't need constructor
	
	
	public static final String PLAYER_ATLAS="player_sprites.png";
	public static final String LEVEL_ATLAS="outside_sprites.png";
//public static final String LEVEL_ONE_DATA="level_one_data.png";//
//public static final String LEVEL_ONE_DATA="level_one_data_long.png";

	public static final String MENU_BUTTONS="button_atlas.png";
	public static final String MENU_BACKGROUND="menu_background.png"; //alternative to menu_background.png
	public static final String PAUSE_BACKGROUND="pause_menu.png";
	public static final String SOUND_BUTTONS="sound_button.png";
	public static final String URM_BUTTONS="urm_buttons.png";
	public static final String VOLUME_BUTTONS="volume_buttons.png";
	public static final String MENU_BG_IMG="starter_bg3.png";
	public static final String PLAYING_BG_IMG="playing_bg_img.png";
	public static final String BIG_CLOUDS="big_clouds.png";
	public static final String SMALL_CLOUDS="small_clouds.png";
	public static final String CRABBY_SPRITE="crabby_sprite.png";
	public static final String STATUS_BAR="health_power_bar.png";
	public static final String COMPLETED_IMG="completed_sprite.png";
	public static final String DEATH_SCREEEN="death_screen.png";

	//Objects
	public static final String POTION_ATLAS="potions_sprites.png";
	public static final String CONTAINER_ATLAS="objects_sprites.png";
	public static final String TRAP_ATLAS="trap_atlas.png";
	public static final String CANNON_ATLAS="cannon_atlas.png";
	public static final String CANNON_BALL="ball.png";

	public static final int TILE_SOLID = 0;//value to be assigned for solid tiles
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
	
	public static BufferedImage[] GetAllLevels() {
		
		URL url=LoadSave.class.getResource("/lvls");
		File file=null;
		
		try {
			file=new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		File[] files=file.listFiles();
		File[] filesSorted=new File[files.length];
		
		for(int i=0;i<filesSorted.length;i++)
			for(int j=0;j<files.length;j++)
				if(files[j].getName().equals(""+(i+1)+".png"))
					filesSorted[i]=files[j];

		BufferedImage[] imgs=new BufferedImage[filesSorted.length];
		for(int i=0;i<imgs.length;i++)
			try {
				imgs[i]=ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return imgs;
	}

}
