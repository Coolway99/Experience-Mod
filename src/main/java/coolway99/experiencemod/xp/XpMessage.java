package coolway99.experiencemod.xp;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class XpMessage implements IMessage, IMessageHandler<XpMessage, IMessage>{
	
	private final EntityPlayer player;
	private final XpHandler handler;
	private String playerName;
	private int dimension;
	private ByteBuf buf;
	
	//This is called when this class is reading the message
	public XpMessage(){
		this.player = null;
		this.handler = null;
	}
	
	public XpMessage(EntityPlayer player, XpHandler handler){
		this.player = player;
		this.handler = handler;
	}
	
	@Override
	public IMessage onMessage(XpMessage message, MessageContext ctx){
		//This takes place on the client side
		System.out.println("We got a message");
		System.out.println("The message has player "+message.playerName);
		XpHandler handler = Minecraft.getMinecraft().thePlayer.getCapability(XpCapability.INSTANCE, null)
				.getHandler();
		handler.fromBytes(message.buf);
		return null;
	}

	@Override
	public void toBytes(ByteBuf buf){
		buf.writeInt(this.player.dimension);
		ByteBufUtils.writeUTF8String(buf, this.player.getName());
		this.handler.toBytes(buf);
	}
	
	@Override
	public void fromBytes(ByteBuf buf){
		this.dimension = buf.readInt();
		this.playerName = ByteBufUtils.readUTF8String(buf);
		this.buf = buf;
	}
}