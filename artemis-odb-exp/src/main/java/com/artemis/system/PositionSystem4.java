package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.UnpackedPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PositionSystem4 extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<UnpackedPosition> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PositionSystem4() {
		super(Aspect.getAspectForAll(UnpackedPosition.class));
	}

	@Override
	protected void process(Entity e) {
		UnpackedPosition pos = positionMapper.get(e);
		pos.x(pos.x() + .1f % 100000);
		pos.y(pos.y() - 0.1f % 100000);
		
		voidness.consume(e);
	}
}
