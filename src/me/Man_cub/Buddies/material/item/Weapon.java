package me.Man_cub.Buddies.material.item;

import java.util.Set;

import me.Man_cub.Buddies.data.weapon.WeaponType;
import me.Man_cub.Buddies.material.BuddiesItemMaterial;

import org.spout.api.inventory.ItemStack;
import org.spout.api.util.flag.Flag;

public abstract class Weapon extends BuddiesItemMaterial {
	private WeaponType weaponType;

	public Weapon(String name, int id, String model, WeaponType itemType) {
		super(name, id, model) ;
		this.weaponType = itemType;
		this.setMaxStackSize(1);
	}
	
	public WeaponType getWeaponType() {
		return this.weaponType;
	}
	
	@Override
	public void getItemFlags(ItemStack item, Set<Flag> flags) {
		super.getItemFlags(item, flags);
		flags.add(this.weaponType.getWeaponFlag());
	}
		
}
