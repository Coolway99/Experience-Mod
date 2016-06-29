package coolway99.experiencemod.xp;

import java.util.concurrent.Callable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class XpCapability implements ICapabilitySerializable<NBTTagCompound>{
	
	@CapabilityInject(XpHandler.class) //TODO perhaps change the class to itself
	public static final Capability<XpCapability> INSTANCE = null;
	
	private static final String handlerTypeKey = "handlerType"; //TODO
	private static final String handlerKey = "handler";
	private static final String invKey = "inventory"; //TODO
	private final XpHandler handler;
	
	
	public XpCapability(EntityPlayer player){
		if(player.worldObj.isRemote){
			this.handler = new XpHandlerClient(player);
			return;
		}
		this.handler = new XpHandler(player);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability != null && capability == INSTANCE;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(this.hasCapability(capability, facing)) return INSTANCE.cast(this);
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT(){
		NBTTagCompound data = new NBTTagCompound();
		data.setTag(handlerKey, this.handler.serializeNBT());
		return data;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		this.handler.deserializeNBT(nbt.getCompoundTag(handlerKey));
	}
	
	public void tick(){
		this.handler.tick();
	}
	
	public void onXpAdd(int amount){
		this.handler.onXpAdd(amount);
	}
	
	public void onXpLevel(int levels){
		this.handler.onXpLevel(levels);
	}
	
	public static void register(){
		CapabilityManager.INSTANCE.register(XpHandler.class, //TODO see TODO at the top
				(new IStorage<XpHandler>(){
					@Override
					public NBTBase writeNBT(Capability<XpHandler> capability, XpHandler instance,
							EnumFacing side){return null;}

					@Override
					public void readNBT(Capability<XpHandler> capability, XpHandler instance,
							EnumFacing side, NBTBase nbt){/*Unused*/}
				}),
				(new Callable<XpHandler>(){
					@Override
					public XpHandler call() throws Exception{
						return null;
					}
				}));
	}

	public void onDeath(){
		this.handler.onDeath();
	}
	
	public XpHandler getHandler(){
		return this.handler;
	}
}