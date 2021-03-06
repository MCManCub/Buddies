package me.man_cub.buddies.material;

import java.util.ArrayList;
import java.util.List;

import me.man_cub.buddies.BuddiesPlugin;
import me.man_cub.buddies.component.world.misc.Sky;
import me.man_cub.buddies.data.BuddiesData;
import me.man_cub.buddies.data.drops.type.block.BlockDrops;
import me.man_cub.buddies.event.material.network.BlockActionEvent;
import me.man_cub.buddies.render.BuddiesEffects;

import org.spout.api.component.block.BlockComponent;
import org.spout.api.entity.Entity;
import org.spout.api.entity.Player;
import org.spout.api.geo.LoadOption;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.geo.cuboid.Chunk;
import org.spout.api.material.BlockMaterial;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.block.BlockFaces;
import org.spout.api.Engine;
import org.spout.api.Platform;
import org.spout.physics.collision.shape.CollisionShape;

public abstract class BuddiesBlockMaterial extends BlockMaterial implements BuddiesMaterial {
	private final int buddiesId;
	private int meleeDamage = 1;
	//private SoundEffect stepSound = SoundEffects.STEP_STONE;
	private final BlockDrops drops = new BlockDrops();
	
	public BuddiesBlockMaterial(String name, int id, String model, CollisionShape shape, Class<? extends BlockComponent>... components) {
		super((short) 0, name, model, shape, components);
		this.buddiesId = id;
		if (getEngine().getPlatform() == Platform.CLIENT) {
			if (!getModel().getRenderMaterial().getRenderEffects().contains(BuddiesEffects.SKY_TIME)) {
				getModel().getRenderMaterial().addRenderEffect(BuddiesEffects.SKY_TIME);
				getModel().getRenderMaterial().addBufferEffect(BuddiesEffects.LIGHTING);
			}
		}
	}
	
	public BuddiesBlockMaterial(short dataMask, String name, int id, String model, CollisionShape shape, Class<? extends BlockComponent>... components) {
		super(dataMask, name, model, shape, components);
		this.buddiesId = id;
		this.setTransparent();
		if (getEngine().getPlatform() == Platform.CLIENT) {
			if (!getModel().getRenderMaterial().getRenderEffects().contains(BuddiesEffects.SKY_TIME)) {
				getModel().getRenderMaterial().addRenderEffect(BuddiesEffects.SKY_TIME);
				getModel().getRenderMaterial().addBufferEffect(BuddiesEffects.LIGHTING);
			}
		}
	}
	
	public final Engine getEngine() {
		return BuddiesPlugin.getInstance().getEngine();
	}
	
	@Override
	public final int getBuddiesId() {
		return buddiesId;
	}
	
	@Override
	public void onUpdate(BlockMaterial oldMaterial, Block block) {
		super.onUpdate(oldMaterial, block);
	}
	
	@Override
	public BuddiesBlockMaterial setFriction(float friction) {
		return (BuddiesBlockMaterial) super.setFriction(friction);
	}
	
	@Override
	public BuddiesBlockMaterial setHardness(float hardness) {
		return (BuddiesBlockMaterial) super.setHardness(hardness);
	}
	
	//Do step sound
	
	@Override
	public boolean isPlacementObstacle() {
		return true;
	}
	
	@Override
	public boolean hasPhysics() {
		return false;
	}
	
	@Override
	public int getDamage() {
		return this.meleeDamage;
	}
	
	/**
	 * Temporary function to handle resources.entities entering this Block<br>
	 * <b>This is a STUB! Needs to be moved to SpoutAPI!</b>
	 * @param entity that entered or moved
	 * @param block of this material that got entered
	 */
	public void onEntityCollision(Entity entity, Block block) {
	}
	
	@Override
	public BuddiesBlockMaterial setDamage(int damage) {
		this.meleeDamage = damage;
		return this;
	}
	
	public boolean canSupport(BlockMaterial material, BlockFace face) {
		return false;
	}
	
	public boolean isPlacementSuppressed() {
		return false;
	}
	
	// TODO : Still gotta do this system. I don't want to think about it.
	/*
	private void updateDropFlags() {
		this.drops.NOT_ADMIN.clearFlags();
		this.drops.NOT_ADMIN.addFlags(PlayerFlags.ADMIN.NOT);
		if (this.miningLevel != null && this.miningLevel != ToolLevel.NONE) {
			this.drops.NOT_CREATIVE.addFlags(this.miningLevel.getDropFlag());
			Flag[] typeFlags = new Flag[this.miningTypes.size()];
			int i = 0;
			for (ToolType type : this.miningTypes) {
				typeFlags[i] = type.getDropFlag();
				i++;
			}
			this.drops.NOT_ADMIN.addFlags(new FlagBundle(typeFlags));
		}
	}*/
	
	// TODO: Set up drops
	public BlockDrops getDrops() {
		return this.drops;
	}
	
	public static void playBlockAction(Block block, byte arg1, byte arg2) {
		BlockActionEvent event = new BlockActionEvent(block, block.getMaterial(), arg1, arg2);
		for (Player player : block.getChunk().getObservingPlayers()) {
			player.getNetwork().callProtocolEvent(event);
		}
	}
	
	/**
	 * Gets if rain is falling nearby the block specified
	 * @param block to check it nearby of
	 * @return True if it is raining, False if not
	 */
	public static boolean hasRainNearby(Block block) {
		Sky sky = block.getWorld().get(Sky.class);
		if (sky != null & sky.hasWeather()) {
			if (sky.getWeatherSimulator().isRainingAt(block.getX(), block.getY(), block.getZ(), false)) {
				for (BlockFace face : BlockFaces.NESW) {
					if (block.translate(face).isAtSurface()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Gets if rain is falling on top of the block specified
	 * @param block to check
	 * @return True if rain is falling on the Block, false if not
	 */
	public static boolean isRaining(Block block) {
		return block.getWorld().getData().get(BuddiesData.WORLD_WEATHER).isRaining() && block.isAtSurface();
	}
	
	public static List<Chunk> getChunkColumn(Chunk middle) {
		Chunk top = middle;
		Chunk tmp;
		while (true) {
			tmp = top.getRelative(BlockFace.TOP, LoadOption.NO_LOAD);
			if (tmp != null && tmp.isLoaded()) {
				top = tmp;
			} else {
				break;
			}
		}
		List<Chunk> rval = new ArrayList<Chunk>();
		while (top != null && top.isLoaded()) {
			rval.add(top);
			top = top.getRelative(BlockFace.BOTTOM, LoadOption.NO_LOAD);
		}
		return rval;
	}
	
}
