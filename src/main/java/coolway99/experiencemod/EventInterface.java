package coolway99.experiencemod;

import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerAddXpEvent;
import net.minecraftforge.event.entity.player.PlayerChangeLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@SuppressWarnings("static-method")
public class EventInterface{
	
	@SubscribeEvent
	public void addCapability(AttachCapabilitiesEvent.Entity event){
		if(!(event.getObject() instanceof EntityPlayer)) return;
		event.addCapability(new ResourceLocation(Main.MODID+":main"), new XpCapability((EntityPlayer) event.getObject()));
	}
	
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent event){
		if(!(event.getEntity() instanceof EntityPlayerSP)) return;
		EntityPlayer player = (EntityPlayer) event.getEntity();
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).getHandler().sync();
	}
	
	@SubscribeEvent
	public void tick(PlayerTickEvent event){
		EntityPlayer player = event.player;
		if(!player.hasCapability(XpCapability.INSTANCE, null)) return;
		player.getCapability(XpCapability.INSTANCE, null).tick();
	}
	
	@SubscribeEvent
	public void onXpPickup(PlayerPickupXpEvent event){
		System.out.println(event.getEntityPlayer().getName()+" picked up an exp orb!");
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
		if(!event.isWasDeath()) return; //The player only switched dimensions
		//Otherwise the player died
		EntityPlayer original = event.getOriginal();
		if(!original.hasCapability(XpCapability.INSTANCE, null)) return;
		XpCapability cap = event.getEntityPlayer().getCapability(XpCapability.INSTANCE, null);
		cap.deserializeNBT(original.getCapability(XpCapability.INSTANCE, null).serializeNBT());
		cap.onDeath();
	}
}
