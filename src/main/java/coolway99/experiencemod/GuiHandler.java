package coolway99.experiencemod;

import coolway99.experiencemod.container.ContainerXpInv;
import coolway99.experiencemod.gui.GUIXpInv;
import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z){
		switch(ID){
			case 0:{
				if(!player.hasCapability(XpCapability.INSTANCE, null)) return null;
				System.out.println("We opened the server-side");
				return new ContainerXpInv(player, player.getCapability(XpCapability.INSTANCE, null));
			}
			default:
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y,
			int z){
		switch(ID){
			case 0:{
				if(!player.hasCapability(XpCapability.INSTANCE, null)) return null;
				System.out.println("We opened the client-side");
				return new GUIXpInv(player, player.getCapability(XpCapability.INSTANCE, null));
			}
			default:
				break;
		}
		return null;
	}
}
