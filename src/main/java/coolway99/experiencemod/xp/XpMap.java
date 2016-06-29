package coolway99.experiencemod.xp;

public class XpMap{
	
	//They can level up from 0 to 5, so that they can do basic things
	/**
	 * This stores the amount of EXP that the player can have in each level
	 */
	//private int[] expMap = new int[]{1, 5, 10, 25, 60, 100};
	
	
	public static double getExpForLevel(int level){
		/*if(level > 999 || level < 0){
			return Integer.MAX_VALUE;
		}
		return (level*level)-level+10;*/
		int x = level;
		int result = ((int) ((1.5*x*x*x)-(4*x*x)+(6.5*x)+1));
		if(x > 4){
			result = (int) Math.round((double) result / 10)*10;
		}
		return result;
	}
	
	public static int getExp(int level){
		return (int) Math.round(getExpForLevel(level));
	}
	
	public static int getPowerForLevel(int level){
		return level*((int) getExpForLevel(level));
	}
	
	//TODO there should be a better mathematical way to do this then overloading the stack
	//TODO This isn't "needed" once the levels and exp break apart
	public static int getExpAllLevels(int level){
		if(level > 999 || level < 0) return Integer.MAX_VALUE;
		if(level == 0) return getExp(level);
		int result = 0;
		for(; level > 0; level--){
			result += getExp(level);
		}
		return result;
	}
}
