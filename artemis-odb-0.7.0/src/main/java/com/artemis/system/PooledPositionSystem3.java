package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PooledPositionSystem3 extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<PooledPosition> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PooledPositionSystem3() {
		super(Aspect.getAspectForAll(PooledPosition.class));
	}

	@Override
	protected void process(Entity e) {
		PooledPosition pos = positionMapper.get(e);
		pos.x += 0.5f;
		pos.y -= 0.5f;
		
		voidness.consume(e);
	}
}
