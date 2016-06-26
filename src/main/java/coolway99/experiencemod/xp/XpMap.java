package coolway99.experiencemod.xp;

public class XpMap{
	
	public static int getExpForLevel(int level){
		if(level > 999 || level < 0){
			return Integer.MAX_VALUE;
		}
		return (level*level)-level+10;
	}
	
	//TODO there should be a better mathematical way to do this then overloading the stack
	public static int getExpAllLevels(int level){
		if(level > 999 || level < 0) return Integer.MAX_VALUE;
		if(level == 0) return getExpForLevel(level);
		int result = 0;
		for(; level > 0; level--){
			result += getExpForLevel(level);
		}
		return result;
	}
}
