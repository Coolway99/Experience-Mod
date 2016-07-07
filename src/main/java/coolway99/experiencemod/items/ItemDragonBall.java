package coolway99.experiencemod.items;

import coolway99.experiencemod.xp.XpCapability;
import coolway99.experiencemod.xp.XpHandler;
import coolway99.experiencemod.xp.XpMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

//A ball of congealed dragon
public class ItemDragonBall extends IItemMod{

	public ItemDragonBall(String uniqueName){
		super(uniqueName);
	}
	
	//This function is just for testing
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		if(world.isRemote) return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		if(!(player.hasCapability(XpCapability.INSTANCE, null))) return new ActionResult<>(EnumActionResult.FAIL, stack);
		XpHandler handler = player.getCapability(XpCapability.INSTANCE, null).getHandler();
		if(++handler.enderExperience >= XpMap.getExpForLevel(handler.enderLevel)){
			handler.enderLevel++;
			handler.enderExperience = 0;
		}
		handler.sync();
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}
}
