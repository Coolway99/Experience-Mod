package coolway99.experiencemod.blocks;

import coolway99.experiencemod.Main;
import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DummyBlock extends IModBlock{
	
	public DummyBlock(String uniqueName){
		super(Material.CLAY, MapColor.BLUE, uniqueName);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX,
			float hitY, float hitZ){
		if(world.isRemote) return true;
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return false;
		player.openGui(Main.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
