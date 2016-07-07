package coolway99.experiencemod;

import java.util.Iterator;

import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * A class for commonly used utilities
 */
public class ModUtils{
	@CapabilityInject(XpCapability.class)
	public static final Capability<XpCapability> XPCAP = null;
	
	/**
	 * If the player has the capability, return true, otherwise false<br />
	 * This method is here for convince
	 */
	public static boolean hasXpCap(EntityPlayer player){
		return player.hasCapability(XPCAP, null);
	}
	
	/**
	 * If the player has the capability, return it. It will return null otherwise
	 */
	public static XpCapability getXpCap(EntityPlayer player){
		if(hasXpCap(player)) return player.getCapability(XPCAP, null);
		return null;
	}
	
	//Using shorts to avoid the problems that come from signed bytes. dangit java!
	public static int ARGBToInt(short a, short r, short g, short b){
		return (a<<24)|(r<<16)|(g<<8)|b;
	}
	
	//This uses shorts to avoid setting the "sign" bit of bytes
	public static short[] IntToARGB(int color){
		return new short[]{
				//We use a >>> so that we treat the sign bit like a normal bit, insert 0's to the left
				(short) ((color & 0xFF000000) >>> 24),
				(short) ((color & 0xFF0000) >> 16),
				(short) ((color & 0xFF00) >> 8),
				(short) (color & 0xFF)};
	}
	
	//Used for scrubbing an AI of all tasks
	public static void removeClassFromAI(EntityAITasks tasks, Class<? extends EntityAIBase> clazz){
		Iterator<EntityAITaskEntry> iterator = tasks.taskEntries.iterator();
		while(iterator.hasNext()){
			if(clazz.isInstance(iterator.next())){
				iterator.remove();
			}
		}
	}
	
	public static void clearAI(EntityAITasks tasks){
		Iterator<?> iterator = tasks.taskEntries.iterator();
		while(iterator.hasNext()){
			iterator.next();
			iterator.remove();
		}
	}
}
