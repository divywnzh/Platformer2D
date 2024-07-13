package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPLayerSpawn;

public class Level {
	
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby> crabs; //for each level
	private ArrayList<Potion> potions;
	private ArrayList<Spike> spikes;
	private ArrayList<GameContainer> containers;
	private ArrayList<Cannon> cannons;
	
	private int lvlTilesWide;// we don't want to move bg any more than we have -> we don't want to show a blank scene
	private int maxTilesOffset; // remaining space 
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img=img;//Level Image
		createLevelData();
		createEnemies();
		createPotions();
		createContainers();
		createSpikes();  
		createCannons();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	//Level Image passed to Objects/Players/Enemies for the spawn point reference(RGB value)
	private void createCannons() {
		cannons=HelpMethods.GetCannons(img);
		
	}

	private void createSpikes() {
		spikes=HelpMethods.GetSpikes(img);
	}

	private void createContainers() {
		containers=HelpMethods.GetContainers(img);		
	}

	private void createPotions() {
		potions=HelpMethods.GetPotions(img);		
	}

	private void calcPlayerSpawn() {
		playerSpawn=GetPLayerSpawn(img);
	}

	private void calcLvlOffsets() {
		lvlTilesWide=img.getWidth();
		maxTilesOffset=lvlTilesWide-Game.TILES_IN_WIDTH;
		maxLvlOffsetX=Game.TILES_SIZE*maxTilesOffset;
		
	}

	private void createEnemies() {
		crabs=GetCrabs(img);
	}

	private void createLevelData() {
		lvlData=GetLevelData(img);
	}
	
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
	public ArrayList<Potion> getPotions(){
		return potions;
	}
	
	public ArrayList<GameContainer> getContainers(){
		return containers;
	}
	
	public ArrayList<Spike> getSpikes(){
		return spikes;
	}
	
	public ArrayList<Cannon> getCannons(){
		return cannons;
	}
}
