package coolway99.experiencemod.xp;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * The class used for sending pings back and forth, {@link XpHandler} won't work unless both sides have pinged.
 */
public class XpPing implements IMessage, IMessageHandler<XpPing, IMessage>{
	
	private final EntityPlayer player;
	private int dimension;
	private String playerName;
	/**
	 * Will be true if we are server-side, false if client-side
	 */
	private boolean isServer;
	
	public XpPing(){
		this.player = null;
	}
	
	public XpPing(EntityPlayer player){
		this.player = player;
	}
	
	@Override
	public IMessage onMessage(XpPing message, MessageContext ctx){
		if(message.isServer){
			//If serverside
			//This is the client telling us it's done making the player obj
			System.out.println("We got a server-side ping");
			System.out.println("The message came from player "+message.playerName);
			EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance()
					.worldServerForDimension(message.dimension).getPlayerEntityByName(message.playerName);
			if(player == null || !player.hasCapability(XpCapability.INSTANCE, null)) return null;
			//If we have the player obj, then sync
			player.getCapability(XpCapability.INSTANCE, null).getHandler().updatePlayerExp();
			return null;
		}
		//If clientside
		System.out.println("We got a client-side ping");
		//If this is client-side, then the server has pinged us saying that it's done making the player obj
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(player == null || !player.hasCapability(XpCapability.INSTANCE, null)) return null;
		//If we have a player obj, tell the server. The server may not have one yet, though
		Minecraft.getMinecraft().thePlayer.getCapability(XpCapability.INSTANCE, null).getHandler().ping();
		return null;
	}

	@Override
	public void toBytes(ByteBuf buf){
		/*if(this.player == null){
			buf.writeBoolean(true);
			return;
		}
		buf.writeBoolean(false);*/
		buf.writeInt(this.player.dimension);
		ByteBufUtils.writeUTF8String(buf, this.player.getName());
		buf.writeBoolean(this.player.worldObj.isRemote);
	}
	
	@Override
	public void fromBytes(ByteBuf buf){
		//if(buf.readBoolean()) return;
		this.dimension = buf.readInt();
		this.playerName = ByteBufUtils.readUTF8String(buf);
		this.isServer = buf.readBoolean(); //True if it came from the client, False if it came from the server
	}

}
