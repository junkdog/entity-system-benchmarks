package com.artemis.component;

import com.artemis.PackedComponent;
import com.artemis.World;

public class PComp1 extends PackedComponent {

	private World world;

	public PComp1(World world) {
		this.world = world;
	}
	
	@Override
	protected void forEntity(int entityId) {}

	@Override
	protected void ensureCapacity(int id) {}

	@Override
	protected void reset() {}
}
