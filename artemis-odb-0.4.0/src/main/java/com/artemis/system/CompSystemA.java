package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.Comp1;
import com.artemis.component.Comp4;
import com.artemis.component.Comp5;
import com.artemis.systems.EntityProcessingSystem;

public class CompSystemA extends EntityProcessingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemA() {
		super(Aspect.getAspectForAll(Comp1.class, Comp4.class, Comp5.class));
	}

	@Override
	protected void process(Entity e) {
//		voidness.consume(e);
	}
}
