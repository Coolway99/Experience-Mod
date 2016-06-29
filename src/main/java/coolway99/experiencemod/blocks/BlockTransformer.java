package coolway99.experiencemod.blocks;

import coolway99.experiencemod.xp.XpCapability;
import coolway99.experiencemod.xp.XpHandler;
import coolway99.experiencemod.xp.XpMap;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTransformer extends IModBlock{

	public BlockTransformer(String uniqueName){
		super(Material.ANVIL, MapColor.GRAY, uniqueName);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
			EntityPlayer player, EnumHand hand, ItemStack item, EnumFacing side, float hitX,
			float hitY, float hitZ){
		if(world.isRemote) return true;
		if(player.isCreative() || player.isSpectator()
				|| !player.hasCapability(XpCapability.INSTANCE, null)) return false;
		XpHandler handler = player.getCapability(XpCapability.INSTANCE, null).getHandler();
		if(handler.level <= 0) return false;
		double eff = Math.pow(0.95, handler.level/4);
		if(!player.isSneaking() && (handler.power*eff < handler.level+1)) return false;
		if(player.isSneaking() && handler.level <= 1) return false; //Avoiding divide by zero errors
		handler.power *= eff;
		if(player.isSneaking()){
			if(handler.power == 0){
				//TODO actually damage the player
				//This only starts taking effect dangerously above level 20
				player.setHealth((float) (player.getHealth()-((handler.level*(1.0/eff))/5)));
				handler.power = 1;
			}
			handler.level--;
		} else {
			handler.level++;
		}
		if(handler.power > XpMap.getPowerForLevel(handler.level)){
			handler.power = XpMap.getPowerForLevel(handler.level);
		}
		handler.updatePlayerExp();
		return true;
	}
	
}
