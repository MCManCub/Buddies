package me.Man_cub.Buddies.event.entity;

import org.spout.api.entity.Entity;
import org.spout.api.event.Cause;
import org.spout.api.event.HandlerList;
import org.spout.api.event.entity.EntityEvent;

import me.Man_cub.Buddies.event.cause.DamageCause.DamageType;
import me.Man_cub.Buddies.event.cause.NullDamageCause;

public class EntityCombustEvent extends EntityEvent {
	private static HandlerList handlers = new HandlerList();
	private final Cause<? extends Object> cause;
	private int duration;

	public EntityCombustEvent(Entity e, int duration) {
		super(e);
		this.duration = duration;
		this.cause = new NullDamageCause(DamageType.UNKNOWN);
	}

	public EntityCombustEvent(Entity e, int duration, Cause<?> cause) {
		super(e);
		this.duration = duration;
		this.cause = cause;
	}

	/**
	 * Gets the time that the entity should burn for.
	 * @return The time in seconds.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the time that the entity should burn for.
	 * @param duration The time in seconds.
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Gets the {@link Cause} for this event.
	 * @param cause
	 */
	public Cause<? extends Object> getCombustCause() {
		return cause;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
