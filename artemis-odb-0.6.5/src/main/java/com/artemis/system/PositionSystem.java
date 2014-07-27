package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.Position;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PositionSystem extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<Position> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PositionSystem() {
		super(Aspect.getAspectForAll(Position.class));
	}

	@Override
	protected void process(Entity e) {
		Position pos = positionMapper.get(e);
		pos.x(pos.x() + .1f % 100000);
		pos.y(pos.y() - 0.1f % 100000);
		
		voidness.consume(e);
	}
}
