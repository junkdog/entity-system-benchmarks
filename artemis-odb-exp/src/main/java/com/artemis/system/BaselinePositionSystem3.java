package com.artemis.system;

import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.PlainPosition;
import com.artemis.systems.EntityProcessingSystem;

public class BaselinePositionSystem3 extends EntityProcessingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem3() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
		voidness.consume(world.delta + 2);
	}
}
