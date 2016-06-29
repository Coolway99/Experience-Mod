package coolway99.experiencemod.xp;

import coolway99.experiencemod.NetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

//The main class to be used to manage each player's EXP
public class XpHandler implements INBTSerializable<NBTTagCompound>{
	//Begin constants
	private static final String levelKey = "level";
	private static final String powerKey = "power";
	private static final String enderLevelKey = "enderLevel";
	private static final String enderExperienceKey = "enderExperience";
	private static final String versionKey = "version";
	//Begin non-static part of the class
	protected final EntityPlayer player;
	public int level;
	public int power;
	public int enderLevel;
	public int enderExperience;
	
	public XpHandler(EntityPlayer player){
		this.player = player;
		//System.out.println(player.worldObj.isRemote ? "I'm on the client side" : "I'm on the server side!");
	}
	
	//Load
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		this.level = nbt.getInteger(levelKey);
		this.power = nbt.getInteger(powerKey);
		this.enderLevel = nbt.getInteger(enderLevelKey);
		this.enderExperience = nbt.getInteger(enderExperienceKey);
		//this.updatePlayerExp();
	}
	
	//Save
	@Override
	public NBTTagCompound serializeNBT(){
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger(levelKey, this.level);
		data.setInteger(powerKey, this.power);
		data.setInteger(enderLevelKey, this.enderLevel);
		data.setInteger(enderExperienceKey, this.enderExperience);
		return data;
	}

	//Called every player update
	public void tick(){
		if(!this.player.worldObj.isRemote && !this.player.isCreative()
				&& this.player.experienceLevel > 5 && !this.player.isDead){
			//this.player.setHealth(0);
		}
	}
	
	public void updatePlayerExp(){
		//this.updatePlayerExpNoSync();
		this.sync();
	}
	
	//TODO
	protected void updatePlayerExpNoSync(){
		if(this.player == null) return;
		//int xpForLevel = XpMap.getExpForLevel(this.level);
		//this.player.experience = (float) this.experience / xpForLevel;
		//this.player.experienceLevel = this.level;
		//this.player.experienceTotal = this.experience + XpMap.getExpAllLevels(this.level);
	}
	
	public void sync(){
		if(this.player == null) return;
		//We assume server-side
		NetworkHandler.INSTANCE.sendTo(new XpMessage(this.player, this), (EntityPlayerMP) this.player);
		System.out.println("We sent a message");
	}
	
	public void ping(){
		if(this.player == null) return;
		if(this.player.worldObj.isRemote){
			NetworkHandler.INSTANCE.sendToServer(new XpPing(this.player));
		} else {
			NetworkHandler.INSTANCE.sendTo(new XpPing(this.player), (EntityPlayerMP) this.player);
		}
	}
	
	//TODO
	public void onXpAdd(int amount){
		if(this.level == 0){
			this.level++;
			return;
		}
		double experience = (double) this.power / this.level;
		experience += amount;
		while(experience >= XpMap.getExpForLevel(this.level)){
			if(this.level < 5){
				experience -= XpMap.getExpForLevel(this.level++);
			} else {
				experience = XpMap.getExpForLevel(this.level);
				break;
			}
		}
		this.power = (experience == 0.0 ? 1 : (int) Math.round(experience * this.level));
		this.updatePlayerExp();
	}
	
	//TODO
	//Only called when levels are forcefully added
	public void onXpLevel(int levels){
		//this.experience = (int) (((float)this.experience/XpMap.getExpForLevel(this.level))
			//	* XpMap.getExpForLevel(this.level += levels)); 
		this.level += levels;
		this.power = this.level; //AKA 1 EXP
		this.updatePlayerExp();
	}
	public void onDeath(){
		this.level -= 5;
		if(this.level < 0) this.level = 0;
		this.power = this.level; //AKA 1 EXP
		//this.updatePlayerExp();
	}
	
	public void toBytes(ByteBuf buf){
		buf.writeInt(this.level);
		buf.writeInt(this.power);
		buf.writeInt(this.enderLevel);
		buf.writeInt(this.enderExperience);
	}

	public void fromBytes(ByteBuf buf){
		this.level = buf.readInt();
		this.power = buf.readInt();
		this.enderLevel = buf.readInt();
		this.enderExperience = buf.readInt();
	}
}