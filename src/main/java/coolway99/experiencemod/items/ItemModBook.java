package coolway99.experiencemod.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemModBook extends IItemMod{
	
	public ItemModBook(String uniqueName){
		super(uniqueName);
	}
	
	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand){
		//TODO Work on mod first, book later
		return EnumActionResult.SUCCESS;
	}
}