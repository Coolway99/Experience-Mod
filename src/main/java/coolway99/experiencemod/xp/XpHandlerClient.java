package coolway99.experiencemod.xp;

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
	private final XpGUI gui;
	
	public XpHandlerClient(EntityPlayer player){
		super(player);
		this.gui = new XpGUI(this);
	}
	
	public void renderXpBar(ScaledResolution res){
		if(this.player.isCreative() || this.player.isSpectator()) return;
		Profiler prof = Minecraft.getMinecraft().mcProfiler;
		prof.startSection(xpBar); //Unsure what these are for, but I'll use them anyways
		this.gui.drawBar(res);
		prof.endStartSection(xpLevel);
		this.gui.drawNum(res);
		prof.endSection();
		this.gui.update();
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
		//Unused client-side
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
