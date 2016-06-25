package coolway99.experiencemod.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class DummyBlock extends IBlockMod{
	
	public DummyBlock(String uniqueName){
		super(Material.CLAY, MapColor.BLUE, uniqueName);
	}
}
