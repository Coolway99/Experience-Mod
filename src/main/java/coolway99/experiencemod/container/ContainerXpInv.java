package coolway99.experiencemod.container;

import coolway99.experiencemod.items.ModItemManager;
import coolway99.experiencemod.xp.XpCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerXpInv extends Container{
	
	private final EntityPlayer player;
	private final ItemStackHandler xpInv;
	
	public ContainerXpInv(EntityPlayer player, XpCapability cap){
		System.out.println("We are on the "+(player.worldObj.isRemote ? "client" : "server"));
		this.player = player;
		InventoryPlayer playerInv = this.player.inventory;
		this.xpInv = cap.getInv();
		//It's assumed the player has the capability
		for(int x = 0; x < 5; x++){
			this.addSlotToContainer(new XpSlot(this.xpInv, x, x*18, 0));
		}
		
		for(int y = 0; y < 4; y++){
			for(int x = 0; x < 9; x++){
				this.addSlotToContainer(new Slot(playerInv, x+(y*9), x*18, ((y+1)*20)));
			}
		}
	}
	
	@Override
	public boolean canDragIntoSlot(Slot slotIn){
		return false;
	}
	
	@Override
	public boolean getCanCraft(EntityPlayer player){
		return false;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player){
		return player.hasCapability(XpCapability.INSTANCE, null);
	}
	
	@Override
	public void putStackInSlot(int slotID, ItemStack stack){
		super.putStackInSlot(slotID, stack);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		System.out.println("Container closed on the "+(player.worldObj.isRemote ? "client" : "server"));
		System.out.println("The player has a "+player.inventory.getStackInSlot(0).getUnlocalizedName()+" in slot 0");
	}
}

class XpSlot extends SlotItemHandler{

	public XpSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition){
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack){
		return stack != null && stack.getItem() == ModItemManager.dragonBall; //TODO test item
	}
	
	@Override
	public int getSlotStackLimit(){
		return 1;
	}
	
	@Override
	public void putStack(ItemStack stack){
		System.out.println("Calling putStack");
		super.putStack(stack);
	}
}