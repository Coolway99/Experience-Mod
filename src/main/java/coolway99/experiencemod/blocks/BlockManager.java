package coolway99.experiencemod.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockManager{
	
	public static DummyBlock dummyBlock;
	
	public static void createBlocks(){
		dummyBlock = new DummyBlock("dummyBlock");
	}
	
	public static void registerBlocks(){
		registerBlock(dummyBlock);
	}
	
	private static void registerBlock(IBlockMod block){
		GameRegistry.register(block);
		GameRegistry.register(block.getItemBlock(), block.getRegistryName());
	}
}
