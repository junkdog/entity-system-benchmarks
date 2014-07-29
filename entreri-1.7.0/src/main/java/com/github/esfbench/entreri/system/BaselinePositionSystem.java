package com.github.esfbench.entreri.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.esfbench.entreri.Aspect;
import com.github.esfbench.entreri.Entity;
import com.github.esfbench.entreri.component.PlainPosition;
import com.github.esfbench.entreri.systems.EntityProcessingSystem;

public class BaselinePositionSystem extends EntityProcessingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
	}
}
