package coolway99.experiencemod.items;

import coolway99.experiencemod.Main;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class IItemMod extends Item{
	
	public IItemMod(String uniqueName){
		this.setRegistryName(Main.MODID, uniqueName);
		this.setUnlocalizedName(uniqueName);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
	}
}