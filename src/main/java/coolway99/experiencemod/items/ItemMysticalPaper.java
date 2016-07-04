package coolway99.experiencemod.items;

import coolway99.experiencemod.ChatHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMysticalPaper extends IItemSubtypes{
	
	public ItemMysticalPaper(String uniqueName){
		super(uniqueName, 4);
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote){
			this.onItemRightClick(stack, world, player, hand);
			return EnumActionResult.SUCCESS;
		}
		Block block = world.getBlockState(pos).getBlock();
		int amount = stack.stackSize;
		if(block instanceof BlockEnchantmentTable)
		switch(stack.getItemDamage()){
			case 0:{
					if(!stack.isItemEnchanted() && player.experienceLevel >= amount){
						player.removeExperienceLevel(amount);
						stack.setItemDamage(1);
						return EnumActionResult.SUCCESS;
					}
					ChatHelper.UntText(player, "fail0");
					break;
				}
			case 2:{
				ChatHelper.UntText(player, "fail1");
				break;
			}
			case 3:{
				if(player.experienceLevel < 10){
					ChatHelper.UntText(player, "fail2");
					break;
				}
				player.removeExperienceLevel(10);
				stack.stackSize--;
				world.spawnEntityInWorld(new EntityItem(world,
						pos.getX()+0.5, pos.getY()+1.2, pos.getZ()+0.5, new ItemStack(ModItemManager.modBook)));
				break;
			}
			default:
				break;
		}
		return EnumActionResult.FAIL;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world,
			EntityPlayer player, EnumHand hand){
		if(!world.isRemote)
			switch(stack.getItemDamage()){
				case 0:{
					ChatHelper.UntText(player, "inst0");
					break;
				}
				case 1:{
					ChatHelper.UntText(player, "inst1");
					break;
				}
				case 2:{
					ChatHelper.UntText(player, "inst2");
					break;
				}
				case 3:{
					ChatHelper.UntText(player, "inst2");
					break;
				}
				default:
					break;
			}
		return new ActionResult<>(EnumActionResult.FAIL, stack);
	}
}