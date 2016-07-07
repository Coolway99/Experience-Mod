package coolway99.experiencemod.items;

import coolway99.experiencemod.ModUtils;
import coolway99.experiencemod.ai.EntityAIAttackAllExcept;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityVillager;
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
	
	//Testing mob spawning, it will eventually work on more than just creepers
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(world.isRemote) return EnumActionResult.PASS;
		EntityCreeper creeper = new EntityCreeper(world);
		creeper.setLocationAndAngles(pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, 0, 0);
		//ModUtils.removeClassFromAI(creeper.targetTasks, EntityAINearestAttackableTarget.class);
		//Until I find a better way to clear out the list
		ModUtils.clearAI(creeper.targetTasks);
		creeper.targetTasks.addTask(1, new EntityAIAttackAllExcept<>(creeper, EntityPlayer.class, player, true, true));
		//creeper.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(creeper, EntityVillager.class, true));
		world.spawnEntityInWorld(creeper);
		return EnumActionResult.SUCCESS;
	}
}