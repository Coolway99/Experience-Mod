package coolway99.experiencemod.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public final class EntityAIAttackAllExcept<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T>{
	
	/**
	 * The list of explicit entities to not target
	 */
	private final List<T> excluded;
	
	
	public EntityAIAttackAllExcept(EntityCreature parent, Class<T> target, T excluded, boolean checkSight, boolean onlyNearby){
		super(parent, target, checkSight, onlyNearby);
		ArrayList<T> list = new ArrayList<>();
		list.add(excluded);
		this.excluded = list;
	}
	
	public EntityAIAttackAllExcept(EntityCreature parent, Class<T> target, List<T> excluded, boolean checkSight, boolean onlyNearby){
		super(parent, target, checkSight, onlyNearby);
		this.excluded = excluded;
	}
	
	@Override
	public boolean shouldExecute(){
		//Copy/pasted from EntityAINearestAttackableTarget with heavy edits
		List<T> list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.getTargetableArea(this.getTargetDistance()));
		Collections.sort(list, this.theNearestAttackableTargetSorter);
		for(int x = 0; x < list.size(); x++){
			T targetEntity = list.get(x);
			if(this.excluded.contains(targetEntity)) continue;
			this.targetEntity = targetEntity;
			return true;
		}
		return false;
	}
	
}
