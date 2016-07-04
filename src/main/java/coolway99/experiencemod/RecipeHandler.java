package coolway99.experiencemod;

import coolway99.experiencemod.items.ModItemManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RecipeHandler{

	@SuppressWarnings("boxing")
	public static void registerRecipes(){
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItemManager.mysticalPaper),
				"GL ",
				"LPL",
				" LG",
				'G', "dustGlowstone",
				'L', "gemLapis",
				'P', new ItemStack(Items.PAPER)
				));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItemManager.mysticalPaper, 1, 2),
				"PPP",
				"PPP",
				"PPP",
				'P', new ItemStack(ModItemManager.mysticalPaper, 1, 1)
				));
		GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(ModItemManager.mysticalPaper, 1, 3),
				"LLL",
				"LPL",
				"LLL",
				'L', "gemLapis",
				'P', new ItemStack(ModItemManager.mysticalPaper, 1, 2)
				));
	}
}
