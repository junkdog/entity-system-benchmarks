package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

public class PooledPositionSystem extends EntityProcessingSystem {

	ComponentMapper<PooledPosition> positionMapper;

	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public PooledPositionSystem() {
		super(Filter.allComponents(PooledPosition.class));
	}
	
	@Override
	public void initialize() {
		positionMapper = world.getMapper(PooledPosition.class);
	}

	@Override
	protected void process(Entity e) {
		PooledPosition pos = positionMapper.get(e);
		pos.x += 0.1f % 100000;
		pos.y -= 0.1f % 100000;
		
		voidness.consume(e);
	}
}
