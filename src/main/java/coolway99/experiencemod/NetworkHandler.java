package coolway99.experiencemod;

import coolway99.experiencemod.xp.XpHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
	private static int id = 0;
	
	public static void register(){
		//First sync
		//Basically a ping to let the server know when EntityPlayerSP is done loading
		INSTANCE.registerMessage(XpHandler.class, XpHandler.class, id++, Side.SERVER);
		//Sync
		INSTANCE.registerMessage(XpHandler.class, XpHandler.class, id++, Side.CLIENT);
	}
}
