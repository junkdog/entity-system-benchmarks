package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

public class BaselineSystem3 extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselineSystem3() {
		super(Filter.allComponents(PooledPosition.class));
	}
	
	@Override
	protected void process(Entity e) {
		voidness.consume(e);
		voidness.consume(world.getDelta() + 2);
	}
}
