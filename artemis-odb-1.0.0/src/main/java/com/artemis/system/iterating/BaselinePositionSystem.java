package com.artemis.system.iterating;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.PlainPosition;
import com.artemis.systems.IteratingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class BaselinePositionSystem extends IteratingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem() {
		super(Aspect.all(PlainPosition.class));
	}

	@Override
	protected void process(int e) {
		voidness.consume(e);
		voidness.consume(world.delta);
	}
}
