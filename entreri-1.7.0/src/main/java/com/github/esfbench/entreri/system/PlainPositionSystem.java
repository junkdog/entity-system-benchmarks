package com.github.esfbench.entreri.system;


import org.openjdk.jmh.infra.Blackhole;

import com.github.esfbench.entreri.Aspect;
import com.github.esfbench.entreri.ComponentMapper;
import com.github.esfbench.entreri.Entity;
import com.github.esfbench.entreri.component.PlainPosition;
import com.github.esfbench.entreri.systems.EntityProcessingSystem;

public class PlainPositionSystem extends EntityProcessingSystem {

	ComponentMapper<PlainPosition> positionMapper;

	Blackhole voidness = new Blackhole();
	
	
	@SuppressWarnings("unchecked")
	public PlainPositionSystem() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}
	
	@Override
	protected void initialize() {
		positionMapper = world.getMapper(PlainPosition.class);
	}

	@Override
	protected void process(Entity e) {
		PlainPosition pos = positionMapper.get(e);
		pos.x += 0.1f % 100000;
		pos.y -= 0.1f % 100000;
		
		voidness.consume(e);
	}
}
