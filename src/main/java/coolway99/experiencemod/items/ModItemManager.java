package coolway99.experiencemod.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemManager{
	
	public static ItemMysticalPaper mysticalPaper;
	public static Item modBook;
	public static ItemDragonBall dragonBall;
	public static ItemMobMaker mobMaker;

	public static void createItems(){
		mysticalPaper = new ItemMysticalPaper("mysticalPaper");
		dragonBall = new ItemDragonBall("dragonBall");
		mobMaker = new ItemMobMaker("mobMaker");
	}
	
	public static void registerItems(){
		GameRegistry.register(mysticalPaper);
		GameRegistry.register(dragonBall);
		GameRegistry.register(mobMaker);
	}
	
	@SideOnly(Side.CLIENT)
	public static void initModels(){
		mysticalPaper.initModel();
		dragonBall.initModel();
		mobMaker.initModel();
	}
}
