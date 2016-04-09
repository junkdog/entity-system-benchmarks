package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.*;
import com.artemis.systems.EntityProcessingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemD extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();

	@SuppressWarnings("unchecked")
	public CompSystemD() {
		super(Aspect.getAspectForAll(Comp6.class, Comp7.class, Comp8.class).exclude(Comp9.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
	}
}
