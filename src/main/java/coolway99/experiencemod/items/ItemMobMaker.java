package coolway99.experiencemod.items;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMobMaker extends IItemMod{

	public ItemMobMaker(String uniqueName){
		super(uniqueName);
	}
	
	
	//Testing mob spawning
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote) return EnumActionResult.PASS;
		EntityCreeper creeper = new EntityCreeper(world);
		creeper.setLocationAndAngles(pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, 0, 0);
		world.spawnEntityInWorld(creeper);
		return EnumActionResult.SUCCESS;
	}
}