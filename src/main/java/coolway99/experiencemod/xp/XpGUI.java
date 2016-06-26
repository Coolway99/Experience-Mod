package coolway99.experiencemod.xp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class XpGUI extends Gui{
	
	private static Gui gui;
	private static FontRenderer font;
	
	public static void init(){
		gui = new Gui();
		font = Minecraft.getMinecraft().fontRendererObj;
	}
	
	public static void drawBar(ScaledResolution res, XpHandlerClient xp){
		int width = 180;
		int height = 8;
		int x = (res.getScaledWidth()-width)/2;
		int y = res.getScaledHeight() - 30;
		drawRectXy(x, y, width, height, 0xFF555555);
		drawRectXy(x, y, (int) (((float) xp.experience/XpMap.getExpForLevel(xp.level))*width), height, 0xFF00FF00);

	}
	
	public static void drawNum(ScaledResolution res, XpHandlerClient xp){
		int x = (res.getScaledWidth()-font.getStringWidth(Integer.toString(xp.level)))/2;
		int y = res.getScaledHeight() - 40;
		gui.drawString(font, Integer.toString(xp.level), x+1, y, 0xFF000000);
		gui.drawString(font, Integer.toString(xp.level), x, y, 0xFF00FF00);
	}
	
	//This is called after drawing, to be "dedicated" for special effects
	public static void update(){
		
	}
	
	public static void drawRectXy(int x, int y, int width, int height, int color){
		width += x;
		height += y;
		drawRect(x, y, width, height, color);
	}
}
