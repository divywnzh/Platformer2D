package utilz;

public class Constants {
	
	public static class Directions{
		public static final int LEFT=0;
		public static final int UP=1;
		public static final int RIGHT=2;
		public static final int DOWN=3;
	}
	public static class PlayerConstants{
		public static final int RUNNING=0;
		public static final int IDLE=3;
		public static final int JUMP=8;
		public static final int FALLING=7;
		public static final int GROUND=6;
		public static final int HIT=4;
		public static final int ATTACK_1=1;
		public static final int ATTACK_JUMP_1=2;
		public static final int ATTACK_JUMP_2=5;
		
		public static int GetSpriteAmount(int player_action) {
			switch(player_action) {
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
			case ATTACK_1:
			case ATTACK_JUMP_1:
			case ATTACK_JUMP_2:
				return 3;
			case GROUND:
				return 2;
			case FALLING:
			default:
				return 1;
			
			}
		}
	}

}
