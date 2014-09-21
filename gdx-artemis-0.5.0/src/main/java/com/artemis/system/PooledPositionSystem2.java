package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

public class PooledPositionSystem2 extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<PooledPosition> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PooledPositionSystem2() {
		super(Filter.allComponents(PooledPosition.class));
	}
	
	@Override
	public void initialize() {
		positionMapper = world.getMapper(PooledPosition.class);
	}

	@Override
	protected void process(Entity e) {
		PooledPosition pos = positionMapper.get(e);
		pos.x -= 1;
		pos.y += 1;
		
		voidness.consume(e);
	}
}
