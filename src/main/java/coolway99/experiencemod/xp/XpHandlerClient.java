package coolway99.experiencemod.xp;

import coolway99.experiencemod.NetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class XpHandlerClient extends XpHandler{

	public XpHandlerClient(EntityPlayer player){
		super(player);
		System.out.println("This is a client handler");
		this.level = -26;
		this.experience = -53;
	}
	
	@Override
	public NBTTagCompound serializeNBT(){
		return null;
	}
	
	@Override
	public void updatePlayerExp(){
		this.updatePlayerExpNoSync();
	}
	
	@Override
	public void sync(){
		System.out.println("Sending out a ping");
		NetworkHandler.INSTANCE.sendToServer(this);
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		//Unused, the client only syncs to the server
	}
	
	@Override
	public void tick(){
		//Unused, the client serves
	}
	
	@Override
	public void onDeath(){
		this.sync();
	}
	
	@Override
	public void onXpAdd(int amount){
		//Unused
	}
	
	@Override
	public void onXpLevel(int levels){
		//Unused
	}
	
	@Override
	public IMessage onMessage(XpHandler message, MessageContext ctx){
		System.out.println("Message got on client");
		return super.onMessage(message, ctx);
	}
}
