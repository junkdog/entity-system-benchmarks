package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.UnpackedPositionOld;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class UnpackedPositionSystemOld extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<UnpackedPositionOld> positionMapper;
	
	@SuppressWarnings("unchecked")
	public UnpackedPositionSystemOld() {
		super(Aspect.getAspectForAll(UnpackedPositionOld.class));
	}

	@Override
	protected void process(Entity e) {
		UnpackedPositionOld pos = positionMapper.get(e);
		pos.x(pos.x() + .1f % 100000);
		pos.y(pos.y() - 0.1f % 100000);
		
		voidness.consume(e);
	}
}
