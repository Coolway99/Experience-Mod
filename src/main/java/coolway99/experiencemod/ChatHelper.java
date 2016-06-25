package coolway99.experiencemod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public class ChatHelper{
	
	/**
	 * Like {@link #UntranslatedText(EntityPlayer, String)}, but adds {@link Main#MODID} in front
	 */
	public static void UntText(EntityPlayer player, String text){
		UntranslatedText(player, Main.MODID+"."+text);
	}
	
	public static void UntranslatedText(EntityPlayer player, String text){
		UntranslatedText(player, text, new Object[]{/*NO FORMATTING*/});
	}
	
	/**
	 * Like {@link #UntranslatedText(EntityPlayer, String, Object...)}, but adds {@link Main#MODID} in front
	 */
	public static void UntText(EntityPlayer player, String text, Object...formatting){
		UntranslatedText(player, Main.MODID+"."+text, formatting);
	}
	
	public static void UntranslatedText(EntityPlayer player, String text, Object...formatting){
		player.addChatMessage(new TextComponentTranslation(text, formatting));
	}
}
