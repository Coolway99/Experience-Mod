package coolway99.experiencemod.xp;

import coolway99.experiencemod.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class XpHandlerClient extends XpHandler{
	
	private static final String xpBar = "expBar";
	private static final String xpLevel = "expLevel";

	public XpHandlerClient(EntityPlayer player){
		super(player);
		System.out.println("This is a client handler");
		this.level = -26;
		this.experience = -53;
	}
	
	public void renderXpBar(ScaledResolution res){
		Profiler prof = Minecraft.getMinecraft().mcProfiler;
		prof.startSection(xpBar); //Unsure what these are for, but I'll use them anyways
		XpGUI.drawBar(res, this);
		prof.endStartSection(xpLevel);
		XpGUI.drawNum(res, this);
		prof.endSection();
		XpGUI.update();
	}
	
	@Override
	public NBTTagCompound serializeNBT(){
		return null;
	}
	
	@Override
	public void updatePlayerExp(){
		//this.updatePlayerExpNoSync();
	}
	
	@Override
	public void sync(){
		System.out.println("Sending out a ping");
		NetworkHandler.INSTANCE.sendToServer(this);
	}
	
	@Override
	public void deserializeNBT(NBTTagCompound nbt){
		//Unused, the client only syncs to the server
	}
	
	@Override
	public void tick(){
		//Unused, the client serves
	}
	
	@Override
	public void onDeath(){
		//this.sync();
	}
	
	@Override
	public void onXpAdd(int amount){
		//Unused
	}
	
	@Override
	public void onXpLevel(int levels){
		//Unused
	}
}
