package com.artemis.system;

import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

public class BaselinePositionSystem extends EntityProcessingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem() {
		super(Filter.allComponents(PooledPosition.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
	}
}
