package coolway99.experiencemod;

import coolway99.experiencemod.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("static-method")
@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.MODVERSION)
public class Main{
	public static final String MODID = "expmod";
	public static final String MODNAME = "Experience Mod";
	public static final String MODVERSION = "0.0";
	public static final String MODPATH = "coolway99.experiencemod";
	public static final String PROXY_COMMON = MODPATH+".proxy.ClientProxy";
	public static final String PROXY_SERVER = MODPATH+".proxy.ServerProxy";
	@Instance
	public static Main instance = new Main();
	
	@SidedProxy(clientSide = PROXY_COMMON, serverSide = PROXY_SERVER)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e){
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		proxy.postInit(e);
	}

}
