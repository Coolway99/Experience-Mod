package coolway99.experiencemod.blocks;

import coolway99.experiencemod.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public abstract class IModBlock extends Block{
	
	private final ItemBlock itemBlock;

	public IModBlock(Material blockMaterial, MapColor blockMapColor, String uniqueName){
		super(blockMaterial, blockMapColor);
		this.setRegistryName(uniqueName);
		this.setUnlocalizedName(Main.MODID+'.'+uniqueName);
		this.itemBlock = new ItemBlock(this);
	}
	
	public ItemBlock getItemBlock(){
		return this.itemBlock;
	}
	
}
