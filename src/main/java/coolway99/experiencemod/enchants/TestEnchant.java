package coolway99.experiencemod.enchants;

import coolway99.experiencemod.items.ItemModBook;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class TestEnchant extends Enchantment{

	public TestEnchant(){
		super(Rarity.COMMON, EnumEnchantmentType.ALL, null);
		this.setRegistryName("testEnchant");
		this.setName("testEnchant");
	}
	
	@Override
	public boolean canApply(ItemStack stack){
		return this.canApplyAtEnchantingTable(stack);
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack){
		System.out.println("Testing "+stack.getUnlocalizedName());
		System.out.println(stack.getItem() instanceof ItemModBook);
		return stack.getItem() instanceof ItemModBook;
	}
	
	@Override
	public boolean canApplyTogether(Enchantment ench){
		return false;
	}
	
	@Override
	public int getMaxLevel(){
		return 1;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel){
		return 1;
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel){
		return 1;
	}
}
