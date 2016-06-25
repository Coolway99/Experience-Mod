package coolway99.experiencemod.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public abstract class IItemSubtypes extends IItemMod{
	
	public final int SUBTYPES;

	public IItemSubtypes(String uniqueName, int subtypes){
		super(uniqueName);
		this.setHasSubtypes(true);
		this.SUBTYPES = subtypes;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> subItems){
		for(int x = 0; x < this.SUBTYPES; x++){
			subItems.add(new ItemStack(item));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		return this.getUnlocalizedName()+stack.getItemDamage();
	}
	
	@Override
	public void initModel(){
		final ArrayList<ModelResourceLocation> list = new ArrayList<>();
		for(int x = 0; x < this.SUBTYPES; x++){
			list.add(new ModelResourceLocation(getRegistryName()+"/"+x, "inventory"));
		}
		ModelBakery.registerItemVariants(this, list.toArray(new ModelResourceLocation[this.SUBTYPES]));
		
		ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition(){
			
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack){
				return list.get(stack.getItemDamage());
			}
		});
	}
	
}
