package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.UnpackedPositionSimple;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class UnpackedPositionSystemSimple extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<UnpackedPositionSimple> positionMapper;
	
	@SuppressWarnings("unchecked")
	public UnpackedPositionSystemSimple() {
		super(Aspect.getAspectForAll(UnpackedPositionSimple.class));
	}

	@Override
	protected void process(Entity e) {
		UnpackedPositionSimple pos = positionMapper.get(e);
		pos.x(pos.x() + .1f % 100000);
		pos.y(pos.y() - 0.1f % 100000);
		
		voidness.consume(e);
	}
}
