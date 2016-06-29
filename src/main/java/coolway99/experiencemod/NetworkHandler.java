package coolway99.experiencemod;

import coolway99.experiencemod.xp.XpMessage;
import coolway99.experiencemod.xp.XpPing;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
	private static int id = 0;
	
	public static void register(){
		//Sync
		INSTANCE.registerMessage(XpMessage.class, XpMessage.class, id++, Side.CLIENT);
		//Client to Server
		//Basically a ping to let the server know when EntityPlayerSP is done loading
		INSTANCE.registerMessage(XpPing.class, XpPing.class, id++, Side.SERVER);
		//Server to client
		//A ping to let the client know when EntityPlayerMP is done loading
		INSTANCE.registerMessage(XpPing.class, XpPing.class, id++, Side.CLIENT);
	}
}
