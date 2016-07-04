package coolway99.experiencemod.gui;

import coolway99.experiencemod.xp.XpHandlerClient;
import coolway99.experiencemod.xp.XpMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIXpBar extends Gui{
	
	private static final int enderColor = 0xFF_AA_00_AA;
	private static final int xpColor = 0xFF_00_CC_00;
	private static final int BLACK = 0xFF_00_00_00;
	
	private final FontRenderer font;
	private final XpHandlerClient xp;
	
	public GUIXpBar(XpHandlerClient xp){
		this.font = Minecraft.getMinecraft().fontRendererObj;
		this.xp = xp;
	}
	
	public void drawBar(ScaledResolution res){
		int width = 180;
		int height = 4;
		int x = (res.getScaledWidth()-width)/2;
		int y = res.getScaledHeight() - 30;
		drawRectXy(x, y, width, height*2, 0xFF555555);
		drawRectXy(x, y, (int) (((float) this.xp.power/XpMap.getPowerForLevel(this.xp.level))*width), height, xpColor);
		y += height;
		drawRectXy(x, y,  (int) (((float) this.xp.enderExperience/XpMap.getExp(this.xp.enderLevel))*width), height, enderColor);
	}
	
	public void drawNum(ScaledResolution res){
		int levelWidth = this.font.getStringWidth(Integer.toString(this.xp.level));
		int enderWidth = this.font.getStringWidth(Integer.toString(this.xp.enderLevel));
		int x = ((res.getScaledWidth()-enderWidth)/2)-levelWidth;
		int y = res.getScaledHeight() - 40;
		drawInt(this.xp.level, x+1, y, BLACK);
		drawInt(this.xp.level, x, y, xpColor);
		x = x+(3*levelWidth/2)+enderWidth/2; //It just works, ok?
		drawInt(this.xp.enderLevel, x+1, y, BLACK);
		drawInt(this.xp.enderLevel, x, y, enderColor);
		drawInt(this.xp.power, 0, 0, xpColor);
		drawInt((this.xp.level == 0 ? 0 : this.xp.power/this.xp.level), 0, 10, xpColor);
		drawInt(XpMap.getPowerForLevel(this.xp.level), 20, 0, xpColor);
		drawInt(XpMap.getExp(this.xp.level), 20, 10, xpColor);
	}
	
	//This is called after drawing, to be "dedicated" for special effects
	public void update(){
		
	}
	
	public void drawInt(int in, int x, int y, int color){
		this.drawString(this.font, Integer.toString(in), x, y, color);
	}
	
	public static void drawRectXy(int x, int y, int width, int height, int color){
		width += x;
		height += y;
		drawRect(x, y, width, height, color);
	}
}
