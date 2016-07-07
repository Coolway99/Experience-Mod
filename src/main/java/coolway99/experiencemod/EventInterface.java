package coolway99.experiencemod;

import coolway99.experiencemod.xp.XpCapability;
import coolway99.experiencemod.xp.XpHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerAddXpEvent;
import net.minecraftforge.event.entity.player.PlayerChangeLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("static-method")
public class EventInterface{
	
	@SubscribeEvent
	public void addCapability(AttachCapabilitiesEvent.Entity event){
		if(!(event.getObject() instanceof EntityPlayer)) return;
		event.addCapability(new ResourceLocation(Main.MODID+":main"), new XpCapability((EntityPlayer) event.getObject()));
	}
	
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event){
		if(!(event.getEntity() instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if(!ModUtils.hasXpCap(player)) return;
		ModUtils.getXpCap(player).getHandler().ping(); 
	}
	
	@SubscribeEvent
	public void tick(PlayerTickEvent event){
		EntityPlayer player = event.player;
		if(!ModUtils.hasXpCap(player)) return;
		ModUtils.getXpCap(player).tick();
	}
	
	@SubscribeEvent
	public void onXpPickup(PlayerPickupXpEvent event){
		//System.out.println(event.getEntityPlayer().getName()+" picked up an exp orb!");
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onXpAdd(PlayerAddXpEvent event){
		EntityPlayer player = event.getEntityPlayer();
		if(!ModUtils.hasXpCap(player)) return;
		ModUtils.getXpCap(player).onXpAdd(event.getAmount());
		event.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onXpLevel(PlayerChangeLevelEvent event){
		EntityPlayer player = event.getEntityPlayer();
		if(!ModUtils.hasXpCap(player)) return;
		ModUtils.getXpCap(player).onXpLevel(event.getLevels());
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event){
		System.out.println("The player either died or returned from the end");
		EntityPlayer original = event.getOriginal();
		if(original.worldObj.isRemote || !ModUtils.hasXpCap(original)) return;
		//It's safe to say the new player has the cap as well
		XpCapability cap = ModUtils.getXpCap(event.getEntityPlayer());
		cap.deserializeNBT(ModUtils.getXpCap(original).serializeNBT());
		if(event.isWasDeath()){
			//The player died
			cap.onDeath();
		}
		//Otherwise the player only switched dimensions
		cap.getHandler().ping();
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.LOWEST, receiveCanceled=true)
	public void onGuiRender(RenderGameOverlayEvent event){
		if(event.getType() != ElementType.EXPERIENCE) return;
		//Since this only takes place on the client, it's easy to shift the context to XpHandler
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(!ModUtils.hasXpCap(player)) return;
		//We are rendering the XP Bar
		event.setCanceled(true);
		((XpHandlerClient) ModUtils.getXpCap(player).getHandler())
				.renderXpBar(event.getResolution());
	}
}
