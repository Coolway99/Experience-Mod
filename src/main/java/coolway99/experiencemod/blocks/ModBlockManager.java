package coolway99.experiencemod.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlockManager{
	
	public static DummyBlock dummyBlock;
	public static BlockTransformer blockTransformer;
	
	public static void createBlocks(){
		dummyBlock = new DummyBlock("dummyBlock");
		blockTransformer = new BlockTransformer("transformer");
	}
	
	public static void registerBlocks(){
		registerBlock(dummyBlock);
		registerBlock(blockTransformer);
	}
	
	private static void registerBlock(IModBlock block){
		GameRegistry.register(block);
		GameRegistry.register(block.getItemBlock(), block.getRegistryName());
	}
}
