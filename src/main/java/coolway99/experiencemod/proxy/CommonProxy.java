package coolway99.experiencemod.proxy;

import coolway99.experiencemod.EventInterface;
import coolway99.experiencemod.GuiHandler;
import coolway99.experiencemod.Main;
import coolway99.experiencemod.NetworkHandler;
import coolway99.experiencemod.RecipeHandler;
import coolway99.experiencemod.blocks.ModBlockManager;
import coolway99.experiencemod.items.ModItemManager;
import coolway99.experiencemod.xp.XpCapability;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@SuppressWarnings({"static-method", "unused"})
public class CommonProxy{
	
	public void preInit(FMLPreInitializationEvent event){
		ModBlockManager.createBlocks();
		ModItemManager.createItems();
		ModBlockManager.registerBlocks();
		ModItemManager.registerItems();
		NetworkHandler.register();
	}
	
	public void init(FMLInitializationEvent event){
		RecipeHandler.registerRecipes();
		XpCapability.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.INSTANCE, new GuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new EventInterface());
	}
}
