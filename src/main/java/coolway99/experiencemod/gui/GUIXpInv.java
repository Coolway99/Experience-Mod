package coolway99.experiencemod.gui;

import coolway99.experiencemod.container.ContainerXpInv;
import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIXpInv extends GuiContainer{
	
	public GUIXpInv(EntityPlayer player, XpCapability cap){
		super(new ContainerXpInv(player, cap));
		this.setGuiSize(200, 200);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		// TODO Auto-generated method stub
		
	}
}
