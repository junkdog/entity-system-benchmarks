package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.component.PlainPosition;
import com.artemis.systems.EntityProcessingSystem;

public class PlainPositionSystem2 extends EntityProcessingSystem {

	@Mapper ComponentMapper<PlainPosition> positionMapper;

	Blackhole voidness = new Blackhole();
	
	
	@SuppressWarnings("unchecked")
	public PlainPositionSystem2() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}

	@Override
	protected void process(Entity e) {
		PlainPosition pos = positionMapper.get(e);
		pos.x -= 1;
		pos.y += 1;
		
		voidness.consume(e);
	}
}
