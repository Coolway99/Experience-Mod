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
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).getHandler().ping(); 
	}
	
	@SubscribeEvent
	public void tick(PlayerTickEvent event){
		EntityPlayer player = event.player;
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).tick();
	}
	
	@SubscribeEvent
	public void onXpPickup(PlayerPickupXpEvent event){
		//System.out.println(event.getEntityPlayer().getName()+" picked up an exp orb!");
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onXpAdd(PlayerAddXpEvent event){
		EntityPlayer player = event.getEntityPlayer();
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).onXpAdd(event.getAmount());
		event.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public void onXpLevel(PlayerChangeLevelEvent event){
		EntityPlayer player = event.getEntityPlayer();
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).onXpLevel(event.getLevels());
		event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event){
		System.out.println("The player either died or returned from the end");
		EntityPlayer original = event.getOriginal();
		if(original.worldObj.isRemote || !original.hasCapability(XpCapability.INSTANCE, null)) return;
		XpCapability cap = event.getEntityPlayer().getCapability(XpCapability.INSTANCE, null);
		cap.deserializeNBT(original.getCapability(XpCapability.INSTANCE, null).serializeNBT());
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
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		//We are rendering the XP Bar
		event.setCanceled(true);
		((XpHandlerClient) player.getCapability(XpCapability.INSTANCE, null).getHandler())
				.renderXpBar(event.getResolution());
	}
}
