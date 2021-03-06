package me.man_cub.buddies.component.entity.living;

import me.man_cub.buddies.component.entity.BuddiesEntityComponent;
import me.man_cub.buddies.component.entity.living.buddy.Buddy;
import me.man_cub.buddies.component.entity.misc.EntityBody;
import me.man_cub.buddies.component.entity.misc.Burn;
import me.man_cub.buddies.component.entity.misc.Health;
import me.man_cub.buddies.data.BuddiesData;

import org.spout.api.ai.goap.GoapAIComponent;
import org.spout.api.component.entity.NavigationComponent;
import org.spout.api.entity.Entity;
import org.spout.api.util.Parameter;

public abstract class Living extends BuddiesEntityComponent {
	private EntityBody body;
	private Health health;
	private NavigationComponent navigation;
	private GoapAIComponent ai;
	
	@Override
	public void onAttached() {
		super.onAttached();
		Entity holder = getOwner();
		body = holder.add(EntityBody.class);
		health = holder.add(Health.class);
		navigation = holder.add(NavigationComponent.class); //TODO : Does this highlight blocks?
		//navigation.setDefaultExaminers(new BuddiesBlockExaminer());
		ai = holder.add(GoapAIComponent.class);
		holder.add(Burn.class);
	}
	
	public boolean isOnGround() {
		return getOwner().getData().get(BuddiesData.IS_ON_GROUND);
	}
	
	public void setOnGround(boolean onGround) {
		getOwner().getData().put(BuddiesData.IS_ON_GROUND, onGround);
	}
	
	public EntityBody getBody() {
		return body;
	}
	
	public Health getHealth() {
		return health;
	}
	
	public NavigationComponent getNavigation() {
		return navigation;
	}
	
	public GoapAIComponent getAI() {
		return ai;
	}
	
	// TODO : Not sure about sneaking/riding/sprinting
	protected byte getCommonMetadata() {
		byte value = 0;
		Burn burn = getOwner().get(Burn.class);
		if (burn != null) {
			value = (byte) (value | (burn.isOnFire() ? 1 : 0));
		}
		
		value = (byte) (value | ((isSneaking() ? 1 : 0) << 1));
		value = (byte) (value | ((isRiding() ? 1 : 0) << 2));
		
		Buddy buddy = getOwner().get(Buddy.class);
		if (buddy != null) {
			value = (byte) (value | ((buddy.isSprinting() ? 1 : 0) << 3));
		}
		
		/*
		EffectsComponent effects = getOwner().get(EffectsComponent.class);
		if (effects != null) {
			value = (byte) (value | ((effects.containsEffect(StatusEffect.INVISIBILITY) ? 1 : 0) << 4));
		}*/
		
		return value;
	}
	
	public boolean isRiding() {
		return getOwner().getData().get(BuddiesData.IS_RIDING);
	}
	
	public void setRiding(boolean isRiding) {
		getOwner().getData().put(BuddiesData.IS_RIDING, isRiding);
		sendMetaData();
	}
	
	public boolean isSneaking() {
		return getOwner().getData().get(BuddiesData.IS_SNEAKING);
	}
	
	public void setSneaking(boolean isSneaking) {
		getOwner().getData().put(BuddiesData.IS_SNEAKING, isSneaking);
		sendMetaData();
	}
	
	public void sendMetaData() {
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 0, getCommonMetadata()));
	}

}
