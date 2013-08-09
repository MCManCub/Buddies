package me.man_cub.buddies.material.block.natural;

import me.man_cub.buddies.data.resources.BuddiesMaterialModels;
import me.man_cub.buddies.material.block.Solid;

public class Border extends Solid {
	
	public Border(String name, int id) {
		super(name, id, BuddiesMaterialModels.BORDER);
		this.setTransparent();
	}

}
