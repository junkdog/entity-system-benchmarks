package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.PackedPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PackedPositionSystem2 extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<PackedPosition> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PackedPositionSystem2() {
		super(Aspect.getAspectForAll(PackedPosition.class));
	}

	@Override
	protected void process(Entity e) {
		PackedPosition pos = positionMapper.get(e);
		pos.x -= 1;
		pos.y += 1;
		
		voidness.consume(e);
	}
}
