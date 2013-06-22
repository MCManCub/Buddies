package me.Man_cub.Buddies.material.item;

import me.Man_cub.Buddies.component.entity.substance.object.Substance;
import me.Man_cub.Buddies.component.entity.substance.object.projectile.Projectile;
import me.Man_cub.Buddies.material.BuddiesItemMaterial;

import org.spout.api.component.entity.SceneComponent;
import org.spout.api.entity.Entity;
import org.spout.api.event.player.Action;
import org.spout.api.geo.World;
import org.spout.api.math.VectorMath;

public class Bullet extends BuddiesItemMaterial {
	private Class<? extends Substance> bulletType;

	public Bullet(String name, int id, Class<? extends Substance> bulletType) {
		super(name, id, null);
		this.bulletType = bulletType;
	}

	@Override
	public void onInteract(Entity entity, Action type) {
		onInteract(entity, type, 10f);
	}

	public void onInteract(Entity entity, Action type, float mass) {
		super.onInteract(entity, type);
		if (type == Action.LEFT_CLICK) {
			World world = entity.getWorld();
			// TODO : Figure out where to start the entity. Also make sure it doesn't try to throw the item you're holding.
			Substance item = world.createEntity(entity.getScene().getPosition().add(0, 1.6f, 0), bulletType).add(bulletType);
			SceneComponent scene = item.getOwner().getScene();
			//scene.setShape(mass, new SphereShape(0.1f)); // TODO: Correct this
			scene.impulse(VectorMath.getDirection(entity.getScene().getRotation()).multiply(250)); //TODO: Need real parameters
			if (item instanceof Projectile) {
				((Projectile) item).setShooter(entity);
			}
			world.spawnEntity(item.getOwner());
		}
	}
}
