package com.artemis.component;

import com.artemis.Entity;
import com.artemis.PackedComponent;
import com.artemis.World;

public class PComp1 extends PackedComponent {

	public PComp1() {}
	
	@Override
	protected PackedComponent forEntity(Entity e) {
		return null;
	}
	
	@Override
	protected void reset() {}
}
