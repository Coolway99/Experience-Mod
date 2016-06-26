package coolway99.experiencemod.xp;

import coolway99.experiencemod.NetworkHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

//The main class to be used to manage each player's EXP
public class XpHandler implements INBTSerializable<NBTTagCompound>, IMessage, IMessageHandler<XpHandler, IMessage>{
	//Begin constants
	private static final String levelKey = "level";
	private static final String experienceKey = "experience";
	private static final String versionKey = "version";
	//Begin non-static part of the class
	protected final EntityPlayer player;
	private String playerName; // Used for message sending
	private int dimension; //Used for message sending
	protected int level;
	protected int experience;
	
	//This should only be called when this is a message handler
	public XpHandler(){
		this.player = null;
	}
	
	public XpHandler(EntityPlayer player){
		this.player = player;
		System.out.println(player.worldObj.isRemote ? "I'm on the client side" : "I'm on the server side!");
	}
	//Load
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		this.level = nbt.getInteger(levelKey);
		this.experience = nbt.getInteger(experienceKey);
		//this.updatePlayerExp();
	}
	//Save
	@Override
	public NBTTagCompound serializeNBT(){
		NBTTagCompound data = new NBTTagCompound();
		data.setInteger(levelKey, this.level);
		data.setInteger(experienceKey, this.experience);
		return data;
	}

	//Called every update
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
	
	protected void updatePlayerExpNoSync(){
		if(this.player == null) return;
		int xpForLevel = XpMap.getExpForLevel(this.level);
		this.player.experience = (float) this.experience / xpForLevel;
		this.player.experienceLevel = this.level;
		this.player.experienceTotal = this.experience + XpMap.getExpAllLevels(this.level);
	}
	
	public void sync(){
		if(this.player == null) return;
		NetworkHandler.INSTANCE.sendTo(this, (EntityPlayerMP) this.player);
		System.out.println("We sent a message");
	}
	
	public void onXpAdd(int amount){
		this.experience += amount;
		while(this.experience >= XpMap.getExpForLevel(this.level)){
			this.experience -= XpMap.getExpForLevel(this.level++);
		}
		this.updatePlayerExp();
	}
	
	public void onXpLevel(int levels){
		this.experience = (int) (((float)this.experience/XpMap.getExpForLevel(this.level))
				* XpMap.getExpForLevel(this.level += levels)); 
		this.updatePlayerExp();
	}
	public void onDeath(){
		this.level -= 5;
		this.updatePlayerExp();
	}
	
	@Override
	public IMessage onMessage(XpHandler message, MessageContext ctx){
		if(message.experience == -53 && message.level == -26){
			//This takes place on the server side
			System.out.println("We got the first ping");
			//Assume it's the class that has a null player
			System.out.println("This player is "+this.player);
			System.out.println("The message came from player "+message.playerName);
			EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance()
					.worldServerForDimension(this.dimension).getPlayerEntityByName(message.playerName);
			player.getCapability(XpCapability.INSTANCE, null).getHandler().updatePlayerExp();
			return null;
		}
		//This takes place on the client side
		System.out.println("We got a message");
		System.out.println("We have player "+this.player);
		System.out.println("The message has player "+message.player+" with playername "+message.playerName);
		XpHandler handler = Minecraft.getMinecraft().thePlayer.getCapability(XpCapability.INSTANCE, null).getHandler();
		handler.level = message.level;
		handler.experience = message.experience;
		handler.updatePlayerExpNoSync();
		return null;
	}
	
	@Override
	public void toBytes(ByteBuf buf){
		buf.writeInt(this.level);
		buf.writeInt(this.experience);
		buf.writeInt(this.player.dimension);
		ByteBufUtils.writeUTF8String(buf, this.player.getName());
	}
	@Override
	public void fromBytes(ByteBuf buf){
		this.level = buf.readInt();
		this.experience = buf.readInt();
		this.dimension = buf.readInt();
		this.playerName = ByteBufUtils.readUTF8String(buf);
	}
}